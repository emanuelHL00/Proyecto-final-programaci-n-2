package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.Administrador;
import co.edu.uniquindio.poo.proyecto_final_p2.model.Repartidor;
import co.edu.uniquindio.poo.proyecto_final_p2.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    /**
     * Método para manejar el inicio de sesión
     */
    @FXML
    void inicioSesion(ActionEvent event) {
        String idStr = txtId.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        // Validación básica
        if (idStr.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        // Obtener instancia del Administrador
        Administrador admin = Administrador.getInstancia();

        // PRIMERO: Verificar si es el administrador
        if (idStr.equals(admin.getUsuario()) && contrasena.equals(admin.getContrasenia())) {
            // Login exitoso como Administrador
            abrirDashboardAdmin();
            return;
        }

        // SEGUNDO: Intentar convertir el ID a número para buscar usuarios/repartidores
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID inválido. Use 'admin' para administrador o un número para usuario/repartidor", Alert.AlertType.ERROR);
            return;
        }

        // TERCERO: Buscar en la lista de usuarios
        Usuario usuario = admin.buscarUsuario(id);
        if (usuario != null) {
            // Verificar contraseña
            if (usuario.getContrasena().equals(contrasena)) {
                // Login exitoso como Usuario
                abrirDashboardUsuario(usuario);
                return;
            } else {
                mostrarAlerta("Error", "Contraseña incorrecta", Alert.AlertType.ERROR);
                return;
            }
        }

        // CUARTO: Si no es usuario, buscar en la lista de repartidores
        Repartidor repartidor = admin.buscarRepartidor(id);
        if (repartidor != null) {
            // Verificar contraseña
            if (repartidor.getContrasena().equals(contrasena)) {
                // Login exitoso como Repartidor
                abrirDashboardRepartidor(repartidor);
                return;
            } else {
                mostrarAlerta("Error", "Contraseña incorrecta", Alert.AlertType.ERROR);
                return;
            }
        }

        // Si no se encontró en ninguna lista
        mostrarAlerta("Error", "Usuario no encontrado. Por favor regístrese", Alert.AlertType.ERROR);
    }

    /**
     * Método para abrir el Dashboard de Administrador
     */
    private void abrirDashboardAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/DashboardAdmin.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            DashboardAdminController controller = loader.getController();
            controller.inicializarDatos();

            // Cambiar la escena
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Dashboard Administrador");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el Dashboard de Administrador: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para abrir el Dashboard de Usuario
     */
    private void abrirDashboardUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/DashboardUsuario.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasar el usuario
            DashboardUsuarioController controller = loader.getController();
            controller.inicializarDatos(usuario);

            // Cambiar la escena
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Dashboard Usuario - " + usuario.getNombre());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el Dashboard de Usuario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para abrir el Dashboard de Repartidor
     */
    private void abrirDashboardRepartidor(Repartidor repartidor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/DashboardRepartidor.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasar el repartidor
            DashboardRepartidorController controller = loader.getController();
            controller.inicializarDatos(repartidor);

            // Cambiar la escena
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Dashboard Repartidor - " + repartidor.getNombre());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el Dashboard de Repartidor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método para cambiar a la vista de registro
     */
    @FXML
    void PasarRegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/RegistrarUsuario.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Registro de Usuario");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de registro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método auxiliar para mostrar alertas
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}