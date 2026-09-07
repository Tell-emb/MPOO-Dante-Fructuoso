public class CuentaBancariaService {

    private CuentaBancaria[] cuentas;

    public CuentaBancariaService() {

        cuentas = new CuentaBancaria[4];

        cuentas[0] = new CuentaBancaria("001", "Dante", 5000, true);
        cuentas[1] = new CuentaBancaria("002", "Melanie", 3000, true);
        cuentas[2] = new CuentaBancaria("003", "Carlos", 1000, true);
        cuentas[3] = new CuentaBancaria("004", "Ana", 0, false);
    }

    public CuentaBancaria buscarCuenta(String numeroCuenta) {

        for (int i = 0; i < cuentas.length; i++) {

            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }

        return null;
    }

    public boolean depositar(String numeroCuenta, double cantidad) {

        CuentaBancaria cuenta = buscarCuenta(numeroCuenta);

        if (cuenta == null) {
            return false;
        }

        return cuenta.depositar(cantidad);
    }

    public boolean retirar(String numeroCuenta, double cantidad) {

        CuentaBancaria cuenta = buscarCuenta(numeroCuenta);

        if (cuenta == null) {
            return false;
        }

        return cuenta.retirar(cantidad);
    }

    public boolean transferir(String numeroOrigen, String numeroDestino, double cantidad) {

        CuentaBancaria origen = buscarCuenta(numeroOrigen);
        CuentaBancaria destino = buscarCuenta(numeroDestino);

        if (origen == null || destino == null) {
            return false;
        }

        if (origen == destino) {
            return false;
        }

        if (!origen.isActiva() || !destino.isActiva()) {
            return false;
        }

        if (cantidad <= 0 || cantidad > origen.getSaldo()) {
            return false;
        }

        origen.retirar(cantidad);
        destino.depositar(cantidad);

        return true;
    }
}