package com.saikiran.skillbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.saikiran.skillbridge.entity.Skill;
import com.saikiran.skillbridge.entity.User;
import com.saikiran.skillbridge.repository.SkillRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/skills")
    public String skillsPage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("skills", skillRepository.findByUser(user));

        return "skills";
    }

    @GetMapping("/add-skill")
    public String addSkillPage(Model model) {

        model.addAttribute("skill", new Skill());

        return "add-skill";
    }

    @PostMapping("/add-skill")
    public String saveSkill(@ModelAttribute Skill skill,
                            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        skill.setUser(user);

        skillRepository.save(skill);

        return "redirect:/skills";
    }
}