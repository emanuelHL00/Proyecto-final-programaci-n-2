package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class TarifaPrioridad extends TarifaDecorator {

    private Envio envio;

    public TarifaPrioridad(Tarifa tarifa, Envio envio) {
        super(tarifa);
        this.envio = envio;
    }

    @Override
    public double calcularPrecio() {
        double precio = super.calcularPrecio();
        if (envio.isPrioridad()) {
            precio += 10000;
        }
        return precio;
    }
}
