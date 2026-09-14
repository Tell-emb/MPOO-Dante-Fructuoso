import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'cifrarMensaje' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING mensaje
     *  2. STRING alfabetoInterior
     *  3. INTEGER posicionInicial
     *  4. INTEGER intervaloRotacion
     */

    public static String cifrarMensaje(String mensaje, String alfabetoInterior, int posicionInicial, int intervaloRotacion) {
    // Write your code here
    
     String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    StringBuilder resultado = new StringBuilder();

    int letrasProcesadas = 0;

    for (int i = 0; i < mensaje.length(); i++) {

        char letra = mensaje.charAt(i);

        if (letra == ' ') {
            resultado.append(letra);
            continue;
        }

        int posicion = letra - 'A';

        int rotacion = posicionInicial + (letrasProcesadas / intervaloRotacion);

        int posicionInterior = (posicion - rotacion + 26) % 26;

        char letraCifrada = alfabetoInterior.charAt(posicionInterior);

        resultado.append(letraCifrada);

        letrasProcesadas++;
    }

    return resultado.toString();
}


}

public class RuedaMensajero {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String mensaje = bufferedReader.readLine();

        String alfabetoInterior = bufferedReader.readLine();

        int posicionInicial = Integer.parseInt(bufferedReader.readLine().trim());

        int intervaloRotacion = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.cifrarMensaje(mensaje, alfabetoInterior, posicionInicial, intervaloRotacion);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}