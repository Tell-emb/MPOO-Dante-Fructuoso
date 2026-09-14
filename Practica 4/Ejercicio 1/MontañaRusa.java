import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'detectarZonaAjuste' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY vagones as parameter.
     */

    public static int[] detectarZonaAjuste(int[] vagones) {

        int inicio = -1;
        
        for (int i = 0; i < vagones.length -1; i ++) {
            if (vagones [i] > vagones [i + 1]) {
                inicio = i;
                break;  
            }
        }
        
        if (inicio == -1) {
            return new int [] {-1, -1, 0};
        }
        
        int fin = -1;
        
        for (int i = vagones.length -1; i > 0; i --) {
            if (vagones [i -1] > vagones [i]) {
                fin = i;
                break;
            }
        }
        
        int minimo = vagones [inicio];
        
        int maximo = vagones [inicio];
        
        for (int i = inicio; i <= fin; i ++) {
            if (vagones [i] < minimo) {
                minimo = vagones [i];
            }
            
            if (vagones [i] > maximo) {
                maximo = vagones [i];
            }
        }
        
        while (inicio > 0 && vagones [inicio - 1] > minimo) {
            inicio --;
        }
        
        while (fin < vagones.length - 1 && vagones [fin + 1] < maximo) {
            fin ++;
        }
        
        int longitud = fin - inicio + 1;    

        return new int[]{inicio, fin, longitud};
    }

}

public class MontañaRusa {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        BufferedWriter bufferedWriter =
                new BufferedWriter(
                        new FileWriter(System.getenv("OUTPUT_PATH"))
                );

        String linea = bufferedReader.readLine();

        String[] datos = linea.trim().split("\\s+");

        int[] vagones = new int[datos.length];

        for (int i = 0; i < datos.length; i++) {
            vagones[i] = Integer.parseInt(datos[i]);
        }

        int[] result = Result.detectarZonaAjuste(vagones);

        for (int i = 0; i < result.length; i++) {

            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write(" ");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
