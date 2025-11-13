package co.edu.uniquindio.poo.proyecto_final_p2.model;


public interface IEstadoEnvio {
    void avanzar(Envio envio);
    void cancelar(Envio envio);
    String obtenerDescripcion();
    boolean puedeModificar();
}
