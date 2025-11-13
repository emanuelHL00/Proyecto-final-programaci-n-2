package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class EstadoEntregado implements IEstadoEnvio {

    @Override
    public void avanzar(Envio envio) {
        System.out.println("El envío ya ha sido entregado. No se puede avanzar más.");
    }

    @Override
    public void cancelar(Envio envio) {
        System.out.println("No se puede cancelar un envío ya entregado");
    }

    @Override
    public String obtenerDescripcion() {
        return "El envío ha sido entregado al destinatario";
    }

    @Override
    public boolean puedeModificar() {
        return false;
    }
}