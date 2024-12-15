package com.example.smsbe.repository;

import com.example.smsbe.entity.GlobalConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<GlobalConfig, Integer> {
    Optional<GlobalConfig> findByConfigKey(String configKey);
}
