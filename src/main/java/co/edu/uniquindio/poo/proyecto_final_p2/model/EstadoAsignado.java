package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class EstadoAsignado implements IEstadoEnvio {

    @Override
    public void avanzar(Envio envio) {
        envio.setEstadoEnvio(EstadoEnvio.EN_RUTA);
        envio.setEstadoActual(new EstadoEnRuta());
        System.out.println("Envío en ruta");
    }

    @Override
    public void cancelar(Envio envio) {
        envio.setEstadoEnvio(EstadoEnvio.INCIDENCIA);
        envio.setEstadoActual(new EstadoIncidencia());
        System.out.println("Envío cancelado después de asignación");
    }

    @Override
    public String obtenerDescripcion() {
        return "El envío ha sido asignado a un repartidor";
    }

    @Override
    public boolean puedeModificar() {
        return true;
    }
}