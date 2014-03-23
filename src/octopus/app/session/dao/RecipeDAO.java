package octopus.app.session.dao;

import octopus.app.common.CollectionUtil;
import octopus.app.model.Ingredient;
import octopus.app.model.Note;
import octopus.app.model.Recipe;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/** @author Dmitry Kozlov */
@Component
public class RecipeDAO {

    @Inject
    private SessionManager manager;

    public Recipe getBy(String id) {
        Session session = manager.getSession();
        return (Recipe) session.get(Recipe.class, id);
    }

    public Recipe getByName(String name) {
        Session session = manager.getSession();
        Query query = session.createQuery("from Recipe where name = :name").setParameter("name", name);
        return (Recipe) query.uniqueResult();
    }

    public void save(Recipe recipe) {
        Session session = manager.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(recipe);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw exception;
        }
    }

    public void merge(Recipe recipe) {
        Session session = manager.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(recipe);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw exception;
        }
    }

    public List getRecent() {
        Session session = manager.getSession();
        Query query = session.createQuery("select new Recipe(r) from Recipe as r order by r.published");
        return query.setMaxResults(6).list();
    }
}
