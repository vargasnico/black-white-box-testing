package estacionamento;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalculadoraTarifa {

    public static final double TARIFA_FIXA = 15.00;
    public static final double ADICIONAL_POR_HORA = 5.00;
    public static final double TARIFA_PERNOITE = 50.00;
    public static final double DESCONTO_VIP = 0.50;
    public static final int MINUTOS_CORTESIA = 20;
    public static final int MINUTOS_POR_HORA = 60;

    private static final LocalTime ABERTURA = LocalTime.of(8, 0);
    private static final LocalTime FECHAMENTO = LocalTime.of(2, 0);

    // Calcula a tarifa a partir dos valores de data e horário informados.
    public double calcular(int diaEntrada, int mesEntrada, int anoEntrada, int horaEntrada, int minutoEntrada,
                           int diaSaida, int mesSaida, int anoSaida, int horaSaida, int minutoSaida,
                           boolean vip) {
        LocalDateTime entrada = criarDataHora(diaEntrada, mesEntrada, anoEntrada, horaEntrada, minutoEntrada);
        LocalDateTime saida = criarDataHora(diaSaida, mesSaida, anoSaida, horaSaida, minutoSaida);
        return calcular(entrada, saida, vip);
    }

    // Calcula a tarifa considerando permanência, pernoite e desconto VIP.
    public double calcular(LocalDateTime entrada, LocalDateTime saida, boolean vip) {
        if (entrada == null || saida == null) {
            throw new IllegalArgumentException("Data/hora de entrada e de saida sao obrigatorias.");
        }

        entrada = entrada.truncatedTo(ChronoUnit.MINUTES);
        saida = saida.truncatedTo(ChronoUnit.MINUTES);

        validarEntrada(entrada);
        validarSaida(saida);

        if (saida.isBefore(entrada)) {
            throw new IllegalArgumentException("A saida nao pode ser anterior a entrada.");
        }

        long minutos = ChronoUnit.MINUTES.between(entrada, saida);

        if (minutos <= MINUTOS_CORTESIA) {
            return 0.0;
        }

        double valor;
        int pernoites = contarPernoites(entrada, saida);

        if (pernoites > 0) {
            valor = pernoites * TARIFA_PERNOITE;
        } else if (minutos <= MINUTOS_POR_HORA) {
            valor = TARIFA_FIXA;
        } else {
            valor = TARIFA_FIXA
                    + ADICIONAL_POR_HORA
                    * divisaoParaCima(minutos - MINUTOS_POR_HORA, MINUTOS_POR_HORA);
        }

        return vip ? valor * (1 - DESCONTO_VIP) : valor;
    }

    // Valida se o horário de entrada é permitido.
    private static void validarEntrada(LocalDateTime entrada) {
        if (entrada.toLocalTime().isBefore(ABERTURA)) {
            throw new IllegalArgumentException("Entrada nao permitida antes das 08:00.");
        }
    }

    // Valida se o horário de saída é permitido.
    private static void validarSaida(LocalDateTime saida) {
        LocalTime hora = saida.toLocalTime();

        if (!hora.isBefore(FECHAMENTO) && hora.isBefore(ABERTURA)) {
            throw new IllegalArgumentException("Saida nao permitida entre 02:00 e 07:59.");
        }
    }

    // Conta a quantidade de pernoites durante a permanência.
    private static int contarPernoites(LocalDateTime entrada, LocalDateTime saida) {
        int pernoites = 0;
        LocalDateTime marco = entrada.toLocalDate().plusDays(1).atTime(ABERTURA);

        while (saida.isAfter(marco)) {
            pernoites++;
            marco = marco.plusDays(1);
        }

        return pernoites;
    }

    // Realiza uma divisão inteira arredondando o resultado para cima.
    private static long divisaoParaCima(long dividendo, long divisor) {
        return (dividendo + divisor - 1) / divisor;
    }

    // Cria uma data e horário validando os valores informados.
    private static LocalDateTime criarDataHora(int dia, int mes, int ano, int hora, int minuto) {
        if (ano < 1) {
            throw new IllegalArgumentException("Ano invalido: " + ano);
        }

        try {
            return LocalDateTime.of(ano, mes, dia, hora, minuto);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(
                    String.format(
                            "Data/hora invalida: %02d/%02d/%04d %02d:%02d",
                            dia, mes, ano, hora, minuto
                    ),
                    e
            );
        }
    }
}