package puentethreadsafe;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Persona implements Runnable {
    // Atributos
    private final String idPersona;
    private final int tiempoPaso;
    private final int pesoPersona;
    private final Puente puente;
    private final String direccion;  // Nueva propiedad para indicar la dirección

    // Constructor
    public Persona(String idPersona, int tiempoPaso, int pesoPersona, Puente puente, String direccion) {
        this.idPersona = idPersona;
        this.tiempoPaso = tiempoPaso;
        this.pesoPersona = pesoPersona;
        this.puente = puente;
        this.direccion = direccion;
    }

    // Getter
    public String getDireccion() {
        return direccion;
    }

    public int getPesoPersona() {
        return pesoPersona;
    }

    // Método run
    @Override
    public void run() {
        System.out.printf(">>> La %s con %d kilos quiere cruzar en %d segundos hacia %s.\n",
                          idPersona, pesoPersona, tiempoPaso, direccion);
        // Entrar al puente
        try {
            puente.entrar(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Cruzar
        try {
            Thread.sleep(tiempoPaso * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Salir del puente
        puente.salir(this);
    }
}
