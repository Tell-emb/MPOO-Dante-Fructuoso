import java.io.*;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

enum TipoReembolso {
    ALIMENTOS,
    TRANSPORTE,
    HOSPEDAJE
}

enum ResultadoValidacion {
    APROBADO,
    SIN_COMPROBANTE,
    FUERA_DE_PERIODO,
    EXCEDE_LIMITE,
    REQUIERE_AUTORIZACION
}

final class ReembolsoInputDTO {

    private final TipoReembolso tipo;
    private final String folio;
    private final String empleado;
    private final double monto;
    private final LocalDate fechaGasto;
    private final boolean tieneComprobante;
    private final boolean autorizadoGerente;
    private final LocalDate fechaEvaluacion;

    public ReembolsoInputDTO(
            TipoReembolso tipo,
            String folio,
            String empleado,
            double monto,
            LocalDate fechaGasto,
            boolean tieneComprobante,
            boolean autorizadoGerente,
            LocalDate fechaEvaluacion) {

        this.tipo = tipo;
        this.folio = folio;
        this.empleado = empleado;
        this.monto = monto;
        this.fechaGasto = fechaGasto;
        this.tieneComprobante = tieneComprobante;
        this.autorizadoGerente = autorizadoGerente;
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public TipoReembolso getTipo() {
        return tipo;
    }

    public String getFolio() {
        return folio;
    }

    public String getEmpleado() {
        return empleado;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFechaGasto() {
        return fechaGasto;
    }

    public boolean tieneComprobante() {
        return tieneComprobante;
    }

    public boolean estaAutorizadoGerente() {
        return autorizadoGerente;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }
}

final class ReembolsoOutputDTO {

    private final String folio;
    private final TipoReembolso tipo;
    private final ResultadoValidacion resultado;

    public ReembolsoOutputDTO(
            String folio,
            TipoReembolso tipo,
            ResultadoValidacion resultado) {

        this.folio = folio;
        this.tipo = tipo;
        this.resultado = resultado;
    }

    public String getFolio() {
        return folio;
    }

    public TipoReembolso getTipo() {
        return tipo;
    }

    public ResultadoValidacion getResultado() {
        return resultado;
    }
}

/*
 * 1. JERARQUIA DE REEMBOLSOS
 */

abstract class Reembolso {

    protected String folio;
    protected String empleado;
    protected double monto;
    protected LocalDate fechaGasto;
    protected boolean tieneComprobante;
    protected boolean autorizadoGerente;

    public Reembolso(
            String folio,
            String empleado,
            double monto,
            LocalDate fechaGasto,
            boolean tieneComprobante,
            boolean autorizadoGerente) {

        this.folio = folio;
        this.empleado = empleado;
        this.monto = monto;
        this.fechaGasto = fechaGasto;
        this.tieneComprobante = tieneComprobante;
        this.autorizadoGerente = autorizadoGerente;
    }

    public Reembolso(
            String folio,
            String empleado,
            double monto,
            LocalDate fechaGasto) {

        this(folio, empleado, monto, fechaGasto, false, false);
    }

    public String getFolio() {
        return folio;
    }

    public String getEmpleado() {
        return empleado;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFechaGasto() {
        return fechaGasto;
    }

    public boolean tieneComprobante() {
        return tieneComprobante;
    }

    public boolean estaAutorizadoGerente() {
        return autorizadoGerente;
    }

    public abstract double getLimitePermitido();
}

class ReembolsoAlimentos extends Reembolso {

    public ReembolsoAlimentos(
            String folio, String empleado, double monto,
            LocalDate fechaGasto, boolean tieneComprobante,
            boolean autorizadoGerente) {

        super(folio, empleado, monto, fechaGasto,
              tieneComprobante, autorizadoGerente);
    }

    public ReembolsoAlimentos(
            String folio, String empleado, double monto,
            LocalDate fechaGasto) {

        super(folio, empleado, monto, fechaGasto);
    }

    public double getLimitePermitido() {
        return 1000.0;
    }
}

class ReembolsoTransporte extends Reembolso {

    public ReembolsoTransporte(
            String folio, String empleado, double monto,
            LocalDate fechaGasto, boolean tieneComprobante,
            boolean autorizadoGerente) {

        super(folio, empleado, monto, fechaGasto,
              tieneComprobante, autorizadoGerente);
    }

    public ReembolsoTransporte(
            String folio, String empleado, double monto,
            LocalDate fechaGasto) {

        super(folio, empleado, monto, fechaGasto);
    }

    public double getLimitePermitido() {
        return 1500.0;
    }
}

class ReembolsoHospedaje extends Reembolso {

    public ReembolsoHospedaje(
            String folio, String empleado, double monto,
            LocalDate fechaGasto, boolean tieneComprobante,
            boolean autorizadoGerente) {

        super(folio, empleado, monto, fechaGasto,
              tieneComprobante, autorizadoGerente);
    }

    public ReembolsoHospedaje(
            String folio, String empleado, double monto,
            LocalDate fechaGasto) {

        super(folio, empleado, monto, fechaGasto);
    }

    public double getLimitePermitido() {
        return 4000.0;
    }
}

/*
 * 2. FACTORY METHOD
 */

abstract class RegistroReembolso {

    public abstract Reembolso crearReembolso(
            ReembolsoInputDTO input);
}

class RegistroAlimentos extends RegistroReembolso {

    public Reembolso crearReembolso(ReembolsoInputDTO input) {

        return new ReembolsoAlimentos(
                input.getFolio(),
                input.getEmpleado(),
                input.getMonto(),
                input.getFechaGasto(),
                input.tieneComprobante(),
                input.estaAutorizadoGerente()
        );
    }
}

class RegistroTransporte extends RegistroReembolso {

    public Reembolso crearReembolso(ReembolsoInputDTO input) {

        return new ReembolsoTransporte(
                input.getFolio(),
                input.getEmpleado(),
                input.getMonto(),
                input.getFechaGasto(),
                input.tieneComprobante(),
                input.estaAutorizadoGerente()
        );
    }
}

class RegistroHospedaje extends RegistroReembolso {

    public Reembolso crearReembolso(ReembolsoInputDTO input) {

        return new ReembolsoHospedaje(
                input.getFolio(),
                input.getEmpleado(),
                input.getMonto(),
                input.getFechaGasto(),
                input.tieneComprobante(),
                input.estaAutorizadoGerente()
        );
    }
}

/*
 * 3. CHAIN OF RESPONSIBILITY
 */

abstract class ValidadorReembolso {

    private ValidadorReembolso siguiente;

    public ValidadorReembolso establecerSiguiente(
            ValidadorReembolso siguiente) {

        this.siguiente = siguiente;
        return siguiente;
    }

    public ResultadoValidacion validar(
            Reembolso reembolso,
            LocalDate fechaEvaluacion) {

        ResultadoValidacion resultado =
                comprobar(reembolso, fechaEvaluacion);

        if (resultado != ResultadoValidacion.APROBADO) {
            return resultado;
        }

        if (siguiente != null) {
            return siguiente.validar(
                    reembolso, fechaEvaluacion);
        }

        return ResultadoValidacion.APROBADO;
    }

    protected abstract ResultadoValidacion comprobar(
            Reembolso reembolso,
            LocalDate fechaEvaluacion);
}

class ValidadorComprobante extends ValidadorReembolso {

    protected ResultadoValidacion comprobar(
            Reembolso reembolso,
            LocalDate fechaEvaluacion) {

        if (!reembolso.tieneComprobante()) {
            return ResultadoValidacion.SIN_COMPROBANTE;
        }

        return ResultadoValidacion.APROBADO;
    }
}

class ValidadorPeriodo extends ValidadorReembolso {

    protected ResultadoValidacion comprobar(
            Reembolso reembolso,
            LocalDate fechaEvaluacion) {

        long dias = ChronoUnit.DAYS.between(
                reembolso.getFechaGasto(),
                fechaEvaluacion
        );

        if (dias > 30) {
            return ResultadoValidacion.FUERA_DE_PERIODO;
        }

        return ResultadoValidacion.APROBADO;
    }
}

class ValidadorLimite extends ValidadorReembolso {

    protected ResultadoValidacion comprobar(
            Reembolso reembolso,
            LocalDate fechaEvaluacion) {

        if (reembolso.getMonto()
                > reembolso.getLimitePermitido()) {

            return ResultadoValidacion.EXCEDE_LIMITE;
        }

        return ResultadoValidacion.APROBADO;
    }
}

class ValidadorGerencia extends ValidadorReembolso {

    protected ResultadoValidacion comprobar(
            Reembolso reembolso,
            LocalDate fechaEvaluacion) {

        if (reembolso.getMonto() > 2000
                && !reembolso.estaAutorizadoGerente()) {

            return ResultadoValidacion.REQUIERE_AUTORIZACION;
        }

        return ResultadoValidacion.APROBADO;
    }
}

/*
 * 4. PROCESAMIENTO
 */

class Result {

    public static ReembolsoOutputDTO procesarReembolso(
            ReembolsoInputDTO input) {

        RegistroReembolso registro;

        switch (input.getTipo()) {

            case ALIMENTOS:
                registro = new RegistroAlimentos();
                break;

            case TRANSPORTE:
                registro = new RegistroTransporte();
                break;

            case HOSPEDAJE:
                registro = new RegistroHospedaje();
                break;

            default:
                throw new IllegalArgumentException(
                        "Tipo de reembolso no valido");
        }

        Reembolso reembolso =
                registro.crearReembolso(input);

        ValidadorReembolso comprobante =
                new ValidadorComprobante();

        ValidadorReembolso periodo =
                new ValidadorPeriodo();

        ValidadorReembolso limite =
                new ValidadorLimite();

        ValidadorReembolso gerencia =
                new ValidadorGerencia();

        comprobante.establecerSiguiente(periodo)
                   .establecerSiguiente(limite)
                   .establecerSiguiente(gerencia);

        ResultadoValidacion resultado =
                comprobante.validar(
                        reembolso,
                        input.getFechaEvaluacion()
                );

        return new ReembolsoOutputDTO(
                input.getFolio(),
                input.getTipo(),
                resultado
        );
    }
}

/*
 * 5. SOLUTION DE HACKERRANK
 */

public class Herencia {

    public static void main(String[] args)
            throws Exception {

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String lineaFecha = br.readLine();

        LocalDate fechaEvaluacion =
                LocalDate.parse(lineaFecha.trim());

        String lineaSolicitud = br.readLine();

        String[] datos = lineaSolicitud.split("\\|");

        TipoReembolso tipo =
                TipoReembolso.valueOf(datos[0].trim());

        String folio = datos[1].trim();
        String empleado = datos[2].trim();

        double monto =
                Double.parseDouble(datos[3].trim());

        LocalDate fechaGasto =
                LocalDate.parse(datos[4].trim());

        boolean tieneComprobante =
                Boolean.parseBoolean(datos[5].trim());

        boolean autorizadoGerente =
                Boolean.parseBoolean(datos[6].trim());

        ReembolsoInputDTO input =
                new ReembolsoInputDTO(
                        tipo,
                        folio,
                        empleado,
                        monto,
                        fechaGasto,
                        tieneComprobante,
                        autorizadoGerente,
                        fechaEvaluacion
                );

        ReembolsoOutputDTO output =
                Result.procesarReembolso(input);

        System.out.println(
                "REEMBOLSO " + output.getFolio());

        System.out.println(
                "TIPO " + output.getTipo());

        System.out.println(
                "RESULTADO " + output.getResultado());
    }
}