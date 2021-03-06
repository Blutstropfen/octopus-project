package octopus.app.session.dao;

import octopus.app.beans.DbSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

/** @author Dmitry Kozlov */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionManager {

    private Session session;

    @Inject
    public SessionManager(DbSessionFactory dbSessionFactory) {
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
