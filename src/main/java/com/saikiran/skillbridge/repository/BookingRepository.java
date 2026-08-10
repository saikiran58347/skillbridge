package com.saikiran.skillbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saikiran.skillbridge.entity.Booking;
import com.saikiran.skillbridge.entity.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStudent(User student);

    List<Booking> findBySkill_User(User user);

}