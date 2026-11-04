package estacionamento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class TarifaPermanenciaTest {

    private static final double DELTA = 0.001;

    private final CalculadoraTarifa calculadora = new CalculadoraTarifa();

    // Verifica o cálculo da tarifa conforme o tempo de permanência.
    @ParameterizedTest(name = "{0} - {1}")
    @CsvFileSource(resources = "/casos_permanencia.csv", numLinesToSkip = 1)
    void deveCalcularValorConformePermanencia(String id, String descricao,
            int diaE, int mesE, int anoE, int horaE, int minE,
            int diaS, int mesS, int anoS, int horaS, int minS,
            boolean vip, double esperado) {

        double real = calculadora.calcular(diaE, mesE, anoE, horaE, minE,
                                           diaS, mesS, anoS, horaS, minS, vip);

        assertEquals(esperado, real, DELTA, id + " - " + descricao);
    }
}
