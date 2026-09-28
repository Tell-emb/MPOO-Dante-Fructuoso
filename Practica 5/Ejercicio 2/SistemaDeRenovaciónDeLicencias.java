import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    enum TipoLicencia {
        BASICA,
        PROFESIONAL,
        EMPRESARIAL,
        TEMPORAL
    }

    enum EstadoLicencia {
        VIGENTE,
        PROXIMA_A_VENCER,
        VENCIDA,
        BLOQUEADA
    }

    /*
     * Complete the 'evaluarLicencia' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING fechaActual
     *  2. STRING fechaVencimiento
     *  3. STRING tipoLicencia
     *  4. INTEGER renovacionesPrevias
     */

    public static String evaluarLicencia(String fechaActual, String fechaVencimiento, String tipoLicencia, int renovacionesPrevias) {

        // Separar fechas

        String[] fa = fechaActual.split("/");
        String[] fv = fechaVencimiento.split("/");

        // Crear Calendar

        Calendar actual = Calendar.getInstance();
        Calendar vencimiento = Calendar.getInstance();

        actual.set(
            Integer.parseInt(fa[2]),
            Integer.parseInt(fa[1]) - 1,
            Integer.parseInt(fa[0]),
            0,
            0
        );

        vencimiento.set(
            Integer.parseInt(fv[2]),
            Integer.parseInt(fv[1]) - 1,
            Integer.parseInt(fv[0]),
            0,
            0
        );

        actual.set(Calendar.SECOND, 0);
        actual.set(Calendar.MILLISECOND, 0);

        vencimiento.set(Calendar.SECOND, 0);
        vencimiento.set(Calendar.MILLISECOND, 0);

        // Calcular diferencia de dias

        long diferencia = vencimiento.getTimeInMillis()
                - actual.getTimeInMillis();

        long diasRest = diferencia / (1000L * 60 * 60 * 24);

        // Determinar estado

        EstadoLicencia estado;

        if (diasRest > 30) {
            estado = EstadoLicencia.VIGENTE;
        } else if (diasRest >= 0) {
            estado = EstadoLicencia.PROXIMA_A_VENCER;
        } else if (diasRest >= -90) {
            estado = EstadoLicencia.VENCIDA;
        } else {
            estado = EstadoLicencia.BLOQUEADA;
        }

        TipoLicencia licencia = TipoLicencia.valueOf(tipoLicencia);

        // Si esta bloqueada

        if (estado == EstadoLicencia.BLOQUEADA) {
            return String.format(
                    Locale.US,
                    "%s %d 0.00 NO_DISPONIBLE",
                    estado,
                    diasRest
            );
        }

        // Obtener costo y periodo

        double costo = 0;
        int periodo = 0;

        if (licencia == TipoLicencia.BASICA) {
            costo = 1000;
            periodo = 1;
        } else if (licencia == TipoLicencia.PROFESIONAL) {
            costo = 1500;
            periodo = 2;
        } else if (licencia == TipoLicencia.EMPRESARIAL) {
            costo = 2500;
            periodo = 3;
        } else if (licencia == TipoLicencia.TEMPORAL) {
            costo = 600;
        }

        // Aplicar ajuste segun estado

        if (estado == EstadoLicencia.VIGENTE) {
            costo *= 0.90;
        } else if (estado == EstadoLicencia.VENCIDA) {
            costo *= 1.20;
        }

        // Ajuste por renovaciones

        if (renovacionesPrevias > 3) {
            costo *= 0.95;
        }

        // Calcular nueva fecha

        Calendar nuevaFecha = (Calendar) vencimiento.clone();

        if (estado == EstadoLicencia.VENCIDA) {
            nuevaFecha = (Calendar) actual.clone();
        }

        if (licencia == TipoLicencia.BASICA) {
            nuevaFecha.add(Calendar.YEAR, 1);
        } else if (licencia == TipoLicencia.PROFESIONAL) {
            nuevaFecha.add(Calendar.YEAR, 2);
        } else if (licencia == TipoLicencia.EMPRESARIAL) {
            nuevaFecha.add(Calendar.YEAR, 3);
        } else if (licencia == TipoLicencia.TEMPORAL) {
            nuevaFecha.add(Calendar.MONTH, 6);
        }

        // Formatear nueva fecha

        String nuevaFechaTexto = String.format(
                Locale.US,
                "%02d/%02d/%04d",
                nuevaFecha.get(Calendar.DAY_OF_MONTH),
                nuevaFecha.get(Calendar.MONTH) + 1,
                nuevaFecha.get(Calendar.YEAR)
        );

        // Regresar resultado

        return String.format(
                Locale.US,
                "%s %d %.2f %s",
                estado,
                diasRest,
                costo,
                nuevaFechaTexto
        );
    }
}

public class SistemaDeRenovaciónDeLicencias {
    public static void main(String[] args)
            throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(System.in)
                );

        BufferedWriter bufferedWriter =
                new BufferedWriter(
                        new FileWriter(
                                System.getenv("OUTPUT_PATH")
                        )
                );

        String fechaActual =
                bufferedReader.readLine();

        String fechaVencimiento =
                bufferedReader.readLine();

        String tipoLicencia =
                bufferedReader.readLine();

        int renovacionesPrevias =
                Integer.parseInt(
                        bufferedReader
                                .readLine()
                                .trim()
                );

        String result =
                Result.evaluarLicencia(
                        fechaActual,
                        fechaVencimiento,
                        tipoLicencia,
                        renovacionesPrevias
                );

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}