package com.lzos.steels.admin.controller;

import com.lzos.steels.admin.utils.HttpClientUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/test")
public class DemoController {

    @RequestMapping("demo.json")
    public String demo(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

}
