package com.saikiran.skillbridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saikiran.skillbridge.entity.Skill;
import com.saikiran.skillbridge.entity.User;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByUser(User user);

}