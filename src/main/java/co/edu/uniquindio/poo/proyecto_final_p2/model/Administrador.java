package co.edu.uniquindio.poo.proyecto_final_p2.model;

import java.util.ArrayList;
import java.util.List;

public class Administrador {

    private String id;
    private String contrasenia;
    private List<Usuario> listUsuarios;
    private List<Repartidor> listRepartidores;
    private List<Envio> listEnvios;
    private static Administrador instance;

    private Administrador(String id, String contrasenia) {
        this.id = id;
        this.contrasenia = contrasenia;
        this.listUsuarios = new ArrayList<>();
        this.listRepartidores = new ArrayList<>();
        this.listEnvios = new ArrayList<>();

        cargarDatosQuemados();
    }

    // Patrón singleton
    public static Administrador getInstancia() {
        if (instance == null) {
            instance = new Administrador("admin", "1234");
        }
        return instance;
    }


    private void cargarDatosQuemados() {
        Usuario usuario1 = new Usuario.Builder()
                .id(1001)
                .nombre("Juan Pérez")
                .telefono("3001234567")
                .contrasena("1234")
                .direccion("Calle 10 #20-30, Armenia")
                .build();

        Usuario usuario2 = new Usuario.Builder()
                .id(1002)
                .nombre("María García")
                .telefono("3109876543")
                .contrasena("1234")
                .direccion("Carrera 15 #25-40, Montenegro")
                .build();

        Usuario usuario3 = new Usuario.Builder()
                .id(1003)
                .nombre("Carlos Rodríguez")
                .telefono("3207654321")
                .contrasena("1234")
                .direccion("Avenida Bolívar #30-50, Calarcá")
                .build();

        listUsuarios.add(usuario1);
        listUsuarios.add(usuario2);
        listUsuarios.add(usuario3);

        Repartidor repartidor1 = Repartidor.crearRepartidor(
                2001, "Pedro Martínez", "3151234567", "1234", ZonaCobertura.ARMENIA
        );

        Repartidor repartidor2 = Repartidor.crearRepartidor(
                2002, "Laura Sánchez", "3162345678", "1234", ZonaCobertura.MONTENEGRO
        );

        Repartidor repartidor3 = Repartidor.crearRepartidor(
                2003, "Diego López", "3173456789", "1234", ZonaCobertura.CALARCA
        );
        repartidor3.setDisponibilidad(Disponibilidad.EN_RUTA);

        Repartidor repartidor4 = Repartidor.crearRepartidor(
                2004, "Ana Fernández", "3184567890", "1234", ZonaCobertura.CIRCASIA
        );
        repartidor4.setDisponibilidad(Disponibilidad.INACTIVO);

        listRepartidores.add(repartidor1);
        listRepartidores.add(repartidor2);
        listRepartidores.add(repartidor3);
        listRepartidores.add(repartidor4);

        Envio envio1 = new Envio(1, "Calle 20 #15-30", ZonaCobertura.ARMENIA, 10.5, 3.0, 0.5, true);
        Envio envio2 = new Envio(2, "Carrera 8 #12-45", ZonaCobertura.MONTENEGRO, 25.0, 8.0, 1.2, false);
        Envio envio3 = new Envio(3, "Avenida Centenario #40-20", ZonaCobertura.CALARCA, 15.0, 2.5, 0.3, true);
        envio3.avanzarEstado();

        listEnvios.add(envio1);
        listEnvios.add(envio2);
        listEnvios.add(envio3);

        usuario1.getListEnviosPropios().add(envio1);
        usuario2.getListEnviosPropios().add(envio2);
        usuario3.getListEnviosPropios().add(envio3);

        System.out.println("✓Datos quemados cargados:");
        System.out.println("  - " + listUsuarios.size() + " usuarios");
        System.out.println("  - " + listRepartidores.size() + " repartidores");
        System.out.println("  - " + listEnvios.size() + " envíos");
    }

    public void agregarUsuario(Usuario usuario) {
        listUsuarios.add(usuario);
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario u : listUsuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public boolean eliminarUsuario(int id) {
        Usuario usuario = buscarUsuario(id);
        if (usuario != null) {
            listUsuarios.remove(usuario);
            return true;
        }
        return false;
    }

    public boolean actualizarUsuario(Usuario usuarioActualizado) {
        for (int i = 0; i < listUsuarios.size(); i++) {
            if (listUsuarios.get(i).getId() == usuarioActualizado.getId()) {
                listUsuarios.set(i, usuarioActualizado);
                return true;
            }
        }
        return false;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void agregarRepartidor(Repartidor repartidor) {
        listRepartidores.add(repartidor);
    }

    public Repartidor buscarRepartidor(int id) {
        for (Repartidor r : listRepartidores) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public boolean eliminarRepartidor(int id) {
        Repartidor repartidor = buscarRepartidor(id);
        if (repartidor != null) {
            listRepartidores.remove(repartidor);
            return true;
        }
        return false;
    }

    public boolean actualizarRepartidor(Repartidor repartidorActualizado) {
        for (int i = 0; i < listRepartidores.size(); i++) {
            if (listRepartidores.get(i).getId() == repartidorActualizado.getId()) {
                listRepartidores.set(i, repartidorActualizado);
                return true;
            }
        }
        return false;
    }

    public void agregarEnvio(Envio envio) {
        listEnvios.add(envio);
    }

    public Envio buscarEnvioPorId(int id) {
        for (Envio e : listEnvios) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public boolean eliminarEnvio(int id) {
        Envio envio = buscarEnvioPorId(id);
        if (envio != null) {
            listEnvios.remove(envio);
            return true;
        }
        return false;
    }

    public List<Repartidor> getListRepartidores() {
        return listRepartidores;
    }

    public List<Envio> getListEnvios() {
        return listEnvios;
    }

    public String getUsuario() {
        return id;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setUsuario(String id) {
        this.id = id;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}