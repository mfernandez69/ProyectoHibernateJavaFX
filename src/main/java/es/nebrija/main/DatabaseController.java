package es.nebrija.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import es.nebrija.dao.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

public class DatabaseController {
	private static SessionFactory sessionFactory;

    public static boolean initializeDatabase() {
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            testConnection();
            System.out.println("Conexión a la base de datos establecida con éxito.");
            return true;
        } catch (JDBCException e) {
            System.err.println("Error de conexión JDBC: " + e.getMessage());
            System.err.println("Asegúrate de que XAMPP y el servidor MySQL estén en ejecución.");
            return false;
        } catch (HibernateException e) {
            System.err.println("Error de Hibernate: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("deprecation")
	private static void testConnection() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.createNativeQuery("SELECT 1").getSingleResult();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
