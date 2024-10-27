/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prestamos;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Estudiante implements Runnable {
    private static final Random random = new Random();
    private final Libro[] libros;
    private final int id;
    private static final Object lock = new Object();

    public Estudiante(Libro[] libros, int id) {
        this.libros = libros;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                
                Libro libro1 = seleccionarLibro();
                Libro libro2 = seleccionarLibro();
                System.out.println("Estudiante " + id + " ha seleccionado " + libro1 + " y " + libro2);

                
                int tiempoUso = 1 + random.nextInt(3); 
                TimeUnit.SECONDS.sleep(tiempoUso);
                System.out.println("Estudiante " + id + " ha devuelto " + libro1 + " y " + libro2);

                
                int tiempoDescanso = 1 + random.nextInt(2); 
                TimeUnit.SECONDS.sleep(tiempoDescanso);
                System.out.println("Estudiante " + id + " está descansando.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private Libro seleccionarLibro() {
        synchronized (lock) {
            int index = random.nextInt(libros.length);
            return libros[index];
        }
    }
}

