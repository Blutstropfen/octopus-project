package octopus.app.beans;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/** @author Dmitry Kozlov */
@Component
public class DbSearchEngine {

    @Inject
    private DbSessionFactory factory;

    @PostConstruct
    private void postConstruct() {
        SessionFactory sessions = factory.getSessionFactory();
        FullTextSession session = Search.getFullTextSession(sessions.openSession());
        MassIndexer indexer = session.createIndexer();
        try {
            indexer.startAndWait();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        } finally {
            session.close();
        }
    }
}
