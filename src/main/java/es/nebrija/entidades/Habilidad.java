package es.nebrija.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "habilidad")
public class Habilidad {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "idHabilidad")
	private int idHabilidad;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;

	public Habilidad() {
	}

	public Habilidad(String nombre) {
		this.nombre = nombre;

	}

	public int getId() {
		return idHabilidad;
	}

	public void setId(int id) {
		this.idHabilidad = id;
	}

	public String getName() {
		return nombre;
	}

	public void setName(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String Descripcion) {
		this.descripcion = Descripcion;
	}
}