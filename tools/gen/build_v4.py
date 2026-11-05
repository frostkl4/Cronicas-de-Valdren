"""Gera o Vorthrax v4: StormDragonModel.java, texturas (storm_dragon.png e _glow), .bbmodel e prévias.

  python3 build_v4.py

A geometria está em v4_dragon.py e a pintura em v4_paint.py. Só o dragão é gerado aqui.
"""
import os

from boxmodel import save_bbmodel, camel, jf
import v4_dragon as D
from v4_paint import painter
import v4_preview as P

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", ".."))
JAVA = os.path.join(ROOT, "src/main/java/com/frostkl4/valdren/client/model/StormDragonModel.java")
TEX = os.path.join(ROOT, "src/main/resources/assets/valdren/textures/entity")
BB = os.path.join(ROOT, "tools/models/storm_dragon.bbmodel")
PREV = os.path.join(ROOT, "docs/preview")

WING_BONES = ["", "_fore", "_wrist", "_f0", "_f1", "_f2", "_f3", "_f0_tip", "_f1_tip", "_f2_tip", "_f3_tip"]
NECK = [f"neck{k}" for k in range(1, 6)]
TAIL = [f"tail{k}" for k in range(1, 10)]
LEGS = [f"leg_{a}{s}{b}" for a in "fb" for s in "rl" for b in
        (("", "_fore", "_hand") if a == "f" else ("", "_shin", "_meta", "_foot"))]
FIELDS = ["body", "head", "jaw", "tail_tip"] + NECK + TAIL + LEGS + \
         [f"wing_{s}{b}" for s in "lr" for b in WING_BONES]


def pose_rows(pose):
    rows = []
    for b in WING_BONES:
        x, y, z = pose.get("wing_l" + b, (0.0, 0.0, 0.0))
        rows.append(f"            {{{jf(x)}, {jf(y)}, {jf(z)}}}")
    return ",\n".join(rows)


def layer_methods(model):
    """createBodyLayer dividido em métodos (um por filho do corpo) para não estourar o limite de 64 KB."""
    body = model.roots[0]

    def cubes(p):
        cb = "CubeListBuilder.create()"
        for c in p.cubes:
            x, y, z = c.o
            w, h, d = c.s
            cb += (f"\n                .texOffs({c.uv[0]}, {c.uv[1]}).addBox({jf(x)}, {jf(y)}, {jf(z)}, "
                   f"{jf(w)}, {jf(h)}, {jf(d)}, CubeDeformation.NONE)")
        return cb

    def pose(p):
        px, py, pz = p.pivot
        rx, ry, rz = p.rot
        return f"PartPose.offsetAndRotation({jf(px)}, {jf(py)}, {jf(pz)}, {jf(rx)}, {jf(ry)}, {jf(rz)})"

    def emit(p, parent_var, out):
        var = "p_" + p.name
        prefix = f"PartDefinition {var} = " if p.children else ""
        out.append(f"        {prefix}{parent_var}.addOrReplaceChild(\"{p.name}\", {cubes(p)}, {pose(p)});")
        for ch in p.children:
            emit(ch, var, out)

    main = ["        MeshDefinition mesh = new MeshDefinition();",
            "        PartDefinition root = mesh.getRoot();",
            f"        PartDefinition p_body = root.addOrReplaceChild(\"body\", {cubes(body)}, {pose(body)});"]
    helpers = []
    for ch in body.children:
        mname = "add" + camel(ch.name)[0].upper() + camel(ch.name)[1:]
        main.append(f"        {mname}(p_body);")
        lines = []
        emit(ch, "p_body", lines)
        helpers.append(f"    private static void {mname}(PartDefinition p_body) {{\n" + "\n".join(lines) + "\n    }")
    main.append(f"        return LayerDefinition.create(mesh, {model.tw}, {model.th});")
    return "\n".join(main), "\n\n".join(helpers)


TEMPLATE = """package com.frostkl4.valdren.client.model;

import com.frostkl4.valdren.Valdren;
import com.frostkl4.valdren.entity.boss.StormDragon;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Vorthrax (v4): dragão de gelo e raio. Modelo gerado por tools/gen/build_v4.py
 * (geometria em v4_dragon.py, pintura em v4_paint.py); tools/models/storm_dragon.bbmodel abre no Blockbench.
 *
 * Só visual: lê do dragão apenas o que ele já expõe (voando, rugido, golpe, dano, morte, olhar).
 */
public class StormDragonModel extends HierarchicalModel<StormDragon> {{
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(new ResourceLocation(Valdren.MODID, "storm_dragon"), "main");

    /** Ordem dos ossos da asa: úmero, antebraço, pulso, dedos 0-3, pontas dos dedos 0-3 (lado esquerdo). */
    private static final float[][] WING_GROUND = {{
{ground}
    }};
    private static final float[][] WING_FLY = {{
{fly}
    }};
    /** Transição suave entre pousado e voando, guardada por dragão (só no cliente). */
    private static final Map<StormDragon, float[]> FLIGHT = new WeakHashMap<>();

    private final ModelPart root;
{fields}
    private final ModelPart[] wingBonesL;
    private final ModelPart[] wingBonesR;
    private final ModelPart[] neck;
    private final ModelPart[] tail;

    public StormDragonModel(ModelPart root) {{
        this.root = root;
{assign}
        this.wingBonesL = new ModelPart[]{{{wing_l}}};
        this.wingBonesR = new ModelPart[]{{{wing_r}}};
        this.neck = new ModelPart[]{{{neck}}};
        this.tail = new ModelPart[]{{{tail}}};
    }}

    public static LayerDefinition createBodyLayer() {{
{layer}
    }}

{helpers}

    @Override
    public ModelPart root() {{
        return this.root;
    }}

    private static float flightBlend(StormDragon entity, float ageInTicks) {{
        float target = entity.isFlying() ? 1.0F : 0.0F;
        float[] s = FLIGHT.computeIfAbsent(entity, e -> new float[]{{target, ageInTicks}});
        float dt = Mth.clamp(ageInTicks - s[1], 0.0F, 4.0F);
        s[1] = ageInTicks;
        s[0] += Mth.clamp(target - s[0], -dt * 0.07F, dt * 0.07F);
        return s[0];
    }}

    private static void sym(ModelPart l, ModelPart r, float x, float y, float z) {{
        l.xRot += x;
        l.yRot += y;
        l.zRot += z;
        r.xRot += x;
        r.yRot -= y;
        r.zRot -= z;
    }}

    @Override
    public void setupAnim(StormDragon entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {{
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float t = ageInTicks;
        float partial = ageInTicks - entity.tickCount;
        float fly = flightBlend(entity, ageInTicks);
        fly = fly * fly * (3.0F - 2.0F * fly);
        float ground = 1.0F - fly;
        float yaw = netHeadYaw * Mth.DEG_TO_RAD;
        float pitch = headPitch * Mth.DEG_TO_RAD;
        float hurt = entity.hurtTime > 0 ? (entity.hurtTime - partial) / 10.0F : 0.0F;
        float death = entity.deathTime > 0 ? Math.min(1.0F, (entity.deathTime + partial) / 20.0F) : 0.0F;
        float roar = entity.getRoarAmount(partial);
        float bite = Mth.sin(this.attackTime * Mth.PI);
        float breathe = Mth.sin(t * 0.07F);
        float amt = Math.min(limbSwingAmount, 1.0F) * ground;
        float swing = limbSwing * 0.35F;
        float f = t * 0.3F;
        float flap = Mth.sin(f);

        // ---- asas: pose pousada (erguidas, caindo como manto) <-> asas abertas
        for (int i = 0; i < this.wingBonesL.length; i++) {{
            sym(this.wingBonesL[i], this.wingBonesR[i], Mth.lerp(fly, WING_GROUND[i][0], WING_FLY[i][0]),
                    Mth.lerp(fly, WING_GROUND[i][1], WING_FLY[i][1]), Mth.lerp(fly, WING_GROUND[i][2], WING_FLY[i][2]));
        }}
        // batida: o antebraço, o pulso e as pontas seguem com atraso
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, Mth.cos(f) * 0.12F * fly, flap * 0.7F * fly);
        sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, 0.0F, Mth.sin(f - 0.6F) * 0.35F * fly);
        sym(this.wingBonesL[2], this.wingBonesR[2], 0.0F, 0.0F, Mth.sin(f - 1.1F) * 0.25F * fly);
        for (int k = 0; k < 4; k++) {{
            sym(this.wingBonesL[3 + k], this.wingBonesR[3 + k], 0.0F, Mth.sin(f - 1.2F) * 0.03F * k * fly, 0.0F);
            sym(this.wingBonesL[7 + k], this.wingBonesR[7 + k], 0.0F, 0.0F,
                    Mth.sin(f - 1.5F - k * 0.1F) * 0.15F * fly + Mth.sin(t * 0.05F - k * 0.4F) * 0.04F * ground);
        }}
        // pousado: respiração e balanço ao andar
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, 0.0F, breathe * 0.025F * ground + Mth.sin(swing * 2.0F) * 0.04F * amt);
        sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, breathe * 0.03F * ground, 0.0F);
        // rugido no chão: as asas se abrem
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, roar * 0.3F * ground, -roar * 0.35F * ground);
        sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, roar * 0.9F * ground, 0.0F);
        for (int k = 0; k < 4; k++) {{
            sym(this.wingBonesL[3 + k], this.wingBonesR[3 + k], 0.0F, -roar * 0.15F * k * ground, 0.0F);
        }}
        this.body.y += -flap * 1.4F * fly + breathe * 0.5F * ground;

        // ---- pescoço em S, olhar e mordida
        for (int i = 0; i < this.neck.length; i++) {{
            this.neck[i].yRot += yaw * 0.16F + Mth.sin(t * 0.045F - i * 0.5F) * 0.03F * ground;
            this.neck[i].xRot += Mth.sin(t * 0.07F - i * 0.6F) * 0.02F;
        }}
        this.head.yRot += yaw * 0.2F;
        this.neck[0].yRot += Mth.sin(swing) * 0.05F * amt;
        this.neck[0].xRot += pitch * 0.15F - roar * 0.45F + hurt * 0.25F + bite * 0.35F + fly * 0.2F;
        this.neck[1].xRot += -roar * 0.1F;
        this.neck[2].xRot += bite * 0.1F;
        this.neck[3].xRot += roar * 0.15F;
        this.head.xRot += pitch * 0.35F - roar * 0.15F - hurt * 0.3F + bite * 0.1F - fly * 0.15F;
        this.jaw.xRot += 0.05F + (breathe * 0.5F + 0.5F) * 0.04F + bite * 0.85F + roar * 0.95F + hurt * 0.4F;

        // ---- cauda em onda
        for (int i = 0; i < this.tail.length; i++) {{
            this.tail[i].yRot += Mth.sin(t * 0.08F - i * 0.45F) * (0.035F + 0.012F * i)
                    + Mth.sin(swing - i * 0.5F) * 0.04F * amt;
            this.tail[i].xRot += Mth.sin(t * 0.06F - i * 0.4F) * 0.015F + Mth.sin(f - 1.0F - i * 0.4F) * 0.03F * fly;
        }}
        this.tail[0].xRot += 0.12F * fly;

        // ---- pernas: passada no chão, encolhidas no voo
        float a = Mth.cos(swing) * 0.5F * amt;
        float b = Mth.cos(swing + Mth.PI) * 0.5F * amt;
        this.legFl.xRot += a + 0.9F * fly;
        this.legFr.xRot += b + 0.9F * fly;
        this.legFlFore.xRot += Math.max(0.0F, -a) * 0.7F + 0.6F * fly;
        this.legFrFore.xRot += Math.max(0.0F, -b) * 0.7F + 0.6F * fly;
        this.legFlHand.xRot += -a * 0.6F + 0.5F * fly;
        this.legFrHand.xRot += -b * 0.6F + 0.5F * fly;
        this.legBr.xRot += a + 1.0F * fly;
        this.legBl.xRot += b + 1.0F * fly;
        this.legBrShin.xRot += Math.max(0.0F, a) * 0.5F + 0.4F * fly;
        this.legBlShin.xRot += Math.max(0.0F, b) * 0.5F + 0.4F * fly;
        this.legBrMeta.xRot += -Math.max(0.0F, a) * 0.3F + 0.6F * fly;
        this.legBlMeta.xRot += -Math.max(0.0F, b) * 0.3F + 0.6F * fly;
        this.legBrFoot.xRot += -a * 0.6F + 0.5F * fly;
        this.legBlFoot.xRot += -b * 0.6F + 0.5F * fly;
        this.body.zRot += Mth.sin(swing) * 0.03F * amt;

        // ---- dano: recua a cabeça e as asas tremem
        sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, 0.0F, -hurt * 0.15F);

        // ---- morte: desaba, pescoço cai, asas caem abertas
        if (death > 0.0F) {{
            this.body.y += death * 8.0F * ground;
            this.legFl.zRot -= death * 0.5F;
            this.legFr.zRot += death * 0.5F;
            this.legBl.zRot -= death * 0.5F;
            this.legBr.zRot += death * 0.5F;
            this.neck[0].xRot += death * 0.9F;
            this.neck[1].xRot += death * 0.2F;
            this.head.xRot += death * 0.3F;
            this.jaw.xRot += death * 0.45F;
            sym(this.wingBonesL[0], this.wingBonesR[0], 0.0F, 0.0F, death * 0.9F);
            sym(this.wingBonesL[1], this.wingBonesR[1], 0.0F, death * 0.6F, 0.0F);
            this.tail[0].xRot -= death * 0.1F;
        }}
    }}
}}
"""


def main():
    m = D.dragon_v4()
    tex, glow = m.paint(painter, glow=True)
    os.makedirs(TEX, exist_ok=True)
    tex.save(os.path.join(TEX, "storm_dragon.png"))
    glow.save(os.path.join(TEX, "storm_dragon_glow.png"))
    names = {p.name for p in m.parts()}
    missing = [n for n in FIELDS if n not in names]
    if missing:
        raise SystemExit(f"ossos ausentes: {missing}")
    fields, assign = m.java_fields(set(FIELDS))
    layer, helpers = layer_methods(m)
    src = TEMPLATE.format(
        ground=pose_rows(D.POSE_GROUND), fly=pose_rows(D.POSE_FLY), fields=fields, assign=assign,
        wing_l=", ".join("this." + camel("wing_l" + b) for b in WING_BONES),
        wing_r=", ".join("this." + camel("wing_r" + b) for b in WING_BONES),
        neck=", ".join("this." + camel(n) for n in NECK), tail=", ".join("this." + camel(n) for n in TAIL),
        layer=layer, helpers=helpers)
    with open(JAVA, "w") as fh:
        fh.write(src)
    save_bbmodel(m, tex, BB)
    os.makedirs(PREV, exist_ok=True)
    P.main(os.path.join(PREV, "storm_dragon.png"), os.path.join(PREV, "storm_dragon_hero.png"))
    print(f"ok storm_dragon v4: {sum(1 for _ in m.cubes())} cubos, textura {m.tw}x{m.th}")


if __name__ == "__main__":
    main()
