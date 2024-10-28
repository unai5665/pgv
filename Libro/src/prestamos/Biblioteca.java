/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

public class Biblioteca {
    public static void main(String[] args) {
        Libro[] libros = new Libro[9];
        for (int i = 0; i < libros.length; i++) {
            libros[i] = new Libro(i + 1);
        }

        Estudiante[] estudiantes = new Estudiante[4];
        Thread[] hilos = new Thread[4];
        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i] = new Estudiante(i + 1, libros);
            hilos[i] = new Thread(estudiantes[i], "Estudiante " + (i + 1));
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }

        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

