package co.edu.uniquindio.poo.proyecto_final_p2.model;

public enum TipoEstrategiaEnvio {
    EXPRESS("Express"),
    NORMAL("Normal"),
    ECONOMICO("Económico");

    private final String nombre;

    TipoEstrategiaEnvio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}