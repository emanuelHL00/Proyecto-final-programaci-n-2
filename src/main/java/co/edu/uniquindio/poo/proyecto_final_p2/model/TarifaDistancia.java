package co.edu.uniquindio.poo.proyecto_final_p2.model;

public class TarifaDistancia extends TarifaDecorator {

    private Envio envio;

    public TarifaDistancia(Tarifa tarifa, Envio envio) {
        super(tarifa);
        this.envio = envio;
    }

    @Override
    public double calcularPrecio() {
        double precio = super.calcularPrecio();
        if (!envio.getDepartamento().equals(ZonaCobertura.ARMENIA)) {
            precio += 10000;
        }
        return precio;
    }
}
