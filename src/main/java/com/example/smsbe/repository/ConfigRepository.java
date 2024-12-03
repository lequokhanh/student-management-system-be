package com.example.smsbe.repository;

import com.example.smsbe.entity.GlobalConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<GlobalConfig, Integer> {
}
