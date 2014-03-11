package octopus.app.controllers;

import octopus.app.session.Persistence;
import octopus.app.session.Search;
import octopus.app.model.Recipe;
import octopus.app.session.dao.RecipeDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/** @author Dmitry Kozlov */
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    // todo replace persistence/search usages with dao

    @Inject
    private Persistence persistence;

    @Inject
    private Search search;

    @Inject
    private RecipeDAO recipeDAO;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Recipe get(@RequestParam(required = true) String id) {
        return recipeDAO.getRecipe(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void set(@RequestBody Recipe recipe) {
        Session session = persistence.getSession();
        Transaction transaction = session.beginTransaction();
        Recipe existing = (Recipe) session.get(Recipe.class, recipe.id);
        if (existing != null) {
            recipe.published = existing.published;
            session.merge(recipe);
        } else {
            recipe.published = new Date();
            session.saveOrUpdate(recipe);
        }
        transaction.commit();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Recipe> search(@RequestParam(required = true) String request) {
        return search.search(Recipe.class, request, new String[] {"name", "contents"});
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List recent() {
        Session session = persistence.getSession();
        return session.createQuery("select new Recipe(id, name, contents, published) from Recipe")
                .setMaxResults(40).list();
    }
}
