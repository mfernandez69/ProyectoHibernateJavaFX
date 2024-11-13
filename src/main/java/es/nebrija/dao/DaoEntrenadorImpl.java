package es.nebrija.dao;

import java.util.Iterator;
import java.util.List;

import es.nebrija.entidades.Entrenador;
import es.nebrija.entidades.Pokemon;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DaoEntrenadorImpl implements Dao<Entrenador>{
	Session sesion;
	Transaction transaction = null;
	
	public DaoEntrenadorImpl() {
		
	}
	@SuppressWarnings("deprecation")
	@Override
	public Entrenador grabar(Entrenador t) {
		Entrenador entrenador = null;
		Integer idEntrenador;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			sesion.save(t);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al salvar un entrenador");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return entrenador;
	}

	@Override
	public Entrenador leer(Integer id) {
		Entrenador entrenador=null;
		sesion = HibernateUtil.getSessionFactory().openSession();
		
		try {
			transaction = sesion.beginTransaction();
			entrenador = sesion.get(Entrenador.class, id);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al leer un entrenador");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return entrenador;
	}

	@Override
	public Entrenador leer(String nombreUsuario, String contraseña) {
		Entrenador entrenador = null;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			// Iniciar una transacción (opcional, pero recomendado)
			List<Entrenador> entrenadores = sesion
					//Hacemos un select confirmando el nombre de usuario y la contraseña
				    .createQuery("FROM entrenador WHERE nombre = :nombre AND contraseña = :contraseña", Entrenador.class)
				    .setParameter("nombre", nombreUsuario)
				    .setParameter("contraseña", contraseña)
				    .getResultList();
			// Confirmar la transacción
			transaction.commit();

			if (!entrenadores.isEmpty()) {
				entrenador = entrenadores.get(0); // Recuperar el primer resultado (o único)
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
		return entrenador;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Entrenador borrar(Entrenador t) {
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			sesion.delete(t);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al borrar un entrenador");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return t;
	}

	@SuppressWarnings({ "deprecation", "null" })
	@Override
	public Entrenador modificar(Entrenador t) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Entrenador entrenador=null;
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
                entrenador = session.get(Entrenador.class, t.getId());
                System.out.println("pokemon actualizado: " + entrenador.getName());
            } else {
                System.out.println("pokemon no encontrada con ID: " + t.getId());
            }
        } catch (HibernateException e) {
			System.out.println("Error al leer un pokemon");
        }
		return entrenador;
	}

	@Override
	public void grabarLista(List<Entrenador> l) {
		for (Entrenador p : l) {
			this.grabar(p);
		}
	}

	@Override
	public List<Entrenador> leerLista() {
		sesion = HibernateUtil.getSessionFactory().openSession();
		List<Entrenador> listaEntrenador=null;
		try {
			listaEntrenador = sesion.createQuery("from entrenador", Entrenador.class).list();
		} catch (HibernateException e) {
			System.out.println("Error al leer la lista de entrenadores");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return listaEntrenador;
	}
}
