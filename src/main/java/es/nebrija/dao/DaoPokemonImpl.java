package es.nebrija.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import es.nebrija.entidades.Habilidad;
import es.nebrija.entidades.Pokemon;
import es.nebrija.entidades.Tipo;

public class DaoPokemonImpl implements Dao<Pokemon> {

	Session sesion;
	Transaction transaction = null;

	public DaoPokemonImpl() {

		
	}
	@SuppressWarnings("deprecation")
	@Override
	public Pokemon grabar(Pokemon t) {
	    Pokemon pokemon = null;
	    Session sesion = null;
	    Transaction transaction = null;
	    try {
	        sesion = HibernateUtil.getSessionFactory().openSession();
	        transaction = sesion.beginTransaction();
	        
	        // Guardar el Pokémon
	        sesion.save(t);
	        
	        transaction.commit();
	        pokemon = t; // El Pokémon ya tiene su ID asignado después de save()
	    } catch (HibernateException e) {
	        System.out.println("Error al guardar un Pokémon: " + e.getMessage());
	        if (transaction != null) {
	            transaction.rollback();
	        }
	    } finally {
	        if (sesion != null) {
	            sesion.close();
	        }
	    }
	    return pokemon;
	}

	@Override
	public Pokemon leer(Integer id) {
		Pokemon pokemon = null;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			pokemon = sesion.get(Pokemon.class, id);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al leer un pokemon");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return pokemon;
	}

	@Override
	public Pokemon borrar(Pokemon t) {
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			sesion.delete(t);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al borrar un pokemon");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return t;
	}

	@Override
	public Pokemon modificar(Pokemon t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Pokemon pokemon=null;
        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();  
            // Verificar si se encontró el pokemon
            if (t != null) {
                // Actualizar el objeto en la base de datos
                session.update(t);
                // Confirmar la transacción
                transaction.commit();                
             // Obtener el objeto pokemon por su ID
                pokemon = session.get(Pokemon.class, t.getId());
                System.out.println("pokemon actualizado: " + pokemon.getName());
            } else {
                System.out.println("pokemon no encontrada con ID: " + t.getId());
            }
        } catch (HibernateException e) {
			System.out.println("Error al leer un pokemon");
        }
		return pokemon;
	}

	@Override
	public void grabarLista(List<Pokemon> l) {
		for (Pokemon p : l) {
			this.grabar(p);
		}
	}

	@Override
	public List<Pokemon> leerLista() {
	    Session sesion = HibernateUtil.getSessionFactory().openSession();
	    List<Pokemon> listaPokemon = null;
	    try {
	        Query<Pokemon> query = sesion.createQuery("FROM Pokemon p LEFT JOIN FETCH p.entrenador", Pokemon.class);
	        listaPokemon = query.getResultList();
	    } catch (HibernateException e) {
	        System.out.println("Error al leer los pokemon: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        sesion.close();
	    }
	    return listaPokemon;
	}

	@Override
	public Pokemon leer(String campo, String valor) {
		Pokemon pokemon = null;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			// Iniciar una transacción (opcional, pero recomendado)
			List<Pokemon> pokemons = sesion
					.createQuery("FROM Pokemon WHERE nombre = :nombre", Pokemon.class).setParameter(campo, valor)
					.getResultList();
			// Confirmar la transacción
			transaction.commit();

			if (!pokemons.isEmpty()) {
				pokemon = pokemons.get(0); // Recuperar el primer resultado (o único)
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			// Cerrar la sesión
			sesion.close();
		}
		return pokemon;
	}
	public List<Tipo> obtenerTipos() {
	    List<Tipo> tipos = new ArrayList<>();
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Query<Tipo> query = session.createQuery("FROM Tipo", Tipo.class);
	        tipos = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return tipos;
	}

	public List<Habilidad> obtenerHabilidades() {
	    List<Habilidad> habilidades = new ArrayList<>();
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Query<Habilidad> query = session.createQuery("FROM Habilidad", Habilidad.class);
	        habilidades = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return habilidades;
	}
}
