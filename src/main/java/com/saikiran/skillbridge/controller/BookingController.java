package com.saikiran.skillbridge.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


    // ==========================================
    // TEACHER - BOOKING REQUESTS
    // ==========================================

    @GetMapping("/booking-requests")
    public String bookingRequests(
            HttpSession session,
            Model model) {

        User teacher = (User) session.getAttribute("loggedUser");

        if (teacher == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "bookings",
                bookingRepository.findBySkill_User(teacher)
        );

        return "booking-requests";
    }


    // ==========================================
    // STUDENT - MY BOOKINGS
    // ==========================================

    @GetMapping("/bookings")
    public String myBookings(
            HttpSession session,
            Model model) {

        User student = (User) session.getAttribute("loggedUser");

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "bookings",
                bookingRepository.findByStudent(student)
        );

        return "bookings";
    }


    // ==========================================
    // STUDENT - BOOK A SKILL
    // ==========================================

    @PostMapping("/book-skill")
    public String bookSkill(
            @RequestParam Long skillId,
            HttpSession session) {

        User student = (User) session.getAttribute("loggedUser");

        if (student == null) {
            return "redirect:/login";
        }

        Skill skill = skillRepository
                .findById(skillId)
                .orElse(null);

        if (skill == null) {
            return "redirect:/browse-skills";
        }

        Booking booking = new Booking();

        booking.setStudent(student);
        booking.setSkill(skill);
        booking.setStatus("Pending");

        bookingRepository.save(booking);

        return "redirect:/browse-skills";
    }


    // ==========================================
    // TEACHER - ACCEPT BOOKING
    // ==========================================

    @PostMapping("/accept-booking")
    public String acceptBooking(
            @RequestParam Long bookingId,
            HttpSession session) {

        User teacher = (User) session.getAttribute("loggedUser");

        if (teacher == null) {
            return "redirect:/login";
        }

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElse(null);

        if (booking != null
                && booking.getSkill()
                          .getUser()
                          .getId()
                          .equals(teacher.getId())) {

            booking.setStatus("Accepted");

            bookingRepository.save(booking);
        }

        return "redirect:/booking-requests";
    }


    // ==========================================
    // TEACHER - REJECT BOOKING
    // ==========================================

    @PostMapping("/reject-booking")
    public String rejectBooking(
            @RequestParam Long bookingId,
            HttpSession session) {

        User teacher = (User) session.getAttribute("loggedUser");

        if (teacher == null) {
            return "redirect:/login";
        }

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElse(null);

        if (booking != null
                && booking.getSkill()
                          .getUser()
                          .getId()
                          .equals(teacher.getId())) {

            booking.setStatus("Rejected");

            bookingRepository.save(booking);
        }

        return "redirect:/booking-requests";
    }


    // ==========================================
    // TEACHER - SCHEDULE BOOKING
    // ==========================================

    @PostMapping("/schedule-booking")
    public String scheduleBooking(
            @RequestParam Long bookingId,
            @RequestParam LocalDate bookingDate,
            @RequestParam LocalTime bookingTime,
            HttpSession session) {

        User teacher = (User) session.getAttribute("loggedUser");

        if (teacher == null) {
            return "redirect:/login";
        }

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElse(null);

        if (booking == null) {
            return "redirect:/booking-requests";
        }

        // Make sure this teacher owns the skill
        if (!booking.getSkill()
                   .getUser()
                   .getId()
                   .equals(teacher.getId())) {

            return "redirect:/booking-requests";
        }

        // Save date
        booking.setBookingDate(bookingDate);

        // Save time
        booking.setBookingTime(bookingTime);

        // Keep booking accepted
        booking.setStatus("Scheduled");

        bookingRepository.save(booking);

        return "redirect:/booking-requests";
    }

}