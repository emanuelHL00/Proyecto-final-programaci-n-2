package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.Repartidor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class DashboardRepartidorController {

    @FXML
    private Button btnConsultarEnvios;

    @FXML
    private Button btnCambiarDisponibilidad;

    @FXML
    private VBox contentArea;

    @FXML
    private ComboBox<String> comboDisponibilidad;

    @FXML
    private Button btnGuardarDisponibilidad;

    private Repartidor repartidorActual;

    /**
     * Método para inicializar los datos del repartidor
     */
    public void inicializarDatos(Repartidor repartidor) {
        this.repartidorActual = repartidor;
        System.out.println("Repartidor logueado: " + repartidor.getNombre());
        System.out.println("Zona: " + repartidor.getZona());
        System.out.println("Disponibilidad: " + repartidor.getDisponibilidad());

        // Configurar listeners para los botones
        configurarBotones();
    }

    private void configurarBotones() {
        btnConsultarEnvios.setOnAction(event -> {
            System.out.println("Consultar envíos asignados");
            // Aquí implementarás la lógica para consultar envíos
        });

        btnCambiarDisponibilidad.setOnAction(event -> {
            System.out.println("Cambiar disponibilidad");
            // Mostrar el ComboBox y botón para guardar
            comboDisponibilidad.setVisible(true);
            btnGuardarDisponibilidad.setVisible(true);
        });

        btnGuardarDisponibilidad.setOnAction(event -> {
            String nuevaDisponibilidad = comboDisponibilidad.getValue();
            System.out.println("Guardar disponibilidad: " + nuevaDisponibilidad);
            // Aquí implementarás la lógica para guardar la disponibilidad
        });
    }

    public Repartidor getRepartidorActual() {
        return repartidorActual;
    }
}