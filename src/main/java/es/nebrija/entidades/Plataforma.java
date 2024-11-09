package es.nebrija.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plataforma")
public class Plataforma {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "idPlataforma")
    private int idPlataforma;
    
    @Column(name = "nombre")
	private String nombre;
	
	public Plataforma(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Plataforma() {
		super();
	}


	public int getIdPlataforma() {
		return idPlataforma;
	}

	public void setIdPlataforma(int idPlataforma) {
		this.idPlataforma = idPlataforma;
	}

	public String getNombrePlataforma() {
		return nombre;
	}

	public void setNombrePlataforma(String nombre) {
		this.nombre = nombre;
	}
}
