# Crônicas de Valdren (Forge 1.20.1)

Mod de aventura feito para jogar sozinho: viajantes com falas e missões, criaturas, dois chefes,
oito espadas e um guia no formato do livro do Minecraft. Compatível com **Epic Fight** (opcional).

## O que tem na versão 0.1

**Viajantes** (aparecem perto do sino das vilas, cada um no seu horário, e vão embora quando o horário acaba)

| NPC | Profissão | Horário | Missões |
|---|---|---|---|
| Bram Martelo-Fundo | Ferreiro | manhã e começo da tarde | lobos, saqueadores, ferro, carvão |
| Dona Iara | Herbalista | tarde | trigo, cristais gélidos, frutas, cogumelos |
| Mestre Orvyn | Cronista | entardecer | fragmentos de eco, Vorthrax, essências, livros |
| Ravena | Caçadora | noite | zumbis, espectros, Cavaleiro Sem Luz, matilha |

Cada um tem falas próprias (saudações, 4 histórias de lore, respostas por nível de afinidade),
trocas que melhoram com a afinidade e missões únicas e diárias.

**Tela de conversa**: igual à referência. O mundo continua visível, a câmera vira para o NPC, a faixa de baixo
mostra nome, profissão e afinidade com efeito de digitação, e as respostas ficam à direita. Respostas em amarelo
têm novidade. Controles: mouse, setas/W/S, Enter/Espaço/E, teclas 1 a 9 e Esc.

**Afinidade**: Desconhecido → Conhecido → Camarada → Amigo → Confidente. Sobe conversando uma vez por dia,
ouvindo histórias pela primeira vez, negociando e cumprindo missões.

**Criaturas**: Lobo Sombrio (florestas/taigas à noite), Espectro Gélido (terras nevadas, atira gelo, fraco contra
fogo), Saqueador Carmesim (planícies/savanas à noite, humanoide com espada).

**Chefes**
- **Cavaleiro Sem Luz** (Selo Partido): investida sombria, golpe do eclipse em área com escuridão, fúria abaixo de 50%
  chamando saqueadores. Solta a Grande Espada do Sentinela.
- **Vorthrax, o Dragão da Tempestade** (Chifre da Tempestade, a céu aberto): mordida e rabada no chão; no ar, orbes
  de tempestade, chuva de raios marcada no chão e mergulho com impacto. Solta o Fio da Tempestade.

**Espadas** (cada uma com efeito ao acertar): Lâmina da Geada, Fio da Tempestade, Presa Carmesim, Espada do Bosque
Antigo, Lâmina do Crepúsculo, Chama Ancestral, Grande Espada do Sentinela, Katana do Luar.

**Guia**: "Crônicas de Valdren", entregue ao entrar no mundo pela primeira vez. Usa a tela de livro do Minecraft.

## Epic Fight

- `data/valdren/capabilities/weapons/*.json`: moveset de cada espada (espada longa, espada, tachi, espadão, katana).
- `data/valdren/epicfight_mobpatch/*.json`: Saqueador Carmesim e Cavaleiro Sem Luz usam o preset de zumbi do Epic
  Fight, então lutam com os golpes animados dele.
- Sem o Epic Fight, esses arquivos são ignorados e tudo funciona no combate normal.

## Estrutura

- `src/main/java/com/frostkl4/valdren/`: código (NPCs, conversa, missões, criaturas, chefes, itens, tela)
- `src/main/resources/`: texturas, idiomas (`pt_br` e `en_us`), receitas, loot, spawns, Epic Fight
- `tools/gen/`: geradores em Python dos modelos, texturas e textos (`python3 build_models.py`, `build_skins.py`,
  `build_items.py`, `build_data.py`, e `check.py` para conferir tudo)
- `tools/models/*.bbmodel`: modelos das criaturas para abrir no Blockbench
- `docs/preview/`: prévias das texturas e modelos

Para compilar pelo celular, veja `COMO_COMPILAR.md`.

## Ideias para as próximas versões

- Casinha que aparece no mundo com NPCs próprios
- Mais NPCs, criaturas e chefes; correntes de missões maiores
- Diário de missões no guia
