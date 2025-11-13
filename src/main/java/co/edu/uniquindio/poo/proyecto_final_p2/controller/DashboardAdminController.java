package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.Administrador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DashboardAdminController {

    @FXML
    private Button btnGestionUsuarios;

    @FXML
    private Button btnGestionRepartidores;

    @FXML
    private Button btnAsignarEnvios;

    @FXML
    private VBox contentArea;

    private Administrador admin;

    /**
     * Método para inicializar el dashboard del administrador
     */
    public void inicializarDatos() {
        this.admin = Administrador.getInstancia();
        System.out.println("Administrador logueado");
        System.out.println("Total usuarios: " + admin.getListUsuarios().size());
        System.out.println("Total repartidores: " + admin.getListRepartidores().size());
        System.out.println("Total envíos: " + admin.getListEnvios().size());

        // Configurar listeners para los botones
        configurarBotones();
    }

    private void configurarBotones() {
        btnGestionUsuarios.setOnAction(event -> {
            System.out.println("Gestión de usuarios");
            // Aquí implementarás la lógica para gestionar usuarios
            mostrarUsuarios();
        });

        btnGestionRepartidores.setOnAction(event -> {
            System.out.println("Gestión de repartidores");
            // Aquí implementarás la lógica para gestionar repartidores
            mostrarRepartidores();
        });

        btnAsignarEnvios.setOnAction(event -> {
            System.out.println("Asignar envíos");
            // Aquí implementarás la lógica para asignar envíos
            mostrarEnvios();
        });
    }

    private void mostrarUsuarios() {
        System.out.println("=== USUARIOS REGISTRADOS ===");
        admin.getListUsuarios().forEach(usuario -> {
            System.out.println("ID: " + usuario.getId() + " - " + usuario.getNombre());
        });
    }

    private void mostrarRepartidores() {
        System.out.println("=== REPARTIDORES REGISTRADOS ===");
        admin.getListRepartidores().forEach(repartidor -> {
            System.out.println(repartidor.toString());
        });
    }

    private void mostrarEnvios() {
        System.out.println("=== ENVÍOS REGISTRADOS ===");
        admin.getListEnvios().forEach(envio -> {
            System.out.println(envio.toString());
        });
    }
}