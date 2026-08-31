public class BattleBot {

    public static void main(String[] args) {

        Robot nero = new Robot(
            "Nero",
            "V-01",
            "Azul",
            35,
            70,
            0
        );

        Robot prisma = new Robot(
            "Prisma",
            "T-01",
            "Rojo",
            80,
            100,
            0
        );

        Robot depthban = new Robot(
            "Depthban",
            "R-01",
            "Negro",
            55,
            85,
            0
        );

        System.out.println("Robots participantes: " + Robot.getTotalRobots());

        System.out.println("\n--- ANTES DE LA BATALLA ---");
        System.out.println(nero);
        System.out.println(prisma);
        System.out.println(depthban);

        System.out.println("\n--- BATALLA ---");

        nero.avanzar();
        prisma.avanzar();
        depthban.avanzar();

        System.out.println("\n--- DESPUÉS DE LA BATALLA ---");
        System.out.println(nero);
        System.out.println(prisma);
        System.out.println(depthban);


        System.out.println("\n--- PRUEBA DE REFERENCIAS ---");

        Robot robotFavorito = nero;

        System.out.println("Estado de Nero:");
        System.out.println(nero);

        System.out.println("\nEstado de robotFavorito:");
        System.out.println(robotFavorito);

        nero.avanzar();

        System.out.println("\nDespués de modificar Nero:");

        System.out.println("Nero:");
        System.out.println(nero);

        System.out.println("\nRobot favorito:");
        System.out.println(robotFavorito);


        System.out.println("\n--- PRUEBA DE IGUALDAD ---");

        Robot robot2 = new Robot(
            "Nero",
            "V-01",
            "Azul",
            35,
            70,
            0
        );

        System.out.println("Nero y robot2: " + nero.equals(robot2));
        System.out.println("Nero y robotFavorito: " + nero.equals(robotFavorito));
    }
}