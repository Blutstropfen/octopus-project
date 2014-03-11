package octopus.app.session.dao;

import octopus.app.model.Ingredient;
import octopus.app.model.Note;
import octopus.app.model.Recipe;
import octopus.app.session.Persistence;
import org.apache.avro.generic.GenericData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;

/** @author Dmitry Kozlov */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecipeDAO {

    @Inject
    private Persistence persistence;

    public Recipe getRecipe(String id) {
        Session session = persistence.getSession();
        Recipe entity = (Recipe) session.get(Recipe.class, id);
        return deepCopyOf(entity);
    }

    private Recipe deepCopyOf(Recipe entity) {
        Recipe recipe = new Recipe(entity.id, entity.name, entity.contents, entity.published);
        recipe.notes = new ArrayList<>();
        for (Note note : entity.notes) {
            recipe.notes.add(new Note(note.id, note.published, note.contents));
        }
        recipe.ingredients = new HashSet<>();
        for (Ingredient ingredient : entity.ingredients) {
            recipe.ingredients.add(new Ingredient(ingredient.id, ingredient.name, ingredient.description));
        }
        return recipe;
    }
}
