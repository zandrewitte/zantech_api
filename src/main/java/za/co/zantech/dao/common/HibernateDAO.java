package za.co.zantech.dao.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by zandrewitte on 2017/05/10.
 * HibernateDAO
 */
class HibernateDAO {

    private static Logger logger = LoggerFactory.getLogger("HibernateDAO");
    private SessionFactory sessionFactory;
    private static HibernateDAO ourInstance = new HibernateDAO();

    public static HibernateDAO getInstance() {
        return ourInstance;
    }

    private static Logger getLogger() { return logger; }

    private HibernateDAO() {
        setupRegistry();
    }

    private void setupRegistry() {
        try {
            // A SessionFactory is set up once for an application!
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            getLogger().info("Retrying connection in 5 seconds");

            try{
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ie) {
                getLogger().error(e.getMessage(), ie);
            }
            setupRegistry();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
        return getInstance().getSessionFactory().openSession();
    }
}
