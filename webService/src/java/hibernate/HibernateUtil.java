package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure("hibernate/hibernate.cfg.xml");
            StandardServiceRegistryBuilder serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry.build());
        }
        return sessionFactory;
    }
}
