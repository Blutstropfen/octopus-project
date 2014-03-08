package octopus.app.controllers;

import octopus.app.beans.Persistence;
import octopus.app.model.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/** @author Dmitry Kozlov */
@Controller
@RequestMapping("/recipe")
public class RecipeDataAccess {

    @Inject
    private Persistence persistence;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Recipe get(@PathVariable String id) {
        return persistence.getById(Recipe.class, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void set(@RequestBody Recipe recipe) {
        persistence.persist(recipe);
    }

}
