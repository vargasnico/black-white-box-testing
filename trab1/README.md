# Verificação e Validação de Software — Trabalho 1

Testes funcionais (particionamento em classes de equivalência e análise de valor limite) da classe
`CalculadoraTarifa`, que calcula a tarifa de um estacionamento (cortesia, tarifa fixa, horas adicionais,
pernoite e desconto VIP).

- `trabalho-estacionamento/` — projeto Maven (Java 17, JUnit 5) com 89 casos de teste parametrizados.
- `casos_de_teste.pdf` — projeto dos casos de teste.
- `relatorio_defeitos.pdf` — defeitos encontrados na primeira versão da implementação.

```bash
cd trabalho-estacionamento
mvn test        # versão corrigida (src/main/java)
mvn test -Pv1   # primeira versão, com defeitos (versoes/versao1)
```

Continuação: Trabalho 2 (cobertura de código com JaCoCo e teste de mutação com PIT).
