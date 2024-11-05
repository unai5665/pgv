package actividades;

import java.util.Random;

public class Agente implements Runnable {
    private final int id;
    private final String actividad;
    private final Herramienta[] herramientas;
    private final Random random = new Random();

    public Agente(int id, String actividad, Herramienta... herramientas) {
        this.id = id;
        this.actividad = actividad;
        this.herramientas = herramientas;
    }

    private void realizarActividad() throws InterruptedException {
        synchronized (herramientas[0]) {
            synchronized (herramientas[1]) {
                if (herramientas.length == 3) {
                    synchronized (herramientas[2]) {
                        ejecutarActividad();
                    }
                } else {
                    ejecutarActividad();
                }
            }
        }
    }

    private void ejecutarActividad() throws InterruptedException {
        System.out.printf("Agente %d realiza actividad %s usando: ", id, actividad);
        for (Herramienta herramienta : herramientas) {
            System.out.print(herramienta + " ");
        }
        System.out.println();
        
        int tiempoUso = 50 + random.nextInt(151); 
        Thread.sleep(tiempoUso);

        System.out.printf(">>> Agente %d terminó actividad %s.\n", id, actividad, tiempoUso);
    }

    private void descansar() throws InterruptedException {
        int tiempoDescanso = 100 + random.nextInt(101); 
        System.out.printf("--- Agente %d descansando %d ms después de actividad %s\n", id, tiempoDescanso, actividad);
        Thread.sleep(tiempoDescanso);
    }

    @Override
    public void run() {
        try {
            while (true) {
                realizarActividad();
                descansar();
            }
        } catch (InterruptedException e) {
            System.out.printf("<<< Agente %d termina su ciclo.\n", id);
        }
    }
}
