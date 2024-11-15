package es.nebrija.entidades;

public class InicioSesion {
	private static InicioSesion instancia;
    private Integer entrenadorId;

    private InicioSesion() {}

    public static InicioSesion getInstancia() {
        if (instancia == null) {
            instancia = new InicioSesion();
        }
        return instancia;
    }

    public void setEntrenadorId(Integer id) {
        this.entrenadorId = id;
    }

    public Integer getEntrenadorId() {
        return entrenadorId;
    }
}
