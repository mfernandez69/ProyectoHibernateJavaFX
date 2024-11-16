package es.nebrija.entidades;

public class InicioSesion {
    private static InicioSesion instancia;
    private Entrenador entrenadorActual;

    private InicioSesion() {}

    public static InicioSesion getInstancia() {
        if (instancia == null) {
            instancia = new InicioSesion();
        }
        return instancia;
    }

    public void setEntrenadorActual(Entrenador entrenador) {
        this.entrenadorActual = entrenador;
    }

    public Entrenador getEntrenadorActual() {
        return entrenadorActual;
    }
}