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
        // Configurar ComboBox de tipo de cuenta
        cbTipoCuenta.getItems().addAll("Usuario", "Repartidor");
        cbTipoCuenta.setValue("Usuario");

        // Configurar ChoiceBox de disponibilidad
        cbDisponibilidad.getItems().addAll("ACTIVO", "INACTIVO", "EN_RUTA");
        cbDisponibilidad.setValue("ACTIVO");

        // Configurar ChoiceBox de zona
        cbZona.getItems().addAll("ARMENIA", "MONTENEGRO", "CIRCASIA", "CALARCA", "LA_TEBAIDA");
        cbZona.setValue("ARMENIA");

        // Ocultar campos de repartidor por defecto
        vboxRepartidor.setVisible(false);
        vboxRepartidor.setManaged(false);

        // Listener para mostrar/ocultar campos según tipo de cuenta
        cbTipoCuenta.setOnAction(event -> {
            String tipoSeleccionado = cbTipoCuenta.getValue();
            boolean esRepartidor = "Repartidor".equals(tipoSeleccionado);
            vboxRepartidor.setVisible(esRepartidor);
            vboxRepartidor.setManaged(esRepartidor);
        });
    }

    /**
     * Método para registrar un nuevo usuario o repartidor
     */
    @FXML
    void registrarse(ActionEvent event) {
        // Limpiar mensaje anterior
        lblMensaje.setText("");

        try {
            // Obtener datos de los campos
            String idStr = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            String tipoCuenta = cbTipoCuenta.getValue();

            // Validar campos obligatorios
            if (idStr.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                lblMensaje.setText("Por favor complete todos los campos obligatorios");
                return;
            }

            // Validar que el ID sea un número
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                lblMensaje.setText("El ID debe ser un número válido");
                return;
            }

            // Validar formato de correo
            if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblMensaje.setText("Por favor ingrese un correo válido");
                return;
            }

            // Validar longitud de contraseña
            if (contrasena.length() < 4) {
                lblMensaje.setText("La contraseña debe tener al menos 4 caracteres");
                return;
            }

            // Obtener instancia del Administrador (Singleton)
            Administrador admin = Administrador.getInstancia();

            // Verificar que el ID no exista ya
            if (admin.buscarUsuario(id) != null || admin.buscarRepartidor(id) != null) {
                lblMensaje.setText("El ID ya está registrado. Use otro ID");
                return;
            }

            // Registrar según el tipo de cuenta
            if ("Usuario".equals(tipoCuenta)) {
                // Crear usuario usando el patrón Builder
                Usuario nuevoUsuario = new Usuario.Builder()
                        .id(id)
                        .nombre(nombre)
                        .telefono(telefono)
                        .contrasena(contrasena)
                        .direccion("") // Dirección vacía por defecto
                        .build();

                // Agregar a la lista del administrador
                admin.agregarUsuario(nuevoUsuario);

                mostrarAlerta("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);

            } else if ("Repartidor".equals(tipoCuenta)) {
                // Obtener datos adicionales de repartidor
                String disponibilidadStr = cbDisponibilidad.getValue();
                String zonaStr = cbZona.getValue();

                // Convertir a enums
                Disponibilidad disponibilidad = Disponibilidad.valueOf(disponibilidadStr);
                ZonaCobertura zona = ZonaCobertura.valueOf(zonaStr);

                // Crear repartidor usando el patrón Factory
                Repartidor nuevoRepartidor = Repartidor.crearRepartidor(id, nombre, telefono, contrasena, zona);
                nuevoRepartidor.setDisponibilidad(disponibilidad);

                // Agregar a la lista del administrador
                admin.agregarRepartidor(nuevoRepartidor);

                mostrarAlerta("Éxito", "Repartidor registrado correctamente", Alert.AlertType.INFORMATION);
            }

            // Volver a la pantalla de login
            volverAlLogin(event);

        } catch (Exception e) {
            lblMensaje.setText("Error al registrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para volver a la pantalla de login
     */
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