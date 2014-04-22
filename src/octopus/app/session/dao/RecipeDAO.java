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
import java.util.ArrayList;
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

    @SuppressWarnings("unchecked")
    public List<Recipe> getByIngredients(List<String> ingredientsId) {
        Session session = manager.getSession();
        Query query = session.createQuery(
                "from Recipe as recipe where not exists " +
                        "(from Recipe as r join r.ingredients as i where r.id = recipe.id and i.id not in (:ingredientsId))");
        query.setParameterList("ingredientsId", ingredientsId);
        return (List<Recipe>) query.list();
    }

    public void persist(Recipe recipe) {
        Session session = manager.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(recipe);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw exception;
        }
    }

    public void remove(Recipe recipe) {
        Session session = manager.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(recipe);
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
        Query query = session.createQuery("select new Recipe(r) from Recipe as r order by r.published desc");
        return query.setMaxResults(6).list();
    }
}
