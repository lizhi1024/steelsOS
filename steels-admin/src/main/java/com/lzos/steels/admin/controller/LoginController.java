package com.lzos.steels.admin.controller;

import com.lzos.steels.admin.common.CommonEnum;
import com.lzos.steels.admin.common.ResultBody;
import com.lzos.steels.admin.entity.dto.User;
import com.lzos.steels.admin.service.LoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
    public ResultBody doLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String s = cookies[0].getName() + cookies[0].getValue();
            System.out.println(s);
        }
        return ResultBody.success(CommonEnum.SUCCESS.getResultCode(), "登录成功", null);
    }

}
