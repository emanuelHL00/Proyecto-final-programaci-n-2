package co.edu.uniquindio.poo.proyecto_final_p2.model;


public class EstrategiaEconomico implements IEstrategiaEnvio {

    @Override
    public double calcularCosto(Envio envio) {
        // Usar el sistema de decoradores
        Tarifa tarifa = new TarifaBase();
        tarifa = new TarifaDistancia(tarifa, envio);
        tarifa = new TarifaPeso(tarifa, envio);
        tarifa = new TarifaVolumen(tarifa, envio);

        return tarifa.calcularPrecio() * 0.8;
    }

    @Override
    public int calcularTiempoEstimado(Envio envio) {
        int tiempoBase = 48;

        if (envio.getDistancia() > 50) {
            tiempoBase += 48;
        }

        return tiempoBase;
    }

    @Override
    public String obtenerDescripcion() {
        return "Envío Económico: Entrega económica con tiempo de espera mayor";
    }
}