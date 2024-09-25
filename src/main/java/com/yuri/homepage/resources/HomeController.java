package com.yuri.homepage.resources;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String main(Model model) {
        return "main"; 
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        return "projects";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        return "blog";
    }

    @GetMapping("/postform")
    public String postform(Model model) {
        return "postform";
    }
}
