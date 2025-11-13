package co.edu.uniquindio.poo.proyecto_final_p2.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona {

    private String direccion;
    private List<Envio> listEnviosPropios;

    private Usuario(Builder builder) {
        super(builder.id, builder.nombre, builder.telefono, builder.contrasena);
        this.listEnviosPropios = new ArrayList<>();
        this.direccion = builder.direccion;
    }

    // Patrón builder
    public static class Builder {
        private int id;
        private String nombre;
        private String telefono;
        private String contrasena;
        private String direccion;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder contrasena(String contrasena) {
            this.contrasena = contrasena;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

    public Envio crearEnvio(String destino, ZonaCobertura departamento, double distancia, double peso, double volumen, boolean prioridad) {
        Administrador admin = Administrador.getInstancia();
        List<Envio> listaEnvios = admin.getListEnvios();

        int nuevoId = listaEnvios.size() + 1;
        Envio nuevoEnvio = new Envio(nuevoId, destino, departamento, distancia, peso, volumen, prioridad);

        listaEnvios.add(nuevoEnvio);
        return nuevoEnvio;
    }

    public List<Envio> getListEnviosPropios() {
        return listEnviosPropios;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
