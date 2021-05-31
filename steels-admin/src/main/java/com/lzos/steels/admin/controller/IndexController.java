package com.lzos.steels.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/index.html")
    public String index(Model model){
        model.addAttribute("name","hello pillar");
        return "index";
    }

}
