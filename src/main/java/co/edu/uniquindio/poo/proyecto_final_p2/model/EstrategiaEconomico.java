package co.edu.uniquindio.poo.proyecto_final_p2.model;


public class EstrategiaEconomico implements IEstrategiaEnvio {

    @Override
    public double calcularCosto(Envio envio) {
        // Usar el sistema de decoradores sin prioridad
        Tarifa tarifa = new TarifaBase();
        tarifa = new TarifaDistancia(tarifa, envio);
        tarifa = new TarifaPeso(tarifa, envio);
        tarifa = new TarifaVolumen(tarifa, envio);
        // No aplicar TarifaPrioridad

        // Descuento del 20% por ser económico
        return tarifa.calcularPrecio() * 0.8;
    }

    @Override
    public int calcularTiempoEstimado(Envio envio) {
        // Tiempo más largo
        int tiempoBase = 48; // 48 horas base (2 días)

        // Agregar tiempo según distancia
        if (envio.getDistancia() > 50) {
            tiempoBase += 48; // Dos días adicionales
        }

        return tiempoBase;
    }

    @Override
    public String obtenerDescripcion() {
        return "Envío Económico: Entrega económica con tiempo de espera mayor";
    }
}