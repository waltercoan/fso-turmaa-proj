package br.univille.projfso2024a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
    // @ResponseBody
    // public String index(){
    //     return "MÃE, eu não acredito";
    // }
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("home/index");
    }
}
