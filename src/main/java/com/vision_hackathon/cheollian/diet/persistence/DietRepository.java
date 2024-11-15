package com.vision_hackathon.cheollian.diet.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vision_hackathon.cheollian.diet.entity.Diet;

public interface DietRepository extends JpaRepository<Diet, UUID> {

    public List<Diet> findDietsByDate(String date);
}
