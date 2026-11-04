package estacionamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

// Casos derivados da análise de cobertura (JaCoCo) sobre calcular(LocalDateTime, LocalDateTime, boolean).
class TarifaCoberturaTest {

    private static final double DELTA = 0.001;

    private final CalculadoraTarifa calculadora = new CalculadoraTarifa();

    // Verifica o cálculo da tarifa a partir de data/hora completas (inclui segundos).
    @ParameterizedTest(name = "{0} - {1}")
    @CsvFileSource(resources = "/casos_cobertura_validos.csv", numLinesToSkip = 1)
    void deveCalcularValorComDataHora(String id, String descricao,
            LocalDateTime entrada, LocalDateTime saida,
            boolean vip, double esperado) {

        double real = calculadora.calcular(entrada, saida, vip);

        assertEquals(esperado, real, DELTA, id + " - " + descricao);
    }

    // Verifica se data/hora ausente (null) gera IllegalArgumentException.
    @ParameterizedTest(name = "{0} - {1}")
    @CsvFileSource(resources = "/casos_cobertura_invalidos.csv", numLinesToSkip = 1)
    void deveRejeitarDataHoraAusente(String id, String descricao,
            LocalDateTime entrada, LocalDateTime saida,
            boolean vip) {

        assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcular(entrada, saida, vip),
                id + " - " + descricao);
    }
}
