package co.edu.uniquindio.poo.proyecto_final_p2.model;

public interface IEstrategiaEnvio {
    double calcularCosto(Envio envio);
    int calcularTiempoEstimado(Envio envio); // en horas
    String obtenerDescripcion();
}