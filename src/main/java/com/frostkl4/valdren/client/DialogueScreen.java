package com.frostkl4.valdren.client;

import com.frostkl4.valdren.dialogue.DialogueManager;
import com.frostkl4.valdren.dialogue.DialogueOption;
import com.frostkl4.valdren.entity.npc.NpcType;
import com.frostkl4.valdren.network.DialogueActionPacket;
import com.frostkl4.valdren.network.DialoguePacket;
import com.frostkl4.valdren.network.ModNetwork;
import com.frostkl4.valdren.quest.Affinity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Tela de conversa no estilo da referência: o mundo continua visível, a câmera vira para o NPC,
 * uma faixa escura embaixo mostra nome, profissão, afinidade e a fala (com efeito de digitação),
 * e as respostas ficam empilhadas à direita, com uma seta na selecionada.
 * Controles: mouse, setas/W/S, Enter/Espaço/E, teclas 1-9, Esc para sair.
 */
public class DialogueScreen extends Screen {
    private static final int LINES_PER_PAGE = 3;
    private static final int LINE_H = 11;
    private static final int OPT_H = 14;
    private static final int OPT_GAP = 2;
    private static final float CHARS_PER_TICK = 2.5F;

    private static final int COL_BAR = 0xD00C0C10;
    private static final int COL_NAME = 0xFFFFD24A;
    private static final int COL_SUB = 0xFFAFAFAF;
    private static final int COL_TEXT = 0xFFF4F4F4;
    private static final int COL_EXTRA = 0xFFE3B95B;
    private static final int COL_OPT = 0xFFF0F0F0;
    private static final int COL_OPT_HL = 0xFFFFD24A;

    private int entityId;
    private NpcType npc = NpcType.BRAM;
    private int affinity;
    private Component text = Component.empty();
    private Component extra = Component.empty();
    private List<DialogueOption> options = List.of();
    private List<String> lines = List.of("");
    private List<FormattedCharSequence> extraLines = List.of();
    private int page;
    private float shown;
    private int selected;
    private boolean waiting;
    private boolean sentBye;
    private boolean hidGui;
    private boolean prevHideGui;
    private int lastMouseX = Integer.MIN_VALUE;
    private int lastMouseY = Integer.MIN_VALUE;

    public DialogueScreen(DialoguePacket packet) {
        super(Component.translatable(NpcType.byId(packet.npc()).key("name")));
        apply(packet);
    }

    public int entityId() {
        return entityId;
    }

    public void update(DialoguePacket packet) {
        apply(packet);
        rewrap();
    }

    private void apply(DialoguePacket p) {
        this.entityId = p.entityId();
        this.npc = NpcType.byId(p.npc());
        this.affinity = p.affinity();
        this.text = p.text();
        this.extra = p.extra();
        this.options = p.options();
        this.page = 0;
        this.shown = 0;
        this.selected = 0;
        this.waiting = false;
    }

    @Override
    protected void init() {
        if (!hidGui && this.minecraft != null) {
            prevHideGui = this.minecraft.options.hideGui;
            this.minecraft.options.hideGui = true;
            hidGui = true;
        }
        rewrap();
    }

    private void rewrap() {
        if (this.font == null) {
            return;
        }
        int w = Math.max(120, this.width - 28);
        List<String> out = new ArrayList<>();
        for (FormattedText ft : this.font.getSplitter().splitLines(text, w, Style.EMPTY)) {
            out.add(ft.getString());
        }
        lines = out.isEmpty() ? List.of("") : out;
        extraLines = extra.getString().isEmpty() ? List.of() : this.font.split(extra, w);
    }

    // ------------------------------------------------------------ estado
    private int pageCount() {
        return Math.max(1, (lines.size() + LINES_PER_PAGE - 1) / LINES_PER_PAGE);
    }

    private boolean lastPage() {
        return page >= pageCount() - 1;
    }

    private int pageChars() {
        int n = 0;
        for (int i = page * LINES_PER_PAGE; i < Math.min(lines.size(), (page + 1) * LINES_PER_PAGE); i++) {
            n += lines.get(i).length();
        }
        return n;
    }

    private boolean typing() {
        return shown < pageChars();
    }

    private boolean showOptions() {
        return !typing() && lastPage() && !waiting && !options.isEmpty();
    }

    private int barHeight() {
        int extraH = extraLines.isEmpty() ? 0 : extraLines.size() * 10 + 2;
        return 22 + LINES_PER_PAGE * LINE_H + extraH + 6;
    }

    // ------------------------------------------------------------- tick
    @Override
    public void tick() {
        super.tick();
        Minecraft mc = this.minecraft;
        if (mc == null || mc.level == null || mc.player == null) {
            return;
        }
        Entity e = mc.level.getEntity(entityId);
        if (e == null || !e.isAlive() || mc.player.distanceToSqr(e) > 144.0D) {
            onClose();
            return;
        }
        if (typing()) {
            shown = Math.min(pageChars(), shown + CHARS_PER_TICK);
        }
    }

    /** Vira a câmera suavemente para o NPC, deixando ele um pouco à esquerda (as respostas ficam à direita). */
    private void faceNpc() {
        Minecraft mc = this.minecraft;
        if (mc == null || mc.level == null || mc.player == null) {
            return;
        }
        Entity e = mc.level.getEntity(entityId);
        if (e == null) {
            return;
        }
        LocalPlayer pl = mc.player;
        Vec3 eye = pl.getEyePosition();
        Vec3 target = e.getEyePosition().subtract(0.0D, 0.25D, 0.0D);
        double dx = target.x - eye.x;
        double dy = target.y - eye.y;
        double dz = target.z - eye.z;
        double h = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F + 12.0F;
        float pitch = (float) (-(Mth.atan2(dy, h) * (180.0D / Math.PI)));
        float f = Math.min(1.0F, mc.getDeltaFrameTime() * 0.3F);
        float newYaw = Mth.rotLerp(f, pl.getYRot(), yaw);
        float newPitch = Mth.lerp(f, pl.getXRot(), pitch);
        pl.setYRot(newYaw);
        pl.setXRot(newPitch);
        pl.yRotO = newYaw;
        pl.xRotO = newPitch;
        pl.setYHeadRot(newYaw);
        pl.yHeadRotO = newYaw;
    }

    // ----------------------------------------------------------- desenho
    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        faceNpc();
        int barH = barHeight();
        int barY = this.height - barH;
        g.fill(0, barY, this.width, this.height, COL_BAR);
        g.fill(0, barY, this.width, barY + 1, 0x40FFFFFF);

        int x = 12;
        int y = barY + 8;
        Component name = Component.translatable(npc.key("name"));
        g.drawString(this.font, name, x, y, COL_NAME, true);
        int nx = x + this.font.width(name) + 8;
        Component role = Component.translatable(npc.key("role"));
        g.drawString(this.font, role, nx, y, COL_SUB, true);
        nx += this.font.width(role) + 5;
        g.drawString(this.font, "·", nx, y, 0xFF7A7A7A, true);
        nx += this.font.width("·") + 5;
        g.drawString(this.font, Component.translatable("valdren.dialogue.your_affinity", Affinity.tierName(affinity)),
                nx, y, COL_SUB, true);

        int ty = y + 14;
        int budget = typing() ? (int) (shown + partialTick * CHARS_PER_TICK) : pageChars();
        int end = Math.min(lines.size(), (page + 1) * LINES_PER_PAGE);
        for (int i = page * LINES_PER_PAGE; i < end && budget > 0; i++) {
            String ln = lines.get(i);
            String part = ln.length() <= budget ? ln : ln.substring(0, budget);
            budget -= ln.length();
            g.drawString(this.font, part, x, ty, COL_TEXT, true);
            ty += LINE_H;
        }

        if (!typing() && !lastPage() && (System.currentTimeMillis() / 400L) % 2L == 0L) {
            g.drawString(this.font, "▼", this.width - 18, this.height - 14, COL_NAME, true);
        }
        if (!typing() && lastPage() && !extraLines.isEmpty()) {
            int ey = barY + 22 + LINES_PER_PAGE * LINE_H + 2;
            for (FormattedCharSequence seq : extraLines) {
                g.drawString(this.font, seq, x, ey, COL_EXTRA, true);
                ey += 10;
            }
        }
        if (showOptions()) {
            renderOptions(g, mouseX, mouseY);
        }
        super.render(g, mouseX, mouseY, partialTick);
    }

    private int optionWidth() {
        int w = 0;
        for (DialogueOption o : options) {
            w = Math.max(w, this.font.width(o.label()));
        }
        return w + 18;
    }

    private int optionX() {
        return this.width - optionWidth() - 14;
    }

    private int optionY(int index) {
        int total = options.size() * (OPT_H + OPT_GAP) - OPT_GAP;
        return this.height - barHeight() - 14 - total + index * (OPT_H + OPT_GAP);
    }

    private int hitOption(double mx, double my) {
        int bx = optionX();
        int bw = optionWidth();
        for (int i = 0; i < options.size(); i++) {
            int oy = optionY(i);
            if (mx >= bx && mx < bx + bw && my >= oy && my < oy + OPT_H) {
                return i;
            }
        }
        return -1;
    }

    private void renderOptions(GuiGraphics g, int mouseX, int mouseY) {
        if (mouseX != lastMouseX || mouseY != lastMouseY) {
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            int hit = hitOption(mouseX, mouseY);
            if (hit >= 0) {
                selected = hit;
            }
        }
        int bx = optionX();
        int bw = optionWidth();
        for (int i = 0; i < options.size(); i++) {
            DialogueOption o = options.get(i);
            int oy = optionY(i);
            boolean sel = i == selected;
            g.fill(bx, oy, bx + bw, oy + OPT_H, sel ? 0xE8202028 : 0xCC121216);
            g.renderOutline(bx, oy, bw, OPT_H, sel ? 0xFF9C9CAA : 0xFF33333D);
            g.drawString(this.font, o.label(), bx + 8, oy + 3, o.highlight() ? COL_OPT_HL : COL_OPT, true);
            if (sel) {
                g.drawString(this.font, "▶", bx - 10, oy + 3, 0xFFFFFFFF, true);
            }
        }
    }

    // ----------------------------------------------------------- entrada
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        if (showOptions()) {
            int hit = hitOption(mouseX, mouseY);
            if (hit >= 0) {
                choose(hit);
                return true;
            }
            return true;
        }
        advance();
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER
                || keyCode == GLFW.GLFW_KEY_SPACE || keyCode == GLFW.GLFW_KEY_E) {
            advance();
            return true;
        }
        if (showOptions()) {
            int n = options.size();
            if (keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_W) {
                selected = (selected - 1 + n) % n;
                click(1.3F);
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_S) {
                selected = (selected + 1) % n;
                click(1.3F);
                return true;
            }
            if (keyCode >= GLFW.GLFW_KEY_1 && keyCode <= GLFW.GLFW_KEY_9) {
                int k = keyCode - GLFW.GLFW_KEY_1;
                if (k < n) {
                    choose(k);
                }
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void advance() {
        if (typing()) {
            shown = pageChars();
        } else if (!lastPage()) {
            page++;
            shown = 0;
        } else if (showOptions()) {
            choose(Mth.clamp(selected, 0, options.size() - 1));
        }
    }

    private void choose(int index) {
        DialogueOption o = options.get(index);
        click(1.0F);
        if (o.action() == DialogueManager.BYE) {
            ModNetwork.sendToServer(new DialogueActionPacket(entityId, DialogueManager.BYE));
            sentBye = true;
            onClose();
            return;
        }
        waiting = true;
        ModNetwork.sendToServer(new DialogueActionPacket(entityId, o.action()));
    }

    private void click(float pitch) {
        if (this.minecraft != null) {
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.value(), pitch, 0.4F));
        }
    }

    @Override
    public void removed() {
        if (hidGui && this.minecraft != null) {
            this.minecraft.options.hideGui = prevHideGui;
            hidGui = false;
        }
        if (!sentBye) {
            sentBye = true;
            ModNetwork.sendToServer(new DialogueActionPacket(entityId, DialogueManager.BYE));
        }
        super.removed();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
