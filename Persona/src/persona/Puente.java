package persona;

import java.util.ArrayList;
import java.util.List;

public class Puente {
    private final int MAX_PERSONAS = 4;
    private final int MAX_POR_DIRECCION = 3;
    private final int MAX_PESO = 300;

    private int personasEnPuente = 0;
    private int pesoTotal = 0;
    private List<String> direcciones = new ArrayList<>();

    public synchronized void intentarCruzar(Persona persona) throws InterruptedException {
        String direccion = persona.getDireccion();
        
        // Espera hasta que se cumplan todas las restricciones
        while (!puedeCruzar(persona)) {
            wait();
        }
        
        // Si puede pasar, actualiza el estado del puente
        personasEnPuente++;
        pesoTotal += persona.getPeso();
        direcciones.add(direccion);
        System.out.println("Persona de " + persona.getPeso() + "kg ingresó en dirección " + direccion + ". Estado del puente: " + personasEnPuente + " personas, " + pesoTotal + "kg.");
    }

    public synchronized void salir(Persona persona) {
        personasEnPuente--;
        pesoTotal -= persona.getPeso();
        direcciones.remove(persona.getDireccion());
        System.out.println("Persona de " + persona.getPeso() + "kg salió. Estado del puente: " + personasEnPuente + " personas, " + pesoTotal + "kg.");
        notifyAll(); // Notifica a otros hilos que el puente ha cambiado
    }

    private boolean puedeCruzar(Persona persona) {
        String direccion = persona.getDireccion();
        long personasDireccion = direcciones.stream().filter(d -> d.equals(direccion)).count();

        // Validar las restricciones
        return (personasEnPuente < MAX_PERSONAS &&
                personasDireccion < MAX_POR_DIRECCION &&
                (pesoTotal + persona.getPeso()) <= MAX_PESO);
    }
}
