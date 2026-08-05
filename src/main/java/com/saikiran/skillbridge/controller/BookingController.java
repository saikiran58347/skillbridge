package com.saikiran.skillbridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saikiran.skillbridge.entity.Booking;
import com.saikiran.skillbridge.entity.Skill;
import com.saikiran.skillbridge.entity.User;
import com.saikiran.skillbridge.repository.BookingRepository;
import com.saikiran.skillbridge.repository.SkillRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SkillRepository skillRepository;

    @PostMapping("/book-skill")
    public String bookSkill(@RequestParam Long skillId,
                            HttpSession session) {

        System.out.println("BOOK BUTTON CLICKED");

        User student = (User) session.getAttribute("loggedUser");

        if (student == null) {
            return "redirect:/login";
        }

        Skill skill = skillRepository.findById(skillId).orElse(null);

        if (skill == null) {
            return "redirect:/browse-skills";
        }

        System.out.println(student);
        System.out.println(skill);

        Booking booking = new Booking();

        booking.setStudent(student);
        booking.setSkill(skill);
        booking.setStatus("Pending");

        bookingRepository.save(booking);

        System.out.println("BOOKING SAVED");

        return "redirect:/browse-skills";
    }

}