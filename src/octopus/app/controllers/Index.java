package octopus.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.View;

/** @author Dmitry Kozlov */
@Controller
public class Index {

    @RequestMapping(value = "/")
    public String index() {
        return "forward:app.html";
    }
}
