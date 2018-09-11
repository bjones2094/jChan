package com.bsj.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("session")
@RequestMapping("/")
public class HomeController {
    @Value("${home.image}")
    private String homeImageName;

    @GetMapping
    public String root(Model model) {
        model.addAttribute("mainImage", homeImageName);
        return "home";
    }
}
