package actividades;

public class Herramienta {
    private final String nombre;

    public Herramienta(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
