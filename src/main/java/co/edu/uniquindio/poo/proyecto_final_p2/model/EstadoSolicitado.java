package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class EstadoSolicitado implements IEstadoEnvio {

    @Override
    public void avanzar(Envio envio) {
        envio.setEstadoEnvio(EstadoEnvio.ASIGANDO);
        envio.setEstadoActual(new EstadoAsignado());
        System.out.println("Envío asignado a un repartidor");
    }

    @Override
    public void cancelar(Envio envio) {
        envio.setEstadoEnvio(EstadoEnvio.INCIDENCIA);
        envio.setEstadoActual(new EstadoIncidencia());
        System.out.println("Envío cancelado");
    }

    @Override
    public String obtenerDescripcion() {
        return "El envío ha sido solicitado y está esperando asignación";
    }

    @Override
    public boolean puedeModificar() {
        return true;
    }
}