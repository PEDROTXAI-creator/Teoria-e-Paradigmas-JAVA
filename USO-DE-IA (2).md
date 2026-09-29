# USO-DE-IA.md

## Como usamos a IA

Usamos uma ferramenta de IA para ajudar a organizar o trabalho, escrever uma primeira versão do texto, criar os exemplos em Java, montar o PDF e preparar este arquivo. A IA ajudou bastante a começar, mas não foi aceita sem revisão. Nós lemos o material, testamos os programas e mudamos várias partes que não estavam boas.

Também usamos o OpenJDK 21 para compilar os exemplos e o Typst para gerar o PDF. O conteúdo sobre Java foi conferido na documentação oficial da Oracle.

## Partes geradas ou apoiadas pela IA

| Parte | Como a IA ajudou | O que nós conferimos e mudamos |
| --- | --- | --- |
| Estrutura do brief | A IA sugeriu separar o trabalho em quatro critérios e depois tratar escopos e tipos. | A primeira versão ficou grande demais e tinha uma capa e um sumário que ocupavam espaço. Reorganizamos tudo para caber em quatro páginas e deixamos um espaço para os nomes dos alunos. |
| Avaliação dos critérios | A IA sugeriu relacionar a tipagem de Java com confiabilidade, o uso de nomes e escopos com leitura do código, e a memória com custo. | Conferimos se cada ponto tinha algum exemplo ou teste. Também retiramos frases que pareciam apenas opinião e deixamos as avaliações mais ligadas aos programas executados. |
| Modelo do PDF | A IA montou uma primeira versão do PDF usando um modelo pronto. | Nós não gostamos do resultado inicial: havia uma linha no alto com “Java — brief técnico” e “Equipe”, além das fontes repetidas no final de cada página. Pedimos mudanças, retiramos essa linha, removemos as fontes do final das páginas e ajustamos a organização dos tópicos. |
| Nomes, escopos e closures | A IA criou o exemplo com o campo `x`, a variável local `x` e uma lambda que usa `base`. | Compilamos e executamos o programa. Também corrigimos uma sugestão inicial que tentava alterar dentro da lambda uma variável local capturada. Em Java, isso não funciona quando a variável não é final ou não permanece sem mudança. |
| Sistema de tipos | A IA ajudou a explicar tipagem estática, interfaces, genéricos, conversões e `var`. | Conferimos com exemplos. A IA tinha dado a entender que `var` deixava Java dinâmica, o que estava errado. Corrigimos: o compilador descobre o tipo, mas o tipo continua definido. |
| Memória e custo | A IA sugeriu explicar que a JVM cuida da memória e criar um pequeno programa para medir alocações. | Retiramos a ideia de usar `System.gc()` como prova, porque a coleta não acontece em um momento garantido. Mantivemos uma medição simples e explicamos que o tempo muda de acordo com o computador. |
| Exemplos executáveis | A IA escreveu os três programas e sugeriu os comandos para executá-los. | Compilamos todos com `javac -Xlint:all` e executamos cada um. Também testamos uma atribuição errada (`String texto = 1`) para confirmar que o compilador realmente acusa o erro. |
| Revisão final | A IA ajudou a conferir o texto e a paginação. | O PDF chegou a ficar com cinco páginas porque os exemplos passaram para uma página extra. Compactamos e reorganizamos os exemplos até voltar às quatro páginas exigidas. |

## Prompts principais usados

1. ```text
   Preciso fazer um brief técnico de quatro páginas sobre Java. Avalie a
   linguagem pelos critérios de ilegibilidade, redigibilidade, confiabilidade
   e custo. Também explique nomes, escopos, tempo de vida e sistema de tipos.
   Inclua exemplos curtos que possam ser executados.
   ```

2. ```text
   Explique o modelo de nomes, escopos e tempo de vida em Java com linguagem
   simples. Fale sobre variáveis locais, campos, shadowing, lambdas, closures,
   captura de variáveis e coleta de lixo. Mostre o que diferencia Java nesse
   assunto.
   ```

3. ```text
   Explique o sistema de tipos de Java de forma clara. Inclua tipagem estática,
   interfaces, herança, genéricos, inferência, boxing, unboxing e casts. Crie
   um exemplo pequeno que compile em OpenJDK 21.
   ```

4. ```text
   Crie três exemplos Java independentes: um sobre escopo e closure, um sobre
   sistema de tipos e um sobre custo de alocações. Informe os comandos para
   compilar e executar e avise quando uma medida de tempo puder variar.
   ```

5. ```text
   Monte o conteúdo em um PDF de exatamente quatro páginas, com espaço para
   os integrantes. Não coloque cabeçalho repetido no alto das páginas nem uma
   lista de fontes no final de cada página.
   ```

6. ```text
   Revise o texto e os exemplos. Encontre afirmações que pareçam opinião,
   erros sobre lambdas ou memória, exemplos que não compilariam e problemas
   de paginação. Sugira correções simples e confira se os exemplos executam.
   ```

## Como fizemos a conferência

Executamos estes comandos na pasta do projeto:

```bash
javac -encoding UTF-8 -Xlint:all -d build exemplos/*.java
java -cp build EscopoEClosure
java -cp build SistemaDeTipos
java -cp build CustoDeExecucao
```

As saídas principais foram:
```
local=3, bloco=4
campo=10
closure=12
ampliado=7
primeiro=1
referencia=42
desempacotado=42
n=200000
total-positivo=true
tempo-ms=29
```

O último tempo pode mudar em outra execução. O importante nesse exemplo é mostrar que o programa roda e que criar muitas strings tem um custo que pode ser medido.

## Auditoria de cada trecho gerado

Além da conferência geral acima, verificamos cada exemplo separadamente:

### 1. `EscopoEClosure.java`

- **Como verificamos:** compilamos com `javac -encoding UTF-8 -Xlint:all -d build exemplos/*.java` e executamos com `java -cp build EscopoEClosure`.
- **Resultado conferido:** `local=3, bloco=4`, `campo=10` e `closure=12`.
- **O que o resultado mostra:** Java diferencia a variável local `x` do campo `EscopoEClosure.x`; a lambda consegue usar `base` depois de ser criada.
- **Defeito encontrado:** uma versão inicial sugeria mudar dentro da lambda a variável local capturada. Isso não compila em Java quando a variável deixa de ser final ou efetivamente final. Retiramos essa alteração e deixamos a lambda apenas ler `base`.

### 2. `SistemaDeTipos.java`

- **Como verificamos:** compilamos junto com os outros arquivos usando `javac -encoding UTF-8 -Xlint:all -d build exemplos/*.java` e executamos com `java -cp build SistemaDeTipos`.
- **Resultado conferido:** `ampliado=7`, `primeiro=1`, `referencia=42` e `desempacotado=42`.
- **O que o resultado mostra:** Java permite ampliar `int` para `long`, usar a função genérica com `List<Number>`, tratar `Integer` como `Number` e fazer boxing/unboxing em situações permitidas.
- **Defeito encontrado:** uma explicação inicial dizia que `var` deixaria Java dinâmica. Corrigimos porque `var` só economiza a escrita do tipo local; o compilador continua definindo e conferindo esse tipo. Também testamos `String texto = 1`, que foi recusado pelo compilador como esperado.

### 3. `CustoDeExecucao.java`

- **Como verificamos:** compilamos com o mesmo comando e executamos com `java -cp build CustoDeExecucao`.
- **Resultado conferido:** `n=200000`, `total-positivo=true` e um `tempo-ms` não negativo; na execução registrada, o tempo foi `29` ms.
- **O que o resultado mostra:** o programa realmente cria muitas strings e mede o tempo gasto, mas o valor não é fixo e pode mudar conforme o computador.
- **Defeito encontrado:** uma sugestão inicial usava `System.gc()` como se fosse uma prova de que a memória havia sido liberada. Retiramos essa ideia, pois a coleta de lixo não acontece em um momento garantido. O exemplo final mede uma atividade real, sem apresentar o resultado como um benchmark definitivo.

O PDF foi compilado novamente depois das alterações e conferido visualmente. A versão final ficou com quatro páginas, sem a linha do alto e sem as fontes repetidas no final de cada página.

## O que aprendemos

- A IA é boa para dar um primeiro caminho, mas pode escrever mais do que cabe ou escolher um modelo visual que não combina com o trabalho.

- Sempre precisamos compilar os exemplos. Uma explicação que parece correta pode não funcionar em Java.

- Lambdas e memória exigem cuidado: uma variável sair do bloco não significa que o objeto foi apagado naquele momento.

- `var` não transforma Java em uma linguagem sem tipos.

- O resultado de uma medição de tempo não deve ser tratado como um número fixo da linguagem.

- Revisar o PDF também faz parte da atividade: não basta o texto estar correto se ele ultrapassa o limite de páginas ou fica visualmente confuso.