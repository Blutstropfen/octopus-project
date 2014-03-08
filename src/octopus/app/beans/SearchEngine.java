package octopus.app.beans;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.Search;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/** @author Dmitry Kozlov */
@Component
public class SearchEngine {

    @Inject
    private Persistence persistence;

    @PostConstruct
    private void postConstruct() {
        Session session = persistence.getSession();
        FullTextSession search = Search.getFullTextSession(session);
        MassIndexer indexer = search.createIndexer();
        try {
            indexer.startAndWait();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }
}
