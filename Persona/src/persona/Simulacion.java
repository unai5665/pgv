package persona;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulacion {
    public static void main(String[] args) {
        Puente puente = new Puente();
        ExecutorService ejecutor = Executors.newCachedThreadPool();
        Random random = new Random();

        for (int i = 0; i < 20; i++) { // Genera 20 personas para la simulación
            String direccion = random.nextBoolean() ? "Norte" : "Sur";
            Persona persona = new Persona(direccion, puente);
            ejecutor.execute(persona);

            // Espera aleatoria entre la llegada de cada persona
            try {
                Thread.sleep((random.nextInt(30) + 1) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ejecutor.shutdown();
    }
}
