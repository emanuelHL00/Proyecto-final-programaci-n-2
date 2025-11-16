package co.edu.uniquindio.poo.proyecto_final_p2.controller;

import co.edu.uniquindio.poo.proyecto_final_p2.model.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

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


    public void inicializarDatos() {
        this.admin = Administrador.getInstancia();
        System.out.println("Administrador logueado");
        System.out.println("Total usuarios: " + admin.getListUsuarios().size());
        System.out.println("Total repartidores: " + admin.getListRepartidores().size());
        System.out.println("Total envíos: " + admin.getListEnvios().size());

        configurarBotones();
    }

    private void configurarBotones() {
        btnGestionUsuarios.setOnAction(event -> mostrarGestionUsuarios());
        btnGestionRepartidores.setOnAction(event -> mostrarGestionRepartidores());
        btnAsignarEnvios.setOnAction(event -> mostrarGestionEnvios());
    }


    private void mostrarGestionUsuarios() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Gestión de Usuarios");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Button btnAgregar = new Button("Agregar Usuario");
        Button btnEditar = new Button("Editar Usuario");
        Button btnEliminar = new Button("Eliminar Usuario");

        btnAgregar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-cursor: hand;");
        btnEditar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-cursor: hand;");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-cursor: hand;");

        TableView<Usuario> tablaUsuarios = new TableView<>();
        tablaUsuarios.setPrefHeight(300);
        tablaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colId.setPrefWidth(60);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setPrefWidth(150);

        TableColumn<Usuario, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefono()));
        colTelefono.setPrefWidth(120);

        TableColumn<Usuario, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDireccion()));
        colDireccion.setPrefWidth(150);

        tablaUsuarios.getColumns().addAll(colId, colNombre, colTelefono, colDireccion);

        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(admin.getListUsuarios());
        tablaUsuarios.setItems(listaUsuarios);

        btnAgregar.setOnAction(e -> {
            agregarUsuario();
            listaUsuarios.setAll(admin.getListUsuarios());
        });

        btnEditar.setOnAction(e -> {
            Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                editarUsuario(seleccionado);
                listaUsuarios.setAll(admin.getListUsuarios());
            } else {
                mostrarAlerta("Advertencia", "Seleccione un usuario para editar", Alert.AlertType.WARNING);
            }
        });

        btnEliminar.setOnAction(e -> {
            Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText("¿Está seguro de eliminar este usuario?");
                confirmacion.setContentText(seleccionado.getNombre());

                Optional<ButtonType> resultado = confirmacion.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    if (admin.eliminarUsuario(seleccionado.getId())) {
                        listaUsuarios.setAll(admin.getListUsuarios());
                        mostrarAlerta("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
                    }
                }
            } else {
                mostrarAlerta("Advertencia", "Seleccione un usuario para eliminar", Alert.AlertType.WARNING);
            }
        });

        contentArea.getChildren().addAll(titulo, btnAgregar, btnEditar, btnEliminar, tablaUsuarios);
        contentArea.setSpacing(10);
    }


    private void agregarUsuario() {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Agregar Usuario");
        dialog.setHeaderText("Ingrese los datos del nuevo usuario");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtId = new TextField();
        txtId.setPromptText("ID");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");
        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        TextField txtDireccion = new TextField();
        txtDireccion.setPromptText("Dirección");
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Dirección:"), 0, 3);
        grid.add(txtDireccion, 1, 3);
        grid.add(new Label("Contraseña:"), 0, 4);
        grid.add(txtContrasena, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    int id = Integer.parseInt(txtId.getText().trim());

                    if (admin.buscarUsuario(id) != null) {
                        mostrarAlerta("Error", "El ID ya existe", Alert.AlertType.ERROR);
                        return null;
                    }

                    Usuario nuevoUsuario = new Usuario.Builder()
                            .id(id)
                            .nombre(txtNombre.getText().trim())
                            .telefono(txtTelefono.getText().trim())
                            .direccion(txtDireccion.getText().trim())
                            .contrasena(txtContrasena.getText().trim())
                            .build();

                    admin.agregarUsuario(nuevoUsuario);
                    mostrarAlerta("Éxito", "Usuario agregado correctamente", Alert.AlertType.INFORMATION);
                    return nuevoUsuario;
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El ID debe ser un número válido", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void editarUsuario(Usuario usuario) {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Editar Usuario");
        dialog.setHeaderText("Modifique los datos del usuario");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtNombre = new TextField(usuario.getNombre());
        TextField txtTelefono = new TextField(usuario.getTelefono());
        TextField txtDireccion = new TextField(usuario.getDireccion());
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setText(usuario.getContrasena());

        grid.add(new Label("ID: " + usuario.getId()), 0, 0, 2, 1);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Dirección:"), 0, 3);
        grid.add(txtDireccion, 1, 3);
        grid.add(new Label("Contraseña:"), 0, 4);
        grid.add(txtContrasena, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                usuario.setNombre(txtNombre.getText().trim());
                usuario.setTelefono(txtTelefono.getText().trim());
                usuario.setDireccion(txtDireccion.getText().trim());
                usuario.setContrasena(txtContrasena.getText().trim());

                admin.actualizarUsuario(usuario);
                mostrarAlerta("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
                return usuario;
            }
            return null;
        });

        dialog.showAndWait();
    }


    private void mostrarGestionRepartidores() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Gestión de Repartidores");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Button btnAgregar = new Button("Agregar Repartidor");
        Button btnEditar = new Button("Editar Repartidor");
        Button btnEliminar = new Button("Eliminar Repartidor");

        btnAgregar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-cursor: hand;");
        btnEditar.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-cursor: hand;");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-cursor: hand;");

        TableView<Repartidor> tablaRepartidores = new TableView<>();
        tablaRepartidores.setPrefHeight(300);
        tablaRepartidores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Repartidor, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colId.setPrefWidth(60);

        TableColumn<Repartidor, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setPrefWidth(120);

        TableColumn<Repartidor, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTelefono()));
        colTelefono.setPrefWidth(100);

        TableColumn<Repartidor, ZonaCobertura> colZona = new TableColumn<>("Zona");
        colZona.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getZona()));
        colZona.setPrefWidth(100);

        colZona.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(ZonaCobertura item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        TableColumn<Repartidor, Disponibilidad> colDisponibilidad = new TableColumn<>("Disponibilidad");
        colDisponibilidad.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDisponibilidad()));
        colDisponibilidad.setPrefWidth(100);
        colDisponibilidad.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Disponibilidad item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        tablaRepartidores.getColumns().addAll(colId, colNombre, colTelefono, colZona, colDisponibilidad);

        ObservableList<Repartidor> listaRepartidores = FXCollections.observableArrayList(admin.getListRepartidores());
        tablaRepartidores.setItems(listaRepartidores);

        btnAgregar.setOnAction(e -> {
            agregarRepartidor();
            listaRepartidores.setAll(admin.getListRepartidores());
        });

        btnEditar.setOnAction(e -> {
            Repartidor seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                editarRepartidor(seleccionado);
                listaRepartidores.setAll(admin.getListRepartidores());
            } else {
                mostrarAlerta("Advertencia", "Seleccione un repartidor para editar", Alert.AlertType.WARNING);
            }
        });

        btnEliminar.setOnAction(e -> {
            Repartidor seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacion.setTitle("Confirmar eliminación");
                confirmacion.setHeaderText("¿Está seguro de eliminar este repartidor?");
                confirmacion.setContentText(seleccionado.getNombre());

                Optional<ButtonType> resultado = confirmacion.showAndWait();
                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    if (admin.eliminarRepartidor(seleccionado.getId())) {
                        listaRepartidores.setAll(admin.getListRepartidores());
                        mostrarAlerta("Éxito", "Repartidor eliminado correctamente", Alert.AlertType.INFORMATION);
                    }
                }
            } else {
                mostrarAlerta("Advertencia", "Seleccione un repartidor para eliminar", Alert.AlertType.WARNING);
            }
        });

        contentArea.getChildren().addAll(titulo, btnAgregar, btnEditar, btnEliminar, tablaRepartidores);
        contentArea.setSpacing(10);
    }

    private void agregarRepartidor() {
        Dialog<Repartidor> dialog = new Dialog<>();
        dialog.setTitle("Agregar Repartidor");
        dialog.setHeaderText("Ingrese los datos del nuevo repartidor");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtId = new TextField();
        txtId.setPromptText("ID");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre completo");
        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Contraseña");

        ComboBox<ZonaCobertura> cbZona = new ComboBox<>();
        cbZona.getItems().addAll(ZonaCobertura.ARMENIA, ZonaCobertura.MONTENEGRO,
                ZonaCobertura.CIRCASIA, ZonaCobertura.CALARCA,
                ZonaCobertura.LA_TEBAIDA);
        cbZona.setValue(ZonaCobertura.ARMENIA);

        ComboBox<Disponibilidad> cbDisponibilidad = new ComboBox<>();
        cbDisponibilidad.getItems().addAll(Disponibilidad.values());
        cbDisponibilidad.setValue(Disponibilidad.ACTIVO);

        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(txtContrasena, 1, 3);
        grid.add(new Label("Zona:"), 0, 4);
        grid.add(cbZona, 1, 4);
        grid.add(new Label("Disponibilidad:"), 0, 5);
        grid.add(cbDisponibilidad, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    int id = Integer.parseInt(txtId.getText().trim());

                    if (admin.buscarRepartidor(id) != null) {
                        mostrarAlerta("Error", "El ID ya existe", Alert.AlertType.ERROR);
                        return null;
                    }

                    Repartidor nuevoRepartidor = Repartidor.crearRepartidor(
                            id,
                            txtNombre.getText().trim(),
                            txtTelefono.getText().trim(),
                            txtContrasena.getText().trim(),
                            cbZona.getValue()
                    );
                    nuevoRepartidor.setDisponibilidad(cbDisponibilidad.getValue());

                    admin.agregarRepartidor(nuevoRepartidor);
                    mostrarAlerta("Éxito", "Repartidor agregado correctamente", Alert.AlertType.INFORMATION);
                    return nuevoRepartidor;
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El ID debe ser un número válido", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void editarRepartidor(Repartidor repartidor) {
        Dialog<Repartidor> dialog = new Dialog<>();
        dialog.setTitle("Editar Repartidor");
        dialog.setHeaderText("Modifique los datos del repartidor");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtNombre = new TextField(repartidor.getNombre());
        TextField txtTelefono = new TextField(repartidor.getTelefono());
        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setText(repartidor.getContrasena());

        ComboBox<ZonaCobertura> cbZona = new ComboBox<>();
        cbZona.getItems().addAll(ZonaCobertura.ARMENIA, ZonaCobertura.MONTENEGRO,
                ZonaCobertura.CIRCASIA, ZonaCobertura.CALARCA,
                ZonaCobertura.LA_TEBAIDA);
        cbZona.setValue(repartidor.getZona());

        ComboBox<Disponibilidad> cbDisponibilidad = new ComboBox<>();
        cbDisponibilidad.getItems().addAll(Disponibilidad.values());
        cbDisponibilidad.setValue(repartidor.getDisponibilidad());

        grid.add(new Label("ID: " + repartidor.getId()), 0, 0, 2, 1);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(txtContrasena, 1, 3);
        grid.add(new Label("Zona:"), 0, 4);
        grid.add(cbZona, 1, 4);
        grid.add(new Label("Disponibilidad:"), 0, 5);
        grid.add(cbDisponibilidad, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                repartidor.setNombre(txtNombre.getText().trim());
                repartidor.setTelefono(txtTelefono.getText().trim());
                repartidor.setContrasena(txtContrasena.getText().trim());
                repartidor.setZona(cbZona.getValue());
                repartidor.setDisponibilidad(cbDisponibilidad.getValue());

                admin.actualizarRepartidor(repartidor);
                mostrarAlerta("Éxito", "Repartidor actualizado correctamente", Alert.AlertType.INFORMATION);
                return repartidor;
            }
            return null;
        });

        dialog.showAndWait();
    }


    private void mostrarGestionEnvios() {
        contentArea.getChildren().clear();

        Label titulo = new Label("Gestión de Envíos");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #212529;");

        Button btnAgregarEnvio = new Button("Agregar Envío");
        btnAgregarEnvio.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-cursor: hand;");

        TableView<Envio> tablaEnvios = new TableView<>();
        tablaEnvios.setPrefHeight(300);
        tablaEnvios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Envio, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colId.setPrefWidth(50);

        TableColumn<Envio, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDestino()));
        colDestino.setPrefWidth(150);

        TableColumn<Envio, ZonaCobertura> colDepartamento = new TableColumn<>("Zona");
        colDepartamento.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDepartamento()));
        colDepartamento.setPrefWidth(100);
        colDepartamento.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(ZonaCobertura item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        TableColumn<Envio, Double> colPeso = new TableColumn<>("Peso (kg)");
        colPeso.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getPeso()).asObject());
        colPeso.setPrefWidth(80);

        TableColumn<Envio, EstadoEnvio> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getEstadoEnvio()));
        colEstado.setPrefWidth(100);
        colEstado.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(EstadoEnvio item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        tablaEnvios.getColumns().addAll(colId, colDestino, colDepartamento, colPeso, colEstado);

        ObservableList<Envio> listaEnvios = FXCollections.observableArrayList(admin.getListEnvios());
        tablaEnvios.setItems(listaEnvios);

        btnAgregarEnvio.setOnAction(e -> {
            agregarEnvio();
            listaEnvios.setAll(admin.getListEnvios());
        });

        contentArea.getChildren().addAll(titulo, btnAgregarEnvio, tablaEnvios);
        contentArea.setSpacing(10);
    }


    private void agregarEnvio() {
        Dialog<Envio> dialog = new Dialog<>();
        dialog.setTitle("Agregar Envío");
        dialog.setHeaderText("Ingrese los datos del nuevo envío");

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtDestino = new TextField();
        txtDestino.setPromptText("Dirección de destino");

        ComboBox<ZonaCobertura> cbDepartamento = new ComboBox<>();
        cbDepartamento.getItems().addAll(ZonaCobertura.ARMENIA, ZonaCobertura.MONTENEGRO,
                ZonaCobertura.CIRCASIA, ZonaCobertura.CALARCA,
                ZonaCobertura.LA_TEBAIDA);
        cbDepartamento.setValue(ZonaCobertura.ARMENIA);

        TextField txtDistancia = new TextField();
        txtDistancia.setPromptText("Distancia en km");

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso en kg");

        TextField txtVolumen = new TextField();
        txtVolumen.setPromptText("Volumen en m³");

        CheckBox chkPrioridad = new CheckBox("Es prioritario");

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

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    int nuevoId = admin.getListEnvios().size() + 1;

                    Envio nuevoEnvio = new Envio(
                            nuevoId,
                            txtDestino.getText().trim(),
                            cbDepartamento.getValue(),
                            Double.parseDouble(txtDistancia.getText().trim()),
                            Double.parseDouble(txtPeso.getText().trim()),
                            Double.parseDouble(txtVolumen.getText().trim()),
                            chkPrioridad.isSelected()
                    );

                    admin.agregarEnvio(nuevoEnvio);
                    mostrarAlerta("Éxito", "Envío agregado correctamente", Alert.AlertType.INFORMATION);
                    return nuevoEnvio;
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Distancia, peso y volumen deben ser números válidos", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}