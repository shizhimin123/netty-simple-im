package com.netty.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: PageController
 * @Author: shizhimin
 * @Date: 2020/3/18
 * @Version: 1.0
 */
@Controller
public class PageController {

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }
}
