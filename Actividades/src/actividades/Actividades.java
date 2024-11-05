package actividades;

public class Actividades {
    public static void main(String[] args) {
        
        Herramienta taladro = new Herramienta("Taladro");
        Herramienta destornillador = new Herramienta("Destornillador");
        Herramienta alicates = new Herramienta("Alicates");
        
      
        Agente[] agentes = new Agente[4];
        Thread[] hilos = new Thread[4];

        agentes[0] = new Agente(1, "A1", taladro, destornillador);
        agentes[1] = new Agente(2, "A1", taladro, destornillador);
        agentes[2] = new Agente(3, "A2", destornillador, alicates);
        agentes[3] = new Agente(4, "A3", taladro, destornillador, alicates);

        for (int i = 0; i < agentes.length; i++) {
            hilos[i] = new Thread(agentes[i], "Agente " + (i + 1));
            hilos[i].start();
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
