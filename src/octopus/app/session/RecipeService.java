package octopus.app.session;

import octopus.app.common.CollectionUtil;
import octopus.app.common.Messages;
import octopus.app.common.ServiceResponse;
import octopus.app.model.Ingredient;
import octopus.app.model.Note;
import octopus.app.model.Recipe;
import octopus.app.session.dao.IngredientDAO;
import octopus.app.session.dao.RecipeDAO;
import octopus.app.session.dao.Search;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.PersistentObjectException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

/** @author Dmitry Kozlov */
@Component
public class RecipeService {

    @Inject
    private RecipeDAO dao;

    @Inject
    private IngredientDAO ingredients;

    @Inject
    private Search search;

    public Recipe getBy(String id) {
        Recipe recipe = dao.getBy(id);
        if (recipe != null) {
            return copyOf(recipe);
        }
        return null;
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

    public ServiceResponse save(Recipe recipe) {
        if (recipe.id == null) {
            return persist(recipe);
        }
        Recipe dbRecipe = dao.getBy(recipe.id);
        if (dbRecipe == null) {
            return persist(recipe);
        } else {
            return merge(dbRecipe, recipe);
        }
    }

    private ServiceResponse merge(Recipe dbRecipe, Recipe recipe) {
        recipe.published = dbRecipe.published; // Preserve publication date;
        Map<String, Ingredient> dbIngredients = CollectionUtil.toMap(dbRecipe.ingredients);
        for (Note note : recipe.notes) {
            note.owner = recipe;
        }
        recipe.ingredients = mergeIngredients(recipe, dbIngredients);
        try {
            dao.merge(recipe);
        } catch (ConstraintViolationException exception) {
            return ServiceResponse.exception(Messages.constraint_violation);
        } catch (StaleObjectStateException exception) {
            return ServiceResponse.exception(Messages.concurrent_modification);
        } catch (PersistentObjectException exception) {
            return ServiceResponse.exception(Messages.detached_entity);
        }
        return ServiceResponse.ok();
    }

    private Set<Ingredient> mergeIngredients(Recipe recipe, Map<String, Ingredient> dbIngredients) {
        Set<Ingredient> mergedIngredients = new HashSet<>(2 * recipe.ingredients.size());
        for (Ingredient ingredient : recipe.ingredients) {
            Ingredient dbIngredient = dbIngredients.get(ingredient.id);
            if (dbIngredient == null || ObjectUtils.notEqual(dbIngredient.name, ingredient.name)) {
                dbIngredient = ingredients.getByName(ingredient.name);
                if (dbIngredient != null) {
                    ingredient = dbIngredient;
                } else {
                    ingredient.id = null;
                    ingredient.description = null;
                }
            } else {
                ingredient = dbIngredient;
            }
            mergedIngredients.add(ingredient);
        }
        return mergedIngredients;
    }

    private ServiceResponse persist(Recipe recipe) {
        recipe.published = new Date();
        recipe.ingredients = mergeIngredients(recipe, Collections.<String, Ingredient>emptyMap());
        for (Note note : recipe.notes) {
            note.owner = recipe;
        }
        try {
            dao.persist(recipe);
        } catch (ConstraintViolationException exception) {
            return ServiceResponse.exception(Messages.constraint_violation);
        } catch (StaleObjectStateException exception) {
            return ServiceResponse.exception(Messages.concurrent_modification);
        } catch (PersistentObjectException exception) { // Detached entity passed to persist.
            return ServiceResponse.exception(Messages.detached_entity);
        }
        return ServiceResponse.ok();
    }

    public void remove(String id) {
        Recipe dbRecipe = dao.getBy(id);
        if (dbRecipe != null) {
            dao.remove(dbRecipe);
        }
    }

    public List<Recipe> search(String text) {
        return listShallowCopy(search.search(Recipe.class, text, new String[] {"name", "contents"}));
    }

    public List<Recipe> searchByIngredients(List<String> ingredientsIds) {
        return listShallowCopy(dao.getByIngredients(ingredientsIds));
    }

    private List<Recipe> listShallowCopy(List<Recipe> dbRecipes) {
        List<Recipe> result = new ArrayList<>(dbRecipes.size());
        for (Recipe dbRecipe : dbRecipes) {
            result.add(dbRecipe.shallowCopy());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Recipe> getRecent() {
        return (List<Recipe>) dao.getRecent();
    }
}
