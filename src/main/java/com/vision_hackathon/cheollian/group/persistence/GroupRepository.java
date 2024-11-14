package com.vision_hackathon.cheollian.group.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}
