# Tarifa de Estacionamento — Verificação e Validação de Software (PUCRS)

Trabalhos da disciplina **Verificação e Validação de Software** da **PUCRS**, 2026/2.

O objeto de teste é a classe `CalculadoraTarifa`, que calcula o ticket de um estacionamento:
cortesia de 20 minutos, tarifa fixa até 1 hora, adicional por hora, pernoite e desconto VIP.
O desenvolvimento dos testes foi feito em duas fases.

| Fase | Trabalho | Técnicas | Marco no repositório |
|------|----------|----------|----------------------|
| 1 — Caixa-preta | Trabalho 1 | Particionamento em classes de equivalência e análise de valor limite, derivados da especificação. Os 89 casos revelaram 7 defeitos na primeira versão da implementação. | tag `fase-1-caixa-preta` |
| 2 — Caixa-branca | Trabalho 2 | Cobertura de código com [JaCoCo](https://www.jacoco.org/jacoco/) e teste de mutação com [PIT](https://pitest.org/), complementando o conjunto de casos de teste. | commits após a tag |

Para ver o repositório exatamente como ao fim da fase 1:

```bash
git checkout fase-1-caixa-preta
```

## Estrutura

- `t2_2026_2.pdf` — enunciado do Trabalho 2.
- `trab1/casos_de_teste.pdf` — projeto dos casos de teste (fase 1).
- `trab1/relatorio_defeitos.pdf` — defeitos encontrados na primeira versão (fase 1).
- `trab1/trabalho-estacionamento/` — projeto Maven (Java 17, JUnit 5):
  - `src/main/java` — versão corrigida da `CalculadoraTarifa`;
  - `versoes/versao1` — primeira versão, com defeitos;
  - `src/test` — drivers JUnit parametrizados (`@CsvFileSource`) e os CSVs com os casos.

## Como executar

Dentro de `trab1/trabalho-estacionamento`:

```bash
mvn test
```

Executa os testes sobre a versão corrigida e gera o relatório de cobertura em `target/site/jacoco/index.html`.

```bash
mvn test -Pv1
```

Executa os mesmos testes sobre a primeira versão (com defeitos).

```bash
mvn test-compile org.pitest:pitest-maven:mutationCoverage
```

Executa o teste de mutação e gera o relatório em `target/pit-reports/index.html`.

## Autores

- Nícolas Vargas ([@vargasnico](https://github.com/vargasnico))
- [@PauloTrevisan](https://github.com/PauloTrevisan)
