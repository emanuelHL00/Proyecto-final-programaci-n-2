package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

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

        // Mostrar información inicial
        mostrarInformacionInicial();
    }

    private void configurarBotones() {
        btnConsultarEnvios.setOnAction(event -> {
            System.out.println("Consultar envíos asignados");
            mostrarEnviosAsignados();
        });

        btnCambiarDisponibilidad.setOnAction(event -> {
            System.out.println("Cambiar disponibilidad");
            mostrarCambiarDisponibilidad();
        });

        btnGuardarDisponibilidad.setOnAction(event -> {
            String nuevaDisponibilidad = comboDisponibilidad.getValue();
            System.out.println("Guardar disponibilidad: " + nuevaDisponibilidad);
            guardarDisponibilidad();
        });
    }

    /**
     * Muestra información inicial del repartidor
     */
    private void mostrarInformacionInicial() {
        contentArea.getChildren().clear();

        Label lblTitulo = new Label("Bienvenido, " + repartidorActual.getNombre());
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Label lblInfo = new Label(
                "Zona de cobertura: " + repartidorActual.getZona() + "\n" +
                        "Estado actual: " + repartidorActual.getDisponibilidad()
        );
        lblInfo.setStyle("-fx-font-size: 16px; -fx-text-fill: #495057; -fx-padding: 10 0 0 0;");

        // Contar envíos de su zona
        Administrador admin = Administrador.getInstancia();
        long enviosPendientes = admin.getListEnvios().stream()
                .filter(e -> e.getDepartamento().equals(repartidorActual.getZona()))
                .filter(e -> e.getEstadoEnvio() == EstadoEnvio.SOLICITADO ||
                        e.getEstadoEnvio() == EstadoEnvio.ASIGANDO)
                .count();

        Label lblEnvios = new Label("Envíos disponibles en tu zona: " + enviosPendientes);
        lblEnvios.setStyle("-fx-font-size: 14px; -fx-text-fill: #6c757d; -fx-padding: 5 0 0 0;");

        VBox infoBox = new VBox(10, lblTitulo, lblInfo, lblEnvios);
        infoBox.setPadding(new Insets(20));
        infoBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-radius: 5; -fx-background-radius: 5;");

        contentArea.getChildren().add(infoBox);
    }


    /**
     * Muestra los envíos asignados o disponibles en la zona del repartidor
     */
    private void mostrarEnviosAsignados() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Envíos en tu Zona: " + repartidorActual.getZona());
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        // Crear tabla de envíos
        TableView<Envio> tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
// Columna ID
        TableColumn<Envio, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

// Columna Destino
        TableColumn<Envio, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDestino()));

// Columna Peso
        TableColumn<Envio, Double> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPeso()).asObject());

// Columna Distancia
        TableColumn<Envio, Double> colDistancia = new TableColumn<>("Distancia (km)");
        colDistancia.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getDistancia()).asObject());

// Columna Estado
        TableColumn<Envio, EstadoEnvio> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEstadoEnvio()));

// Columna Zona/Departamento
        TableColumn<Envio, ZonaCobertura> colZona = new TableColumn<>("Zona");
        colZona.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDepartamento()));

// Columna Prioridad (ya te funciona)
        TableColumn<Envio, Boolean> colPrioridad = new TableColumn<>("Prioridad");
        colPrioridad.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().isPrioridad()));
        colPrioridad.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Sí" : "NO");
                    setStyle(item ? "-fx-text-fill: red; -fx-font-weight: bold;" : "");
                }
            }
        });

        tabla.getColumns().addAll(colId, colDestino, colPeso, colDistancia, colEstado, colZona, colPrioridad);

        colPrioridad.setCellFactory(column -> new TableCell<Envio, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item ? "Sí" : "No");
                    setStyle(item ? "-fx-text-fill: red; -fx-font-weight: bold;" : "");
                }
            }
        });
        colPrioridad.setPrefWidth(80);

        tabla.getColumns().addAll(colId, colDestino, colPeso, colDistancia, colEstado, colPrioridad);

        // === DEBUG: Información del filtrado ===
        Administrador admin = Administrador.getInstancia();
        System.out.println("\n=== DEBUG ENVÍOS ===");
        System.out.println("Total envíos en sistema: " + admin.getListEnvios().size());
        System.out.println("Zona del repartidor: " + repartidorActual.getZona());

        // 🔹 Filtrar correctamente usando ENUM y evitando nulls
        List<Envio> enviosFiltrados = admin.getListEnvios().stream()
                .filter(e -> {
                    Object dep = e.getDepartamento();
                    ZonaCobertura zonaRep = repartidorActual.getZona();

                    if (dep instanceof ZonaCobertura) {
                        return dep.equals(zonaRep);
                    } else if (dep instanceof String) {
                        return ((String) dep).equalsIgnoreCase(zonaRep.name());
                    }
                    return false;
                })
                .collect(Collectors.toList());


        System.out.println("Envíos filtrados: " + enviosFiltrados.size());
        enviosFiltrados.forEach(e -> System.out.println("  - Envío #" + e.getId() + " (" + e.getDepartamento() + ")"));

        // Cargar datos a la tabla
        ObservableList<Envio> envios = FXCollections.observableArrayList(enviosFiltrados);
        tabla.setItems(envios);

        // Si no hay envíos
        if (envios.isEmpty()) {
            Label lblVacio = new Label("No hay envíos disponibles en tu zona actualmente.");
            lblVacio.setStyle("-fx-font-size: 14px; -fx-text-fill: #6c757d; -fx-padding: 20;");
            VBox emptyBox = new VBox(lblVacio);
            emptyBox.setStyle("-fx-alignment: center; -fx-padding: 40;");
            tabla.setPlaceholder(emptyBox);
        }

        // Botones (NO tocados)
        Button btnAvanzarEstado = new Button("Avanzar Estado del Envío");
        btnAvanzarEstado.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAvanzarEstado.setOnAction(e -> avanzarEstadoEnvio(tabla));

        Button btnIncidencia = new Button("Reportar Incidencia");
        btnIncidencia.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnIncidencia.setOnAction(e -> reportarIncidencia(tabla));

        Label lblInfo = new Label("Selecciona un envío y usa los botones para gestionar el estado");
        lblInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d; -fx-padding: 5 0 0 0;");

        VBox container = new VBox(10, titulo, tabla, lblInfo, btnAvanzarEstado, btnIncidencia);
        container.setPadding(new Insets(10));
        contentArea.getChildren().add(container);
    }



    /**
     * Avanza el estado del envío seleccionado
     */
    private void avanzarEstadoEnvio(TableView<Envio> tabla) {
        Envio seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor selecciona un envío", Alert.AlertType.WARNING);
            return;
        }

        // Verificar que el repartidor esté disponible
        if (repartidorActual.getDisponibilidad() == Disponibilidad.INACTIVO) {
            mostrarAlerta("Error", "Debes estar ACTIVO o EN_RUTA para gestionar envíos", Alert.AlertType.ERROR);
            return;
        }

        // Avanzar el estado usando el patrón State
        try {
            EstadoEnvio estadoAnterior = seleccionado.getEstadoEnvio();
            seleccionado.avanzarEstado();

            // Si el envío pasó a EN_RUTA, actualizar disponibilidad del repartidor
            if (seleccionado.getEstadoEnvio() == EstadoEnvio.EN_RUTA) {
                repartidorActual.setDisponibilidad(Disponibilidad.EN_RUTA);
            }

            // Si el envío fue entregado, liberar al repartidor
            if (seleccionado.getEstadoEnvio() == EstadoEnvio.ENTREGADO) {
                repartidorActual.setDisponibilidad(Disponibilidad.ACTIVO);
            }

            tabla.refresh();
            mostrarAlerta("Éxito",
                    "Estado actualizado:\n" +
                            estadoAnterior + " → " + seleccionado.getEstadoEnvio() + "\n\n" +
                            seleccionado.obtenerDescripcionEstado(),
                    Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo avanzar el estado: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Reporta una incidencia en el envío
     */
    private void reportarIncidencia(TableView<Envio> tabla) {
        Envio seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor selecciona un envío", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Reportar Incidencia");
        confirmacion.setHeaderText("¿Estás seguro de reportar una incidencia?");
        confirmacion.setContentText("Envío #" + seleccionado.getId() + " - " + seleccionado.getDestino());

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                seleccionado.cancelarEnvio();

                // Liberar al repartidor si estaba en ruta
                if (repartidorActual.getDisponibilidad() == Disponibilidad.EN_RUTA) {
                    repartidorActual.setDisponibilidad(Disponibilidad.ACTIVO);
                }

                tabla.refresh();
                mostrarAlerta("Incidencia Reportada",
                        "El envío #" + seleccionado.getId() + " ha sido marcado con incidencia.\n" +
                                "Estado actual: " + seleccionado.getEstadoEnvio(),
                        Alert.AlertType.INFORMATION);
            }
        });
    }

    /**
     * Muestra la interfaz para cambiar disponibilidad
     */
    private void mostrarCambiarDisponibilidad() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Cambiar Disponibilidad");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Label lblActual = new Label("Disponibilidad actual: " + repartidorActual.getDisponibilidad());
        lblActual.setStyle("-fx-font-size: 16px; -fx-text-fill: #495057; -fx-padding: 10 0 10 0;");

        Label lblSeleccionar = new Label("Selecciona nueva disponibilidad:");
        lblSeleccionar.setStyle("-fx-font-size: 14px; -fx-text-fill: #212529;");

        // ComboBox para seleccionar disponibilidad
        ComboBox<Disponibilidad> cbDisponibilidad = new ComboBox<>();
        cbDisponibilidad.getItems().addAll(Disponibilidad.values());
        cbDisponibilidad.setValue(repartidorActual.getDisponibilidad());
        cbDisponibilidad.setPrefWidth(200);

        // Descripción de cada estado
        Label lblDescripcion = new Label();
        lblDescripcion.setStyle("-fx-font-size: 12px; -fx-text-fill: #6c757d; -fx-padding: 5 0 0 0;");
        lblDescripcion.setWrapText(true);

        cbDisponibilidad.setOnAction(e -> {
            Disponibilidad seleccionada = cbDisponibilidad.getValue();
            switch (seleccionada) {
                case ACTIVO:
                    lblDescripcion.setText("✓ Disponible para recibir nuevos envíos");
                    break;
                case INACTIVO:
                    lblDescripcion.setText("⚠ No disponible para envíos (descanso/fin de turno)");
                    break;
                case EN_RUTA:
                    lblDescripcion.setText("🚚 Actualmente realizando una entrega");
                    break;
            }
        });

        // Trigger inicial
        cbDisponibilidad.fireEvent(new javafx.event.ActionEvent());

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnGuardar.setOnAction(e -> {
            Disponibilidad nuevaDisponibilidad = cbDisponibilidad.getValue();

            // Validar que no esté EN_RUTA si tiene envíos pendientes
            if (nuevaDisponibilidad == Disponibilidad.INACTIVO) {
                long enviosEnRuta = Administrador.getInstancia().getListEnvios().stream()
                        .filter(env -> env.getDepartamento().equals(repartidorActual.getZona()))
                        .filter(env -> env.getEstadoEnvio() == EstadoEnvio.EN_RUTA)
                        .count();

                if (enviosEnRuta > 0) {
                    mostrarAlerta("Error",
                            "No puedes ponerte INACTIVO mientras tengas envíos EN_RUTA.\n" +
                                    "Completa tus entregas primero.",
                            Alert.AlertType.ERROR);
                    return;
                }
            }

            repartidorActual.setDisponibilidad(nuevaDisponibilidad);
            Administrador.getInstancia().actualizarRepartidor(repartidorActual);

            mostrarAlerta("Éxito",
                    "Tu disponibilidad ha sido actualizada a: " + nuevaDisponibilidad,
                    Alert.AlertType.INFORMATION);

            mostrarInformacionInicial();
        });

        VBox container = new VBox(15, titulo, lblActual, lblSeleccionar, cbDisponibilidad, lblDescripcion, btnGuardar);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 5; -fx-background-radius: 5;");

        contentArea.getChildren().add(container);
    }

    /**
     * Guarda la disponibilidad (método legacy para el botón del FXML)
     */
    private void guardarDisponibilidad() {
        String nuevaDisponibilidad = comboDisponibilidad.getValue();
        if (nuevaDisponibilidad != null) {
            try {
                Disponibilidad disponibilidad = Disponibilidad.valueOf(nuevaDisponibilidad.toUpperCase().replace(" ", "_"));
                repartidorActual.setDisponibilidad(disponibilidad);

                Administrador admin = Administrador.getInstancia();
                admin.actualizarRepartidor(repartidorActual);

                mostrarAlerta("Éxito", "Disponibilidad actualizada a: " + disponibilidad, Alert.AlertType.INFORMATION);

                // Ocultar controles
                comboDisponibilidad.setVisible(false);
                btnGuardarDisponibilidad.setVisible(false);
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al actualizar disponibilidad", Alert.AlertType.ERROR);
            }
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

    public Repartidor getRepartidorActual() {
        return repartidorActual;
    }
}