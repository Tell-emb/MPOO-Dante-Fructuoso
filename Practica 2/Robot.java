import java.util.UUID;
import java.util.Random;
import java.util.Objects;

public class Robot {

    private static int totalRobots = 0;

    private UUID id;
    private DatosRobot datos;
    private EstadoRobot estado;

    public Robot(String nombre, String modelo, String color,
                 double peso, double bateria, double velocidad) {

        this.id = UUID.randomUUID();

        this.datos = new DatosRobot(nombre, modelo, color, peso);
        this.estado = new EstadoRobot(bateria, velocidad);

        totalRobots++;
    }

    public static int getTotalRobots() {
        return totalRobots;
    }

    public void acelerar(double incremento) {
        estado.setVelocidad(estado.getVelocidad() + incremento);
    }

    public void frenar() {
        estado.setVelocidad(0);
    }

    public void avanzar() {

        Random random = new Random();

        double nuevaVelocidad = 5 + random.nextDouble() * 15;
        double consumo = 2 + random.nextDouble() * 6;

        estado.setVelocidad(nuevaVelocidad);
        estado.setBateria(estado.getBateria() - consumo);
    }

    public double calcularAutonomia() {
        return estado.getBateria() / (estado.getVelocidad() + 1);
    }

    @Override
    public String toString() {
        return "Robot{" +
            "id=" + id +
            ", nombre='" + datos.getNombre() + '\'' +
            ", modelo='" + datos.getModelo() + '\'' +
            ", color='" + datos.getColor() + '\'' +
            ", peso=" + datos.getPeso() +
            ", bateria=" + estado.getBateria() +
            ", velocidad=" + estado.getVelocidad() +
            '}';
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Robot)) {
            return false;
        }

        Robot otroRobot = (Robot) obj;

        return id.equals(otroRobot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}