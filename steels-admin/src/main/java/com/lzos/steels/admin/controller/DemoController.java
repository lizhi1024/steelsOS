package com.lzos.steels.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cloudide/callback")
@Slf4j
public class DemoController {

    @GetMapping("/start")
    public String startCallBack(@RequestParam("instanceId") String instanceId) {
        System.out.println("1111111111111");
        return "11111111";
    }

}