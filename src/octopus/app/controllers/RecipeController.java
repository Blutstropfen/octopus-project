package octopus.app.controllers;

import octopus.app.common.ServiceResponse;
import octopus.app.model.Recipe;
import octopus.app.session.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/** @author Dmitry Kozlov */
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    @Inject
    private RecipeService service;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Recipe get(@RequestParam(required = true) String id) {
        if (id != null) {
            return service.getBy(id);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody ServiceResponse set(@RequestBody Recipe recipe) {
        if (recipe != null) {
            return service.save(recipe);
        }
        return ServiceResponse.ok();
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody ServiceResponse remove(@RequestParam(required = true) String id) {
        service.remove(id);
        return ServiceResponse.ok();
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List recent() {
        return service.getRecent();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List search(@RequestParam(required = true) String text) {
        return service.search(text);
    }

    @RequestMapping(value = "/ingredient-search", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public @ResponseBody List searchByIngredients(@RequestBody List<String> ingredientsId) {
        if (ingredientsId.isEmpty()) {
            return Collections.emptyList();
        }
        return service.searchByIngredients(ingredientsId);
    }
}
