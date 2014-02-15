package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/** @author Dmitry Kozlov */
@Controller
public class Index {

    @RequestMapping("/")
    public @ResponseBody String index() {
        return "Ok";
    }
}
