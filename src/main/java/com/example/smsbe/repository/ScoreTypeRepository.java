package com.example.smsbe.repository;

import com.example.smsbe.entity.ScoreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreTypeRepository extends JpaRepository<ScoreType, Integer> {
}
