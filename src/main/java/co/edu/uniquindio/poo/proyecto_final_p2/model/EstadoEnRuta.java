package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class EstadoEnRuta implements IEstadoEnvio {

    @Override
    public void avanzar(Envio envio) {
        envio.setEstadoEnvio(EstadoEnvio.ENTREGADO);
        envio.setEstadoActual(new EstadoEntregado());
        System.out.println("Envío entregado exitosamente");
    }

    @Override
    public void cancelar(Envio envio) {
        envio.setEstadoEnvio(EstadoEnvio.INCIDENCIA);
        envio.setEstadoActual(new EstadoIncidencia());
        System.out.println("Incidencia reportada durante la entrega");
    }

    @Override
    public String obtenerDescripcion() {
        return "El envío está en camino hacia su destino";
    }

    @Override
    public boolean puedeModificar() {
        return false;
    }
}