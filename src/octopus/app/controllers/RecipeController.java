package octopus.app.controllers;

import octopus.app.model.Recipe;
import octopus.app.session.RecipeService;
import octopus.app.session.dao.RecipeDAO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/** @author Dmitry Kozlov */
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Inject
    private RecipeService service;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Recipe get(@RequestParam(required = true) String id) {
        return service.getBy(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void set(@RequestBody Recipe recipe) {
        service.save(recipe);
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List recent() {
        return service.getRecent();
    }
}
