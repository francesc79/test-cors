package it.zazos.cors.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zanutto on 2/24/15.
 */
@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/index"})
    public String index () { return "index"; }
}
