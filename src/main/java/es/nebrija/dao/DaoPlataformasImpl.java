package es.nebrija.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import es.nebrija.entidades.Plataforma;

public class DaoPlataformasImpl implements Dao<Plataforma> {

	Session sesion;
	Transaction transaction = null;

	public DaoPlataformasImpl() {

	}

	@Override
	public Plataforma grabar(Plataforma t) {
		Plataforma plataforma = null;
		Integer idPlataforma;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			sesion.save(t);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al salvar una plataforma");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return plataforma;
	}

	@Override
	public Plataforma leer(Integer id) {
		Plataforma plataforma = null;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			plataforma = sesion.get(Plataforma.class, id);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al leer una plataforma");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return plataforma;
	}

	@Override
	public Plataforma borrar(Plataforma t) {
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			sesion.delete(t);
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("Error al borrar una plataforma");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return t;
	}

	@Override
	public Plataforma modificar(Plataforma t) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Plataforma plataforma=null;
        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();  
            // Verificar si se encontró la plataforma
            if (t != null) {
                // Actualizar el objeto en la base de datos
                session.update(t);
                // Confirmar la transacción
                transaction.commit();                
             // Obtener el objeto Plataforma por su ID
                plataforma = session.get(Plataforma.class, t.getIdPlataforma());
                System.out.println("Plataforma actualizada: " + plataforma.getNombrePlataforma());
            } else {
                System.out.println("Plataforma no encontrada con ID: " + t.getIdPlataforma());
            }
        } catch (HibernateException e) {
			System.out.println("Error al leer una plataforma");
        }
		return plataforma;
	}

	@Override
	public void grabarLista(List<Plataforma> l) {
		for (Plataforma p : l) {
			this.grabar(p);
		}
	}

	@Override
	public List<Plataforma> leerLista() {
		sesion = HibernateUtil.getSessionFactory().openSession();
		List<Plataforma> listaPlataformas=null;
		try {
			listaPlataformas = sesion.createQuery("from Plataforma", Plataforma.class).list();
		} catch (HibernateException e) {
			System.out.println("Error al leer una plataforma");
			if (sesion.getTransaction() != null) {
				sesion.getTransaction().rollback();
			}
		} finally {
			sesion.close();
		}
		return listaPlataformas;
	}

	@Override
	public Plataforma leer(String campo, String valor) {
		Plataforma plataforma = null;
		sesion = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = sesion.beginTransaction();
			// Iniciar una transacción (opcional, pero recomendado)
			List<Plataforma> plataformas = sesion
					.createQuery("FROM Plataforma WHERE nombre = :nombre", Plataforma.class).setParameter(campo, valor)
					.getResultList();
			// Confirmar la transacción
			transaction.commit();

			if (!plataformas.isEmpty()) {
				plataforma = plataformas.get(0); // Recuperar el primer resultado (o único)
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
		return plataforma;
	}
}
