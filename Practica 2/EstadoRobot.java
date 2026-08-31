public class EstadoRobot {

    private double bateria;
    private double velocidad;

    public EstadoRobot(double bateria, double velocidad) {
        this.bateria = bateria;
        this.velocidad = velocidad;
    }

    public double getBateria() {
        return bateria;
    }

    public void setBateria(double bateria) {
        this.bateria = bateria;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }
}