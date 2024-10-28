/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

import java.util.Random;

public class Estudiante implements Runnable {
    private final int id;
    private final Libro[] libros;
    private final Random random = new Random();

    public Estudiante(int id, Libro[] libros) {
        this.id = id;
        this.libros = libros;
    }

    private void tomarLibros() throws InterruptedException {
        int primerLibro = random.nextInt(libros.length);
        int segundoLibro;
        do {
            segundoLibro = random.nextInt(libros.length);
        } while (primerLibro == segundoLibro);

        synchronized (libros[primerLibro]) {
            synchronized (libros[segundoLibro]) {
                System.out.printf("Estudiante %d toma %s y %s.\n", id, libros[primerLibro], libros[segundoLibro]);
                
                int tiempoUso = (random.nextInt(3) + 1) * 100; // De 1 a 3 horas (en minutos) = 100 a 300 ms
                Thread.sleep(tiempoUso);
                
                System.out.printf("Estudiante %d devuelve %s y %s después de %d minutos.\n",
                        id, libros[primerLibro], libros[segundoLibro], tiempoUso / 100);
            }
        }
    }

    private void descansar() throws InterruptedException {
        int tiempoDescanso = (random.nextInt(2) + 1) * 100; // De 1 a 2 horas (en minutos) = 100 a 200 ms
        System.out.printf("Estudiante %d descansa %d minutos.\n", id, tiempoDescanso / 100);
        Thread.sleep(tiempoDescanso);
    }

    @Override
    public void run() {
        try {
            while (true) {
                tomarLibros();
                descansar();
            }
        } catch (InterruptedException e) {
            System.out.printf("Estudiante %d termina su actividad.\n", id);
        }
    }
}

