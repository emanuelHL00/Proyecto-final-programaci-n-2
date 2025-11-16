package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class Pago {

    private Usuario usuario;
    private Envio envio;
    private Repartidor repartidor;
    private MetodoPago metodoPago;
    private boolean pagoRealizado;

    public Pago(Usuario usuario, Envio envio, Repartidor repartidor) {
        this.usuario = usuario;
        this.envio = envio;
        this.repartidor = repartidor;
        this.pagoRealizado = false;
    }


    public void seleccionarMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        System.out.println("Método de pago seleccionado: " + metodoPago);
    }


    public void realizarPago() {
        if (metodoPago == null) {
            System.out.println("No se ha seleccionado un método de pago.");
            return;
        }

        if (pagoRealizado) {
            System.out.println("El pago ya fue realizado anteriormente.");
            return;
        }

        this.pagoRealizado = true;
        this.envio.setEstadoEnvio(EstadoEnvio.EN_RUTA);
        this.repartidor.setDisponibilidad(Disponibilidad.EN_RUTA);

        System.out.println("Pago realizado exitosamente.");
        System.out.println("Envío #" + envio.getId() + " ahora está EN CAMINO.");
        System.out.println("Repartidor " + repartidor.getNombre() + " está EN RUTA.");
    }

    public boolean isPagoRealizado() {
        return pagoRealizado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Envio getEnvio() {
        return envio;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "usuario=" + usuario.getNombre() +
                ", envio=" + envio.getId() +
                ", repartidor=" + repartidor.getNombre() +
                ", metodoPago=" + metodoPago +
                ", pagoRealizado=" + pagoRealizado +
                '}';
    }
}
