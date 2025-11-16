package co.edu.uniquindio.poo.proyecto_final_p2.model;


public class EstrategiaExpress implements IEstrategiaEnvio {

    @Override
    public double calcularCosto(Envio envio) {
        // Usar el sistema de decoradores
        Tarifa tarifa = new TarifaBase();
        tarifa = new TarifaDistancia(tarifa, envio);
        tarifa = new TarifaPeso(tarifa, envio);
        tarifa = new TarifaVolumen(tarifa, envio);
        tarifa = new TarifaPrioridad(tarifa, envio);

        return tarifa.calcularPrecio() * 1.5;
    }

    @Override
    public int calcularTiempoEstimado(Envio envio) {
        int tiempoBase = 2;

        if (envio.getDistancia() > 50) {
            tiempoBase += 2;
        }

        return tiempoBase;
    }

    @Override
    public String obtenerDescripcion() {
        return "Envío Express: Entrega en el menor tiempo posible";
    }
}