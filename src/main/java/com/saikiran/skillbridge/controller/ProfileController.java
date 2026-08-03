package com.saikiran.skillbridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.saikiran.skillbridge.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {


    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {


        User user = (User) session.getAttribute("loggedUser");


        model.addAttribute("user", user);


        return "profile";
    }

}