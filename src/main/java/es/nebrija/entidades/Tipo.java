package es.nebrija.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "tipo")
public class Tipo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "idTipo")
	private int idTipo;
	
	@Column(name = "nombre")
	private String nombre;
	

	public Tipo() {
	}

	public Tipo(String nombre) {
		this.nombre = nombre;

	}

	public int getId() {
		return idTipo;
	}

	public void setId(int id) {
		this.idTipo = id;
	}

	public String getName() {
		return nombre;
	}

	public void setName(String nombre) {
		this.nombre = nombre;
	}
	  @Override
	    public String toString() {
	        return nombre;
	    }

}