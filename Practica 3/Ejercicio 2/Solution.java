import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'comprimir' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING datos as parameter.
     */

public static String comprimir(String datos) {

    String resultado = "";

    for (int i = 0; i < datos.length(); i++) {

        int cantidad = 1;
        char caracter = datos.charAt(i);

        while (i < datos.length() - 1 && datos.charAt(i + 1) == caracter) {
            cantidad++;
            i++;
        }

        while (cantidad > 9) {
            resultado += "9" + caracter;
            cantidad -= 9;
        }

        resultado += cantidad + "" + caracter;
    }

    return resultado;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String datos = bufferedReader.readLine();

        String result = Result.comprimir(datos);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}