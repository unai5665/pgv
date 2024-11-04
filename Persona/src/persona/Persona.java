package persona;

import java.util.Random;

public class Persona implements Runnable {
    private final String direccion;
    private final int peso;
    private final Puente puente;

    public Persona(String direccion, Puente puente) {
        this.direccion = direccion;
        this.peso = new Random().nextInt(81) + 40; // Peso entre 40 y 120 kg
        this.puente = puente;
    }

    public int getPeso() {
        return peso;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public void run() {
        try {
            puente.intentarCruzar(this); // Intenta cruzar el puente
            int tiempoCruzando = new Random().nextInt(41) + 10; // Tiempo entre 10 y 50 segundos
            System.out.println("Persona de " + peso + "kg cruzando en dirección " + direccion + " durante " + tiempoCruzando + " segundos.");
            Thread.sleep(tiempoCruzando * 1000);
            puente.salir(this); // Sale del puente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
