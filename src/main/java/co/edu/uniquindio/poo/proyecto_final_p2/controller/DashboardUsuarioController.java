package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DashboardUsuarioController {

    @FXML
    private Button btnSolicitarEnvio;

    @FXML
    private Button btnConsultarEnvio;

    @FXML
    private Button btnActualizarDireccion;

    @FXML
    private VBox contentArea;

    private Usuario usuarioActual;

    /**
     * Método para inicializar los datos del usuario
     */
    public void inicializarDatos(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Usuario logueado: " + usuario.getNombre());

        // Aquí puedes configurar listeners para los botones
        configurarBotones();
    }

    private void configurarBotones() {
        btnSolicitarEnvio.setOnAction(event -> {
            System.out.println("Solicitar envío");
            // Aquí implementarás la lógica para solicitar envío
        });

        btnConsultarEnvio.setOnAction(event -> {
            System.out.println("Consultar envío");
            // Aquí implementarás la lógica para consultar envíos
        });

        btnActualizarDireccion.setOnAction(event -> {
            System.out.println("Actualizar dirección");
            // Aquí implementarás la lógica para actualizar dirección
        });
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}