package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class TarifaVolumen extends TarifaDecorator {

    private Envio envio;

    public TarifaVolumen(Tarifa tarifa, Envio envio) {
        super(tarifa);
        this.envio = envio;
    }

    @Override
    public double calcularPrecio() {
        double precio = super.calcularPrecio();
        if (envio.getVolumen() > 1) { // si ocupa más de 1 m³
            precio += 10000;
        }
        return precio;
    }
}

