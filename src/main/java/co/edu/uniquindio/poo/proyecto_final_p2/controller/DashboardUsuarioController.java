package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

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


    public void inicializarDatos(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("Usuario logueado: " + usuario.getNombre());
        System.out.println("Dirección: " + usuario.getDireccion());
        System.out.println("Envíos registrados: " + usuario.getListEnviosPropios().size());

        configurarBotones();
    }

    private void configurarBotones() {
        btnSolicitarEnvio.setOnAction(event -> mostrarSolicitarEnvio());
        btnConsultarEnvio.setOnAction(event -> mostrarConsultarEnvios());
        btnActualizarDireccion.setOnAction(event -> mostrarActualizarDireccion());
    }


    private void mostrarSolicitarEnvio() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Solicitar Nuevo Envío");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Label lblInfo = new Label("Origen: Bodega Envíos Express");
        lblInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #495057; -fx-padding: 5;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField txtDestino = new TextField();
        txtDestino.setPromptText("Dirección de destino");

        ComboBox<ZonaCobertura> cbDepartamento = new ComboBox<>();
        cbDepartamento.getItems().addAll(
                ZonaCobertura.ARMENIA,
                ZonaCobertura.MONTENEGRO,
                ZonaCobertura.CIRCASIA,
                ZonaCobertura.CALARCA,
                ZonaCobertura.LA_TEBAIDA
        );
        cbDepartamento.setValue(ZonaCobertura.ARMENIA);

        TextField txtDistancia = new TextField();
        txtDistancia.setPromptText("Distancia en km");

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso en kg");

        TextField txtVolumen = new TextField();
        txtVolumen.setPromptText("Volumen en m³");

        CheckBox chkPrioridad = new CheckBox("Envío prioritario");

        ComboBox<String> cbEstrategia = new ComboBox<>();
        cbEstrategia.getItems().addAll("Normal", "Express", "Económico");
        cbEstrategia.setValue("Normal");

        Label lblCostoEstimado = new Label("Costo estimado: $0");
        lblCostoEstimado.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #28a745;");

        Label lblTiempoEstimado = new Label("Tiempo estimado: 0 horas");
        lblTiempoEstimado.setStyle("-fx-font-size: 14px; -fx-text-fill: #6c757d;");

        Button btnCalcular = new Button("Calcular Costo");
        btnCalcular.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-cursor: hand;");

        Button btnCrearEnvio = new Button("Crear Envío");
        btnCrearEnvio.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        grid.add(new Label("Destino:"), 0, 0);
        grid.add(txtDestino, 1, 0);
        grid.add(new Label("Departamento:"), 0, 1);
        grid.add(cbDepartamento, 1, 1);
        grid.add(new Label("Distancia (km):"), 0, 2);
        grid.add(txtDistancia, 1, 2);
        grid.add(new Label("Peso (kg):"), 0, 3);
        grid.add(txtPeso, 1, 3);
        grid.add(new Label("Volumen (m³):"), 0, 4);
        grid.add(txtVolumen, 1, 4);
        grid.add(chkPrioridad, 0, 5, 2, 1);
        grid.add(new Label("Tipo de envío:"), 0, 6);
        grid.add(cbEstrategia, 1, 6);

        btnCalcular.setOnAction(e -> {
            try {
                double distancia = Double.parseDouble(txtDistancia.getText().trim());
                double peso = Double.parseDouble(txtPeso.getText().trim());
                double volumen = Double.parseDouble(txtVolumen.getText().trim());
                boolean prioridad = chkPrioridad.isSelected();

                Envio envioTemp = new Envio(0, txtDestino.getText().trim(), cbDepartamento.getValue(),
                        distancia, peso, volumen, prioridad);

                IEstrategiaEnvio estrategia;
                switch (cbEstrategia.getValue()) {
                    case "Express":
                        estrategia = new EstrategiaExpress();
                        break;
                    case "Económico":
                        estrategia = new EstrategiaEconomico();
                        break;
                    default:
                        estrategia = new EstrategiaNormal();
                }

                usuarioActual.setEstrategiaEnvio(estrategia);
                double costo = usuarioActual.calcularCostoEnvio(envioTemp);
                int tiempo = usuarioActual.obtenerTiempoEstimado(envioTemp);

                lblCostoEstimado.setText(String.format("Costo estimado: $%.2f", costo));
                lblTiempoEstimado.setText(String.format("Tiempo estimado: %d horas", tiempo));

            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor ingrese valores numéricos válidos", Alert.AlertType.ERROR);
            }
        });

        btnCrearEnvio.setOnAction(e -> {
            try {
                String destino = txtDestino.getText().trim();
                if (destino.isEmpty()) {
                    mostrarAlerta("Error", "Ingrese una dirección de destino", Alert.AlertType.ERROR);
                    return;
                }

                double distancia = Double.parseDouble(txtDistancia.getText().trim());
                double peso = Double.parseDouble(txtPeso.getText().trim());
                double volumen = Double.parseDouble(txtVolumen.getText().trim());
                boolean prioridad = chkPrioridad.isSelected();

                IEstrategiaEnvio estrategia;
                switch (cbEstrategia.getValue()) {
                    case "Express":
                        estrategia = new EstrategiaExpress();
                        break;
                    case "Económico":
                        estrategia = new EstrategiaEconomico();
                        break;
                    default:
                        estrategia = new EstrategiaNormal();
                }

                usuarioActual.setEstrategiaEnvio(estrategia);

                Envio nuevoEnvio = usuarioActual.crearEnvio(
                        destino,
                        cbDepartamento.getValue(),
                        distancia,
                        peso,
                        volumen,
                        prioridad
                );

                double costoFinal = usuarioActual.calcularCostoEnvio(nuevoEnvio);
                int tiempoFinal = usuarioActual.obtenerTiempoEstimado(nuevoEnvio);

                Alert exitoAlert = new Alert(Alert.AlertType.INFORMATION);
                exitoAlert.setTitle("Envío Creado");
                exitoAlert.setHeaderText("¡Envío solicitado exitosamente!");
                exitoAlert.setContentText(String.format(
                        "Envío #%d\n" +
                                "Destino: %s\n" +
                                "Tipo: %s\n" +
                                "Costo: $%.2f\n" +
                                "Tiempo estimado: %d horas\n" +
                                "Estado: %s",
                        nuevoEnvio.getId(),
                        nuevoEnvio.getDestino(),
                        cbEstrategia.getValue(),
                        costoFinal,
                        tiempoFinal,
                        nuevoEnvio.getEstadoEnvio()
                ));
                exitoAlert.showAndWait();

                txtDestino.clear();
                txtDistancia.clear();
                txtPeso.clear();
                txtVolumen.clear();
                chkPrioridad.setSelected(false);
                cbEstrategia.setValue("Normal");
                lblCostoEstimado.setText("Costo estimado: $0");
                lblTiempoEstimado.setText("Tiempo estimado: 0 horas");

            } catch (NumberFormatException ex) {
                mostrarAlerta("Error", "Por favor complete todos los campos correctamente", Alert.AlertType.ERROR);
            }
        });

        contentArea.getChildren().addAll(
                titulo,
                lblInfo,
                grid,
                btnCalcular,
                lblCostoEstimado,
                lblTiempoEstimado,
                btnCrearEnvio
        );
        contentArea.setSpacing(10);
    }


    private void mostrarConsultarEnvios() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Mis Envíos");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        if (usuarioActual.getListEnviosPropios().isEmpty()) {
            Label lblNoEnvios = new Label("No tienes envíos registrados");
            lblNoEnvios.setStyle("-fx-font-size: 16px; -fx-text-fill: #6c757d; -fx-padding: 20;");
            contentArea.getChildren().addAll(titulo, lblNoEnvios);
            return;
        }

        TableView<Envio> tablaEnvios = new TableView<>();
        tablaEnvios.setPrefHeight(300);

        TableColumn<Envio, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject()
        );

        TableColumn<Envio, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDestino())
        );

        TableColumn<Envio, ZonaCobertura> colDepartamento = new TableColumn<>("Departamento");
        colDepartamento.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDepartamento())
        );

        TableColumn<Envio, Double> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(c ->
                new javafx.beans.property.SimpleDoubleProperty(c.getValue().getPeso()).asObject()
        );

        TableColumn<Envio, EstadoEnvio> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(c ->
                new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getEstadoEnvio())
        );

        tablaEnvios.getColumns().addAll(colId, colDestino, colDepartamento, colPeso, colEstado);

        ObservableList<Envio> listaEnvios =
                FXCollections.observableArrayList(usuarioActual.getListEnviosPropios());
        tablaEnvios.setItems(listaEnvios);

        Button btnVerDetalles = new Button("Ver Detalles");
        btnVerDetalles.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-cursor: hand;");

        Button btnCalcularCosto = new Button("Calcular Costo");
        btnCalcularCosto.setStyle("-fx-background-color: #17a2b8; -fx-text-fill: white; -fx-cursor: hand;");

        Button btnCancelar = new Button("Cancelar Envío");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-cursor: hand;");


        btnVerDetalles.setOnAction(e -> {
            Envio seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                mostrarDetallesEnvio(seleccionado);
            } else {
                mostrarAlerta("Advertencia", "Seleccione un envío", Alert.AlertType.WARNING);
            }
        });

        btnCalcularCosto.setOnAction(e -> {
            Envio seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                double costo = usuarioActual.calcularCostoEnvio(seleccionado);
                int tiempo = usuarioActual.obtenerTiempoEstimado(seleccionado);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cálculo de Costo");
                alert.setHeaderText("Envío #" + seleccionado.getId());
                alert.setContentText(String.format(
                        "Estrategia: %s\nCosto total: $%.2f\nTiempo estimado: %d horas",
                        usuarioActual.getEstrategiaEnvio().obtenerDescripcion(),
                        costo,
                        tiempo
                ));
                alert.showAndWait();
            } else {
                mostrarAlerta("Advertencia", "Seleccione un envío", Alert.AlertType.WARNING);
            }
        });

        btnCancelar.setOnAction(e -> {
            Envio seleccionado = tablaEnvios.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {

                if (!seleccionado.puedeModificar()) {
                    mostrarAlerta("Advertencia",
                            "Este envío no puede cancelarse en su estado actual",
                            Alert.AlertType.WARNING);
                    return;
                }

                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Cancelar Envío");
                confirmacion.setHeaderText("¿Está seguro?");
                confirmacion.setContentText("Envío #" + seleccionado.getId() + " - " + seleccionado.getDestino());

                confirmacion.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        seleccionado.cancelarEnvio();
                        tablaEnvios.refresh();
                        mostrarAlerta("Éxito",
                                "Envío cancelado. Estado: " + seleccionado.getEstadoEnvio(),
                                Alert.AlertType.INFORMATION);
                    }
                });

            } else {
                mostrarAlerta("Advertencia", "Seleccione un envío", Alert.AlertType.WARNING);
            }
        });

        contentArea.getChildren().addAll(titulo, tablaEnvios, btnVerDetalles, btnCalcularCosto, btnCancelar);
        contentArea.setSpacing(10);
    }


    private void mostrarDetallesEnvio(Envio envio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles del Envío");
        alert.setHeaderText("Envío #" + envio.getId());

        String detalles = String.format(
                "Origen: %s\n" +
                        "Destino: %s\n" +
                        "Departamento: %s\n" +
                        "Distancia: %.2f km\n" +
                        "Peso: %.2f kg\n" +
                        "Volumen: %.2f m³\n" +
                        "Prioridad: %s\n" +
                        "Estado: %s\n" +
                        "Descripción: %s",
                envio.getOrigen(),
                envio.getDestino(),
                envio.getDepartamento(),
                envio.getDistancia(),
                envio.getPeso(),
                envio.getVolumen(),
                envio.isPrioridad() ? "Sí" : "No",
                envio.getEstadoEnvio(),
                envio.obtenerDescripcionEstado()
        );

        alert.setContentText(detalles);
        alert.showAndWait();
    }


    private void mostrarActualizarDireccion() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Actualizar Mi Dirección");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Label lblActual = new Label("Dirección actual: " + usuarioActual.getDireccion());
        lblActual.setStyle("-fx-font-size: 14px; -fx-text-fill: #495057; -fx-padding: 10;");

        Label lblNueva = new Label("Nueva dirección:");
        lblNueva.setStyle("-fx-font-size: 14px; -fx-padding: 10 0 5 0;");

        TextField txtNuevaDireccion = new TextField(usuarioActual.getDireccion());
        txtNuevaDireccion.setPromptText("Ingrese la nueva dirección");
        txtNuevaDireccion.setPrefWidth(350);

        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10 20;");

        btnGuardar.setOnAction(e -> {
            String nuevaDireccion = txtNuevaDireccion.getText().trim();

            if (nuevaDireccion.isEmpty()) {
                mostrarAlerta("Error", "La dirección no puede estar vacía", Alert.AlertType.ERROR);
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Cambio");
            confirmacion.setHeaderText("¿Desea actualizar su dirección?");
            confirmacion.setContentText("Nueva dirección: " + nuevaDireccion);

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                usuarioActual.setDireccion(nuevaDireccion);

                Administrador admin = Administrador.getInstancia();
                admin.actualizarUsuario(usuarioActual);

                mostrarAlerta("Éxito", "Dirección actualizada correctamente", Alert.AlertType.INFORMATION);

                lblActual.setText("Dirección actual: " + usuarioActual.getDireccion());
            }
        });

        contentArea.getChildren().addAll(titulo, lblActual, lblNueva, txtNuevaDireccion, btnGuardar);
        contentArea.setSpacing(10);
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}