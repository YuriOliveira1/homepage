package com.yuri.homepage;

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
}
