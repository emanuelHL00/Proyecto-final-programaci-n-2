package co.edu.uniquindio.poo.proyecto_final_p2.model;


public class EstrategiaExpress implements IEstrategiaEnvio {

    @Override
    public double calcularCosto(Envio envio) {
        // Usar el sistema de decoradores existente
        Tarifa tarifa = new TarifaBase();
        tarifa = new TarifaDistancia(tarifa, envio);
        tarifa = new TarifaPeso(tarifa, envio);
        tarifa = new TarifaVolumen(tarifa, envio);
        tarifa = new TarifaPrioridad(tarifa, envio);

        // Recargo adicional del 50% por ser Express
        return tarifa.calcularPrecio() * 1.5;
    }

    @Override
    public int calcularTiempoEstimado(Envio envio) {
        // Tiempo base reducido por ser express
        int tiempoBase = 2; // 2 horas base

        // Agregar tiempo según distancia
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