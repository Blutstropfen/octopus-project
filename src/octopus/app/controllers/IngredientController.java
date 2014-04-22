package octopus.app.controllers;

import octopus.app.model.Ingredient;
import octopus.app.session.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

/** @author Dmitry Kozlov */
@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    @Inject
    private IngredientService service;

    @RequestMapping(value = "/name", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getByName(@RequestParam(required = true) String name) {
        return service.getIdByName(name);
    }
}
