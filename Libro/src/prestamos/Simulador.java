/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prestamos;

public class Simulador {
    public static void main(String[] args) {
        Libro[] libros = new Libro[9];
        for (int i = 0; i < libros.length; i++) {
            libros[i] = new Libro(i + 1); 
        }

        
        for (int i = 0; i < 4; i++) {
            Estudiante estudiante = new Estudiante(libros, i + 1);
            new Thread(estudiante).start();
        }
    }
}
