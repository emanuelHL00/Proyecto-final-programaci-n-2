package co.edu.uniquindio.poo.proyecto_final_p2.model;



public class Envio {

    private int id;
    private String origen = "Bodega Envíos Express";
    private String destino;
    private ZonaCobertura departamento;
    private EstadoEnvio estadoEnvio;
    private double distancia;
    private double peso;
    private double volumen;
    private boolean prioridad;

    // Patrón State
    private IEstadoEnvio estadoActual;

    public Envio(int id, String destino, ZonaCobertura departamento, double distancia, double peso, double volumen, boolean prioridad) {
        this.id = id;
        this.destino = destino;
        this.estadoEnvio = EstadoEnvio.SOLICITADO;
        this.departamento = departamento;
        this.distancia = distancia;
        this.peso = peso;
        this.volumen = volumen;
        this.prioridad = prioridad;

        // Inicializar con el estado Solicitado
        this.estadoActual = new EstadoSolicitado();
    }

    // Métodos del patrón State
    public void avanzarEstado() {
        estadoActual.avanzar(this);
    }

    public void cancelarEnvio() {
        estadoActual.cancelar(this);
    }

    public String obtenerDescripcionEstado() {
        return estadoActual.obtenerDescripcion();
    }

    public boolean puedeModificar() {
        return estadoActual.puedeModificar();
    }

    public void setEstadoActual(IEstadoEnvio estadoActual) {
        this.estadoActual = estadoActual;
    }

    public IEstadoEnvio getEstadoActual() {
        return estadoActual;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public EstadoEnvio getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(EstadoEnvio estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public ZonaCobertura getDepartamento() {
        return departamento;
    }

    public void setDepartamento(ZonaCobertura departamento) {
        this.departamento = departamento;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public boolean isPrioridad() {
        return prioridad;
    }

    public void setPrioridad(boolean prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Envío #" + id + " | Destino: " + destino +
                " | Departamento: " + departamento +
                " | Peso: " + peso + " kg | Distancia: " + distancia +
                " km | Volumen: " + volumen + " m³ | Prioridad: " + (prioridad ? "Sí" : "No") +
                " | Estado: " + estadoEnvio;
    }
}

