package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class TarifaPeso extends TarifaDecorator {

    private Envio envio;

    public TarifaPeso(Tarifa tarifa, Envio envio) {
        super(tarifa);
        this.envio = envio;
    }

    @Override
    public double calcularPrecio() {
        double precio = super.calcularPrecio();
        if (envio.getPeso() > 5) {
            double exceso = envio.getPeso() - 5;
            precio += exceso * 10000;
        }
        return precio;
    }
}

