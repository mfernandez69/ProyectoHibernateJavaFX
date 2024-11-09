package es.nebrija.entidades;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pokemon")
public class Pokemon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "idPokemon")
	private int idPokemon;
	@Column(name = "nombre")
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "tipo_id")
	private Tipo tipo;

	@ManyToOne
	@JoinColumn(name = "habilidad_id")
	private Habilidad habilidad;

	@ManyToOne
	@JoinColumn(name = "entrenador_id")
	private Entrenador entrenador;

	public Pokemon() {
	}

	public Pokemon(String nombre) {
		this.nombre = nombre;

	}

	public int getId() {
		return idPokemon;
	}

	public void setId(int id) {
		this.idPokemon = id;
	}

	public String getName() {
		return nombre;
	}

	public void setName(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Habilidad getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Habilidad habilidad) {
		this.habilidad = habilidad;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}
}