package octopus.app.session.dao;

import octopus.app.model.Ingredient;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * @author Anastasiya Gorbunova
 */
@Component
public class IngredientDAO {

    @Inject
    private SessionManager manager;

    public Ingredient getBy(String id) {
        Session session = manager.getSession();
        return (Ingredient) session.get(Ingredient.class, id);
    }

    public Ingredient getByName(String name) {
        Session session = manager.getSession();
        Query query = session.createQuery("from Ingredient where name = :name").setParameter("name", name);
        return (Ingredient) query.uniqueResult();
    }
}
