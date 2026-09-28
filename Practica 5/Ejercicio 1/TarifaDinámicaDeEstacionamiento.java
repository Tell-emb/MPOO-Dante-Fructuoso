import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    enum TipoVehiculo {
        MOTOCICLETA, AUTOMOVIL, CAMIONETA, ELECTRICO
    }

    enum TipoEstancia {
        NORMAL, NOCTURNA, FIN_SEMANA, MIXTA
    }

    /*
     * Complete the 'calcularEstancia' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING tipoVehiculo
     *  2. STRING fechaEntrada
     *  3. STRING horaEntrada
     *  4. STRING fechaSalida
     *  5. STRING horaSalida
     */

    public static String calcularEstancia(String tipoVehiculo, String fechaEntrada, String horaEntrada, String fechaSalida, String horaSalida) {

        // Separar las fechas

        String[] fe = fechaEntrada.split("/");
        String[] fs = fechaSalida.split("/");

        // Separar las horas

        String[] he = horaEntrada.split(":");
        String[] hs = horaSalida.split(":");

        // Crear Calendar

        Calendar entrada = Calendar.getInstance();
        Calendar salida = Calendar.getInstance();

        entrada.set(
            Integer.parseInt(fe[2]),
            Integer.parseInt(fe[1]) - 1,
            Integer.parseInt(fe[0]),
            Integer.parseInt(he[0]),
            Integer.parseInt(he[1])
        );

        salida.set(
            Integer.parseInt(fs[2]),
            Integer.parseInt(fs[1]) - 1,
            Integer.parseInt(fs[0]),
            Integer.parseInt(hs[0]),
            Integer.parseInt(hs[1])
        );

        entrada.set(Calendar.SECOND, 0);
        entrada.set(Calendar.MILLISECOND, 0);

        salida.set(Calendar.SECOND, 0);
        salida.set(Calendar.MILLISECOND, 0);

        // Validar fechas

        if (!salida.after(entrada)) {
            return "INVALID";
        }

        // Calcular horas

        long diferencia = salida.getTimeInMillis()
                - entrada.getTimeInMillis();

        long horasCobradas = (long) Math.ceil(
                diferencia / (1000.0 * 60 * 60)
        );

        // Convertir String a enum

        TipoVehiculo vehiculo = TipoVehiculo.valueOf(tipoVehiculo);

        // Obtener tarifa y maximo

        double tarifa = 0;
        double maximo = 0;

        if (vehiculo == TipoVehiculo.MOTOCICLETA) {
            tarifa = 15;
            maximo = 100;
        } else if (vehiculo == TipoVehiculo.AUTOMOVIL) {
            tarifa = 25;
            maximo = 180;
        } else if (vehiculo == TipoVehiculo.CAMIONETA) {
            tarifa = 35;
            maximo = 250;
        } else if (vehiculo == TipoVehiculo.ELECTRICO) {
            tarifa = 20;
            maximo = 150;
        }

        // Calcular costo por bloques

        long horasRestantes = horasCobradas;
        double costo = 0;

        while (horasRestantes > 0) {

            long horasBloque = Math.min(horasRestantes, 24);

            double costoBloque = Math.min(
                    horasBloque * tarifa,
                    maximo
            );

            costo += costoBloque;
            horasRestantes -= horasBloque;
        }

        // Fin de semana

        boolean finSemana = false;

        int diaEntrada = entrada.get(Calendar.DAY_OF_WEEK);
        int diaSalida = salida.get(Calendar.DAY_OF_WEEK);

        if (diaEntrada == Calendar.SATURDAY ||
            diaEntrada == Calendar.SUNDAY ||
            diaSalida == Calendar.SATURDAY ||
            diaSalida == Calendar.SUNDAY) {

            finSemana = true;
            costo *= 1.20;
        }

        // Nocturna

        boolean nocturna = false;

        int horaE = entrada.get(Calendar.HOUR_OF_DAY);
        int horaS = salida.get(Calendar.HOUR_OF_DAY);

        if (horaE >= 20 ||
            horaS < 6 ||
            entrada.get(Calendar.YEAR) != salida.get(Calendar.YEAR) ||
            entrada.get(Calendar.DAY_OF_YEAR) != salida.get(Calendar.DAY_OF_YEAR)) {

            nocturna = true;
            costo *= 1.15;
        }

        // Descuento electrico

        if (vehiculo == TipoVehiculo.ELECTRICO) {
            costo *= 0.90;
        }

        // Clasificacion de la estancia

        TipoEstancia tipoEstancia;

        if (finSemana && nocturna) {
            tipoEstancia = TipoEstancia.MIXTA;
        } else if (finSemana) {
            tipoEstancia = TipoEstancia.FIN_SEMANA;
        } else if (nocturna) {
            tipoEstancia = TipoEstancia.NOCTURNA;
        } else {
            tipoEstancia = TipoEstancia.NORMAL;
        }

        // Regresar resultado

        return String.format(
                Locale.US,
                "%d %.2f %s",
                horasCobradas,
                costo,
                tipoEstancia
        );
    }
}

public class TarifaDinámicaDeEstacionamiento {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String tipoVehiculo = bufferedReader.readLine();

        String fechaEntrada = bufferedReader.readLine();

        String horaEntrada = bufferedReader.readLine();

        String fechaSalida = bufferedReader.readLine();

        String horaSalida = bufferedReader.readLine();

        String result = Result.calcularEstancia(tipoVehiculo, fechaEntrada, horaEntrada, fechaSalida, horaSalida);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}