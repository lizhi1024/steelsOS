package com.lzos.steels.admin.controller;

import com.lzos.steels.admin.entity.dto.User;
import com.lzos.steels.admin.service.LoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login.html")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/doLogin.json")
    @ResponseBody
    public User doLogin(@Param("id") int id) {
        return loginService.doLogin(id);
    }

}
