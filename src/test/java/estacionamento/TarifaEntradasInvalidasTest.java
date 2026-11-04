package estacionamento;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class TarifaEntradasInvalidasTest {

    private final CalculadoraTarifa calculadora = new CalculadoraTarifa();

    // Verifica se entradas inválidas geram IllegalArgumentException.
    @ParameterizedTest(name = "{0} - {1}")
    @CsvFileSource(resources = "/casos_invalidos.csv", numLinesToSkip = 1)
    void deveRejeitarEntradasInvalidas(String id, String descricao,
            int diaE, int mesE, int anoE, int horaE, int minE,
            int diaS, int mesS, int anoS, int horaS, int minS,
            boolean vip) {

        assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcular(diaE, mesE, anoE, horaE, minE,
                                           diaS, mesS, anoS, horaS, minS, vip),
                id + " - " + descricao);
    }
}
