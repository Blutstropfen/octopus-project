package octopus.app.session.dao;

import octopus.app.model.Recipe;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/** @author Dmitry Kozlov */
@Component
public class RecipeDAO {

    @Inject
    private SessionManager sessionManager;

    public Recipe getBy(String id) {
        Session session = sessionManager.getSession();
        return (Recipe) session.get(Recipe.class, id);
    }

    public void save(Recipe recipe) {
        Session session = sessionManager.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(recipe);
        transaction.commit();
    }

    public List getRecent() {
        Session session = sessionManager.getSession();
        Query query = session.createQuery("select new Recipe(r) from Recipe as r order by r.published");
        return query.setMaxResults(6).list();
    }
}
