# Testes caixa-preta e caixa-branca

Estudo de verificação e validação de software sobre uma regra de negócio real: o cálculo da tarifa de um
estacionamento (cortesia de 20 minutos, tarifa fixa até 1 hora, adicional por hora, pernoite e desconto VIP),
implementado na classe Java `CalculadoraTarifa`.

O conjunto de testes foi construído em duas fases. Primeiro, os casos foram projetados só a partir da
especificação (caixa-preta). Depois, esses casos foram avaliados e complementados com base no código
(caixa-branca).

## Resultados

| Fase | Técnicas | Resultado | Marco |
|------|----------|-----------|-------|
| 1 — Caixa-preta | Particionamento em classes de equivalência e análise de valor limite | 89 casos de teste; 7 defeitos encontrados na primeira versão da implementação | tag `fase-1-caixa-preta` |
| 2 — Caixa-branca | Cobertura de código ([JaCoCo](https://www.jacoco.org/jacoco/)), MC/DC e teste de mutação ([PIT](https://pitest.org/)) | 98 casos; 100% de instruções, linhas e ramos; 42 de 44 mutantes mortos (os 2 restantes são equivalentes) | tag `fase-2-caixa-branca` |

Alguns achados da fase 2:

- Cobertura de 100% não garante testes eficazes. O truncamento de segundos era executado por todos os casos,
  mas nenhum verificava o seu efeito.
- O conjunto padrão de operadores do PIT escondia uma validação que podia ser removida sem nenhum teste
  falhar. A remoção de condicionais (`STRONGER`) revelou o problema.
- Os mutantes equivalentes apontaram um ramo redundante no código.
- Só 1 dos 7 defeitos reais da fase 1 corresponde a um operador de mutação do PIT. A análise de mutação
  não substitui o projeto por valor limite.

## Estrutura

```
trab1/casos_de_teste.pdf              Fase 1: projeto dos casos de teste
trab1/relatorio_defeitos.pdf          Fase 1: defeitos encontrados na primeira versão
trab1/trabalho-estacionamento/        Projeto Maven (Java 17, JUnit 5)
  src/main/java/                      Versão corrigida da CalculadoraTarifa
  versoes/versao1/                    Primeira versão, com defeitos
  src/test/java/                      Drivers JUnit parametrizados (@CsvFileSource)
  src/test/resources/                 Casos de teste em CSV (CT01–CT98)
```

## Como executar

Requisitos: JDK 17 ou superior e Maven 3.9. Os comandos são executados dentro de `trab1/trabalho-estacionamento`.

```bash
mvn test
```

Executa os 98 casos sobre a versão corrigida e gera o relatório de cobertura em `target/site/jacoco/index.html`.

```bash
mvn test -Pv1
```

Executa os mesmos casos sobre a primeira versão, com defeitos.

```bash
mvn test-compile org.pitest:pitest-maven:mutationCoverage
```

Executa o teste de mutação e gera o relatório em `target/pit-reports/index.html`.

Para ver o projeto como estava ao fim da fase 1:

```bash
git checkout fase-1-caixa-preta
```

## Contexto

Desenvolvido como os Trabalhos 1 e 2 da disciplina Verificação e Validação de Software da PUCRS (2026/2).

## Autores

- Nícolas Vargas ([@vargasnico](https://github.com/vargasnico))
- Paulo Trevisan ([@PauloTrevisan](https://github.com/PauloTrevisan))
