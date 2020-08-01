package Utils;

import Model.AccountsEntity;
import Model.ConnectionsEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import io.github.cdimascio.dotenv.Dotenv;


import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        Properties settings = new Properties();

        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("USERNAME_DATABASE");
        String password = dotenv.get("PASSWORD");

        settings.put(Environment.USER, username != null ? username : "root");
        settings.put(Environment.PASS, password != null ? password : "13101998");

        configuration.addProperties(settings);
        configuration.addAnnotatedClass(AccountsEntity.class);
        configuration.addAnnotatedClass(ConnectionsEntity.class);


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }
}
