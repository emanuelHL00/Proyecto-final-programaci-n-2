package co.edu.uniquindio.poo.proyecto_final_p2.model;


public class EstrategiaNormal implements IEstrategiaEnvio {

    @Override
    public double calcularCosto(Envio envio) {
        // Usar el sistema de decoradores existente sin modificaciones
        Tarifa tarifa = new TarifaBase();
        tarifa = new TarifaDistancia(tarifa, envio);
        tarifa = new TarifaPeso(tarifa, envio);
        tarifa = new TarifaVolumen(tarifa, envio);
        tarifa = new TarifaPrioridad(tarifa, envio);

        return tarifa.calcularPrecio();
    }

    @Override
    public int calcularTiempoEstimado(Envio envio) {
        // Tiempo estándar
        int tiempoBase = 24; // 24 horas base

        // Agregar tiempo según distancia
        if (envio.getDistancia() > 50) {
            tiempoBase += 24; // Un día adicional
        }

        return tiempoBase;
    }

    @Override
    public String obtenerDescripcion() {
        return "Envío Normal: Entrega estándar en tiempo regular";
    }
}