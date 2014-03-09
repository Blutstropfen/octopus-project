package octopus.app.common;

import octopus.app.beans.DbSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

/** @author Dmitry Kozlov */
public class Persistence {

    private Session session;

    @Inject
    public Persistence(DbSessionFactory dbSessionFactory) {
        SessionFactory factory = dbSessionFactory.getSessionFactory();
        session = factory.openSession();
    }

    public Session getSession() {
        return session;
    }

    @PreDestroy
    private void preDestroy() {
        session.close();
    }
}
