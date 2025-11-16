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


    @FXML
    void inicioSesion(ActionEvent event) {
        String idStr = txtId.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (idStr.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
            return;
        }

        Administrador admin = Administrador.getInstancia();

        if (idStr.equals(admin.getUsuario()) && contrasena.equals(admin.getContrasenia())) {
            abrirDashboardAdmin();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "ID inválido. Use 'admin' para administrador o un número para usuario/repartidor", Alert.AlertType.ERROR);
            return;
        }

        Usuario usuario = admin.buscarUsuario(id);
        if (usuario != null) {
            if (usuario.getContrasena().equals(contrasena)) {
                abrirDashboardUsuario(usuario);
                return;
            } else {
                mostrarAlerta("Error", "Contraseña incorrecta", Alert.AlertType.ERROR);
                return;
            }
        }

        Repartidor repartidor = admin.buscarRepartidor(id);
        if (repartidor != null) {
            if (repartidor.getContrasena().equals(contrasena)) {
                abrirDashboardRepartidor(repartidor);
                return;
            } else {
                mostrarAlerta("Error", "Contraseña incorrecta", Alert.AlertType.ERROR);
                return;
            }
        }

        mostrarAlerta("Error", "Usuario no encontrado. Por favor regístrese", Alert.AlertType.ERROR);
    }


    private void abrirDashboardAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/DashboardAdmin.fxml"));
            Parent root = loader.load();

            DashboardAdminController controller = loader.getController();
            controller.inicializarDatos();

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


    private void abrirDashboardUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/DashboardUsuario.fxml"));
            Parent root = loader.load();

            DashboardUsuarioController controller = loader.getController();
            controller.inicializarDatos(usuario);

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


    private void abrirDashboardRepartidor(Repartidor repartidor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/DashboardRepartidor.fxml"));
            Parent root = loader.load();

            DashboardRepartidorController controller = loader.getController();
            controller.inicializarDatos(repartidor);

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


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}