package beans;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/** @author Dmitry Kozlov */
@Component
public class Persistence {

    private SessionFactory factory;
    private Session session;

    @PostConstruct
    private void postConstruct() {
        Configuration configuration = new Configuration().configure("hibernate.xml");
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        factory = configuration.buildSessionFactory(registry);
        session = factory.openSession();
    }

    @PreDestroy
    private void preDestroy() {
        session.close();
        factory.close();
    }
}
