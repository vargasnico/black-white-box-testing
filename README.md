# Black-box and White-box Testing

A software verification and validation study built around a real business rule: calculating a parking lot fee
(20-minute grace period, flat rate up to 1 hour, hourly surcharge, overnight stay and VIP discount),
implemented in the Java class `CalculadoraTarifa`.

The test suite was built in two phases. First, test cases were designed from the specification alone
(black-box). Then those cases were evaluated and extended based on the code (white-box).

## Results

| Phase | Techniques | Result | Milestone |
|-------|------------|--------|-----------|
| 1 — Black-box | Equivalence class partitioning and boundary value analysis | 89 test cases; 7 defects found in the first version of the implementation | tag `fase-1-caixa-preta` |
| 2 — White-box | Code coverage ([JaCoCo](https://www.jacoco.org/jacoco/)), MC/DC and mutation testing ([PIT](https://pitest.org/)) | 98 cases; 100% instruction, line and branch coverage; 42 of 44 mutants killed (the remaining 2 are equivalent) | tag `fase-2-caixa-branca` |

Key findings from phase 2:

- 100% coverage does not guarantee effective tests. Every case executed the truncation of seconds,
  but none of them checked its effect.
- PIT's default mutation operators hid a validation that could be removed without any test failing.
  Conditional removal (`STRONGER`) exposed the problem.
- The equivalent mutants pointed to a redundant branch in the code.
- Only 1 of the 7 real defects from phase 1 matches a PIT mutation operator. Mutation analysis does not
  replace boundary value design.

## Structure

Maven project (Java 17, JUnit 5):

```
src/main/java/         Fixed version of CalculadoraTarifa
src/test/java/         Parameterized JUnit drivers (@CsvFileSource)
src/test/resources/    Test cases as CSV (CT01–CT98)
versoes/versao1/       First version, with defects
```

## Running

Requires JDK 17 or later and Maven 3.9.

```bash
mvn test
```

Runs the 98 cases against the fixed version and generates the coverage report at `target/site/jacoco/index.html`.

```bash
mvn test -Pv1
```

Runs the same cases against the first version, with defects.

```bash
mvn test-compile org.pitest:pitest-maven:mutationCoverage
```

Runs mutation testing and generates the report at `target/pit-reports/index.html`.

To see the project as it was at the end of phase 1:

```bash
git checkout fase-1-caixa-preta
```

## Context

Developed as Assignments 1 and 2 of the Software Verification and Validation course at PUCRS (2026/2).
The code, test cases and CSV files are in Portuguese.

## Authors

- Nícolas Vargas ([@vargasnico](https://github.com/vargasnico))
- Paulo Trevisan ([@PauloTrevisan](https://github.com/PauloTrevisan))
