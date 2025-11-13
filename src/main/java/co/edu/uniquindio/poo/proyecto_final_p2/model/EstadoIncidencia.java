package co.edu.uniquindio.poo.proyecto_final_p2.model;


public class EstadoIncidencia implements IEstadoEnvio {

    @Override
    public void avanzar(Envio envio) {
        // Se puede reintentar el envío
        envio.setEstadoEnvio(EstadoEnvio.SOLICITADO);
        envio.setEstadoActual(new EstadoSolicitado());
        System.out.println("Envío reintentado después de incidencia");
    }

    @Override
    public void cancelar(Envio envio) {
        System.out.println("El envío ya está en estado de incidencia");
    }

    @Override
    public String obtenerDescripcion() {
        return "El envío tiene una incidencia que debe ser resuelta";
    }

    @Override
    public boolean puedeModificar() {
        return true;
    }
}