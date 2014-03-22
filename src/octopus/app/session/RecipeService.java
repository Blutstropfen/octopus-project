package octopus.app.session;

import octopus.app.common.CollectionCopyHelper;
import octopus.app.model.Recipe;
import octopus.app.session.dao.RecipeDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/** @author Dmitry Kozlov */
@Component
public class RecipeService {

    @Inject
    private RecipeDAO dao;

    public Recipe getBy(String id) {
        Recipe dbRecord = dao.getBy(id);

        Recipe recipe = new Recipe(dbRecord);
        recipe.ingredients = CollectionCopyHelper.shallowCopy(dbRecord.ingredients);
        recipe.notes = CollectionCopyHelper.shallowCopy(dbRecord.notes);
        return recipe;
    }

    public void save(Recipe recipe) {
        Recipe dbRecord = dao.getBy(recipe.id);

        dbRecord.name = recipe.name;
        dbRecord.contents = recipe.contents;
        dao.save(dbRecord);
    }

    @SuppressWarnings("unchecked")
    public List<Recipe> getRecent() {
        return (List<Recipe>) dao.getRecent();
    }
}
