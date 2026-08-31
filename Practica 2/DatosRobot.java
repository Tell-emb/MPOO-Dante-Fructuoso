public class DatosRobot {

    private String nombre;
    private String modelo;
    private String color;
    private double peso;

    public DatosRobot(String nombre, String modelo, String color, double peso) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.color = color;
        this.peso = peso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public double getPeso() {
        return peso;
    }
}