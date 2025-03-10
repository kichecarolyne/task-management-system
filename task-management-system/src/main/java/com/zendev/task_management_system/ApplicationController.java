package com.zendev.task_management_system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/admin")
    public String admin(){
        return "/admin/index";
    }

    @GetMapping("/projects")
    public String projects(){
        return "/projects/index";
    }

    @GetMapping("/tasks")
    public String tasks(){
        return "/tasks/index";
    }

    @GetMapping("/my_tasks")
    public String my_tasks(){
        return "/my_tasks/index";
    }

    @GetMapping("/parameters")
    public String parameters(){
        return "/parameters/index";
    }
}
