package octopus.app.beans;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/** @author Dmitry Kozlov */
@Component
public class DbSessionFactory {

    private SessionFactory factory;

    @PostConstruct
    private void postConstruct() {
        Configuration configuration = new Configuration().configure("hibernate.xml");
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        factory = configuration.buildSessionFactory(registry);
    }

    public SessionFactory getSessionFactory() {
        return factory;
    }

    @PreDestroy
    private void preDestroy() {
        factory.close();
    }
}
