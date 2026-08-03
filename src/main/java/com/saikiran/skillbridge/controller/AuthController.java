package com.saikiran.skillbridge.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saikiran.skillbridge.entity.User;
import com.saikiran.skillbridge.repository.UserRepository;
import com.saikiran.skillbridge.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
    return "dashboard";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {

        userService.registerUser(user);

        return "redirect:/login";
    }

@PostMapping("/login")
public String loginUser(
        @RequestParam String email,
        @RequestParam String password,
        HttpSession session) {


    Optional<User> user = userRepository.findByEmail(email);


    if(user.isPresent() && user.get().getPassword().equals(password)) {

        session.setAttribute("loggedUser", user.get());

        return "redirect:/dashboard";

    }


    return "redirect:/login";
  } 

}