package octopus.app.session;

import octopus.app.model.Ingredient;
import octopus.app.session.dao.IngredientDAO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/** @author Dmitry Kozlov */
@Component
public class IngredientService {

    @Inject
    private IngredientDAO dao;

    public String getIdByName(String name) {
        Ingredient ingredient = dao.getByName(name);
        if (ingredient == null) {
            return null;
        } else {
            return ingredient.id;
        }
    }
}
