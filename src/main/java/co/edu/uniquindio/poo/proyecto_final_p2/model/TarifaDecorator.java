package co.edu.uniquindio.poo.proyecto_final_p2.model;

public abstract class TarifaDecorator implements Tarifa {
    protected Tarifa tarifa;

    public TarifaDecorator(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public double calcularPrecio() {
        return tarifa.calcularPrecio();
    }
}

