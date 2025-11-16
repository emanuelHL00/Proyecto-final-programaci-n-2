package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {

    @FXML
    private ComboBox<String> cbTipoCuenta;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ChoiceBox<String> cbDisponibilidad;

    @FXML
    private ChoiceBox<String> cbZona;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Label lblMensaje;

    @FXML
    private javafx.scene.layout.VBox vboxRepartidor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbTipoCuenta.getItems().addAll("Usuario", "Repartidor");
        cbTipoCuenta.setValue("Usuario");

        cbDisponibilidad.getItems().addAll("ACTIVO", "INACTIVO", "EN_RUTA");
        cbDisponibilidad.setValue("ACTIVO");

        cbZona.getItems().addAll("ARMENIA", "MONTENEGRO", "CIRCASIA", "CALARCA", "LA_TEBAIDA");
        cbZona.setValue("ARMENIA");

        vboxRepartidor.setVisible(false);
        vboxRepartidor.setManaged(false);

        cbTipoCuenta.setOnAction(event -> {
            String tipoSeleccionado = cbTipoCuenta.getValue();
            boolean esRepartidor = "Repartidor".equals(tipoSeleccionado);
            vboxRepartidor.setVisible(esRepartidor);
            vboxRepartidor.setManaged(esRepartidor);
        });
    }


    @FXML
    void registrarse(ActionEvent event) {
        lblMensaje.setText("");

        try {
            String idStr = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            String tipoCuenta = cbTipoCuenta.getValue();

            if (idStr.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                lblMensaje.setText("Por favor complete todos los campos obligatorios");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                lblMensaje.setText("El ID debe ser un número válido");
                return;
            }

            if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblMensaje.setText("Por favor ingrese un correo válido");
                return;
            }

            if (contrasena.length() < 4) {
                lblMensaje.setText("La contraseña debe tener al menos 4 caracteres");
                return;
            }

            Administrador admin = Administrador.getInstancia();

            if (admin.buscarUsuario(id) != null || admin.buscarRepartidor(id) != null) {
                lblMensaje.setText("El ID ya está registrado. Use otro ID");
                return;
            }

            if ("Usuario".equals(tipoCuenta)) {
                Usuario nuevoUsuario = new Usuario.Builder()
                        .id(id)
                        .nombre(nombre)
                        .telefono(telefono)
                        .contrasena(contrasena)
                        .direccion("")
                        .build();

                admin.agregarUsuario(nuevoUsuario);

                mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);

            } else if ("Repartidor".equals(tipoCuenta)) {
                String disponibilidadStr = cbDisponibilidad.getValue();
                String zonaStr = cbZona.getValue();

                Disponibilidad disponibilidad = Disponibilidad.valueOf(disponibilidadStr);
                ZonaCobertura zona = ZonaCobertura.valueOf(zonaStr);

                Repartidor nuevoRepartidor = Repartidor.crearRepartidor(id, nombre, telefono, contrasena, zona);
                nuevoRepartidor.setDisponibilidad(disponibilidad);

                admin.agregarRepartidor(nuevoRepartidor);

                mostrarAlerta("Éxito", "Repartidor registrado correctamente", Alert.AlertType.INFORMATION);
            }

            volverAlLogin(event);

        } catch (Exception e) {
            lblMensaje.setText("Error al registrar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void volverAlLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyecto_final_p2/hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Iniciar Sesión");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver al login: " + e.getMessage(), Alert.AlertType.ERROR);
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