package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class Persona {

    protected int id;
    protected String nombre;
    protected String telefono;
    protected String contrasena;

    public Persona(int id, String nombre, String telefono, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return nombre + " (" + telefono + ")";
    }
}

