package octopus.app.session;

import org.hibernate.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/** @author Dmitry Kozlov */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Search {

    private FullTextSession session;

    @Inject
    public Search(Persistence persistence) {
        session = org.hibernate.search.Search.getFullTextSession(persistence.getSession());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> search(Class<T> entity, String request, String[] fields) {
        SearchFactory factory = session.getSearchFactory();
        QueryBuilder builder = factory.buildQueryBuilder().forEntity(entity).get();
        Query query = session.createFullTextQuery(
                builder.keyword()
                        .fuzzy()
                        .onFields(fields)
                        .matching(request)
                        .createQuery());

        return (List<T>) query.list();
    }
}
