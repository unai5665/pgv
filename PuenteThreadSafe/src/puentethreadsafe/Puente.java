/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puentethreadsafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Puente {
    // Restricciones
    private final int MAX_PERSONAS = 4;
    private final int MAX_PERSONAS_SENTIDO = 3;
    private final int MAX_PESO = 300;

    // Variables de estado para cada sentido
    private int personasSentido1 = 0;
    private int personasSentido2 = 0;
    private int pesoSentido1 = 0;
    private int pesoSentido2 = 0;

    // Bloques de sincronización para cada sentido
    private final Lock lockSentido1 = new ReentrantLock();
    private final Lock lockSentido2 = new ReentrantLock();

    // Métodos de acceso
    public void entrar(Persona persona) throws InterruptedException {
        String direccion = persona.getDireccion();
        int peso = persona.getPesoPersona();

        if (direccion.equals("Sentido1")) {
            lockSentido1.lock();
            try {
                while (personasSentido1 >= MAX_PERSONAS_SENTIDO || pesoSentido1 + peso > MAX_PESO) {
                    lockSentido1.unlock();  // Liberar el lock temporalmente si no puede entrar
                    Thread.sleep(100); // Espera activa, puede ser optimizado
                    lockSentido1.lock(); // Vuelve a tomar el lock
                }
                // Si puede entrar, actualizar el estado
                personasSentido1++;
                pesoSentido1 += peso;
            } finally {
                lockSentido1.unlock();
            }
        } else {  // Sentido2
            lockSentido2.lock();
            try {
                while (personasSentido2 >= MAX_PERSONAS_SENTIDO || pesoSentido2 + peso > MAX_PESO) {
                    lockSentido2.unlock(); // Liberar el lock temporalmente
                    Thread.sleep(100); // Espera activa
                    lockSentido2.lock(); // Re-toma el lock
                }
                // Si puede entrar, actualizar el estado
                personasSentido2++;
                pesoSentido2 += peso;
            } finally {
                lockSentido2.unlock();
            }
        }
    }

    public void salir(Persona persona) {
        String direccion = persona.getDireccion();
        int peso = persona.getPesoPersona();

        if (direccion.equals("Sentido1")) {
            lockSentido1.lock();
            try {
                personasSentido1--;
                pesoSentido1 -= peso;
            } finally {
                lockSentido1.unlock();
            }
        } else {  // Sentido2
            lockSentido2.lock();
            try {
                personasSentido2--;
                pesoSentido2 -= peso;
            } finally {
                lockSentido2.unlock();
            }
        }
    }
}
