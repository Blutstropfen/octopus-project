package octopus.app.session;

import octopus.app.common.CollectionUtil;
import octopus.app.model.Ingredient;
import octopus.app.model.Note;
import octopus.app.model.Recipe;
import octopus.app.session.dao.IngredientDAO;
import octopus.app.session.dao.RecipeDAO;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/** @author Dmitry Kozlov */
@Component
public class RecipeService {

    @Inject
    private RecipeDAO dao;

    @Inject
    private IngredientDAO ingredients;

    public Recipe getBy(String id) {
        return copyOf(dao.getBy(id));
    }

    public Recipe getByName(String name) {
        return copyOf(dao.getByName(name));
    }

    private Recipe copyOf(Recipe dbRecipe) {
        Recipe recipe = new Recipe(dbRecipe);
        recipe.ingredients = CollectionUtil.shallowCopy(dbRecipe.ingredients);
        recipe.notes = CollectionUtil.shallowCopy(dbRecipe.notes);
        return recipe;
    }

    public void save(Recipe recipe) {
        Recipe dbRecipe = dao.getBy(recipe.id);
        if (dbRecipe == null) {
            persist(recipe);
        } else {
            merge(dbRecipe, recipe);
        }
    }

    private void merge(Recipe dbRecipe, Recipe recipe) {
        recipe.published = dbRecipe.published; // Preserve publication date;
        Map<String, Ingredient> dbIngredients = CollectionUtil.toMap(dbRecipe.ingredients);
        for (Note note : recipe.notes) {
            note.owner = recipe;
        }
        for (Ingredient ingredient : recipe.ingredients) {
            Ingredient dbIngredient = dbIngredients.get(ingredient.id);
            if (!ObjectUtils.equals(dbIngredient.name, ingredient.name)) {
                dbIngredient = ingredients.getByName(ingredient.name);
                if (dbIngredient != null) {
                    ingredient.id = dbIngredient.id;
                    ingredient.description = dbIngredient.description;
                } else {
                    ingredient.id = null; // Save as new.
                    ingredient.description = null;
                }
            }
        }
        dao.merge(recipe);
    }

    private void persist(Recipe recipe) {
        dao.save(recipe);
    }

    @SuppressWarnings("unchecked")
    public List<Recipe> getRecent() {
        return (List<Recipe>) dao.getRecent();
    }
}
