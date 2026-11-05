# Como compilar pelo celular

Quem compila é o **GitHub Actions**, de graça, nos servidores do GitHub. Você não precisa de PC.

## Primeira vez (repositório Cronicas-de-Valdren)

1. No navegador do celular, abra `github.com/frostkl4/Cronicas-de-Valdren`.
2. Toque em **Add file → Create new file**.
   No nome do arquivo, digite exatamente: `.github/workflows/build.yml`
   Cole o conteúdo do arquivo `build.yml` que veio junto com o ZIP e toque em **Commit changes**.
3. Toque em **Add file → Upload files** e envie o arquivo `cronicas-de-valdren.zip` **sem extrair**.
   O build abre o ZIP sozinho. Toque em **Commit changes**.
4. Em **Settings → Actions → General → Workflow permissions**, marque **Read and write permissions** e salve.
   Sem isso o build não consegue publicar o `.jar` em Releases.
5. Abra a aba **Actions**. O build "Build Cronicas de Valdren" leva de 4 a 8 minutos.
   Se ele começou antes do passo 4, toque nele e em **Re-run jobs**.
6. Quando ficar verde ✅, abra **Releases → Crônicas de Valdren (build mais recente)** e baixe o
   `valdren-0.1.0-mc1.20.1.jar`. (Também fica em **Actions → o build → Artifacts**.)
7. Coloque o `.jar` na pasta `mods` do Minecraft Forge 1.20.1. O Epic Fight é opcional, mas recomendado.

Se o build ficar vermelho ❌, toque no build, depois no passo **Build**, e copie as linhas com `error:`.
Me mande essas linhas que eu corrijo.

## Atualizando depois

Quando eu te mandar um ZIP novo, é só enviar por cima com **Upload files** (mesmo nome de arquivo).
O build roda de novo sozinho.

## Mudar uma textura

As texturas ficam dentro do ZIP em `valdren/src/main/resources/assets/valdren/textures/`:

- `item/<nome>.png`: ícones dos itens (32x32)
- `entity/npc/<nome>.png`: skins dos NPCs (formato de skin de jogador 64x64)
- `entity/<criatura>.png`: texturas das criaturas; `<criatura>_glow.png` é a parte que brilha no escuro

Dá para editar no celular com Pixel Studio, Resprite ou qualquer editor de pixel art. Mantenha o mesmo tamanho.
Para ver onde cada parte do modelo fica na textura, abra `valdren/tools/models/<criatura>.bbmodel` no
Blockbench Web (`web.blockbench.net`, funciona no navegador do celular).
