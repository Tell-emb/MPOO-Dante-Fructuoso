import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CuentaBancariaService service = new CuentaBancariaService();

        int opcion = 0;

        while (opcion != 4) {

            System.out.println("\n--- SISTEMA BANCARIO ---");
            System.out.println("1. Depositar");
            System.out.println("2. Retirar");
            System.out.println("3. Transferir");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    System.out.print("Numero de cuenta: ");
                    String cuentaDeposito = scanner.next();

                    System.out.print("Cantidad a depositar: ");
                    double cantidadDeposito = scanner.nextDouble();

                    if (service.depositar(cuentaDeposito, cantidadDeposito)) {
                        System.out.println("Deposito realizado correctamente.");
                    } else {
                        System.out.println("No se pudo realizar el deposito.");
                    }

                    break;

                case 2:
                    System.out.print("Numero de cuenta: ");
                    String cuentaRetiro = scanner.next();

                    System.out.print("Cantidad a retirar: ");
                    double cantidadRetiro = scanner.nextDouble();

                    if (service.retirar(cuentaRetiro, cantidadRetiro)) {
                        System.out.println("Retiro realizado correctamente.");
                    } else {
                        System.out.println("No se pudo realizar el retiro.");
                    }

                    break;

                case 3:
                    System.out.print("Numero de cuenta origen: ");
                    String cuentaOrigen = scanner.next();

                    System.out.print("Numero de cuenta destino: ");
                    String cuentaDestino = scanner.next();

                    System.out.print("Cantidad a transferir: ");
                    double cantidadTransferencia = scanner.nextDouble();

                    if (service.transferir(cuentaOrigen, cuentaDestino, cantidadTransferencia)) {
                        System.out.println("Transferencia realizada correctamente.");
                    } else {
                        System.out.println("No se pudo realizar la transferencia.");
                    }

                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }

        scanner.close();
    }
}