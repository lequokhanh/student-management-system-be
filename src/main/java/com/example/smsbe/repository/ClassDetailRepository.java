package com.example.smsbe.repository;

import com.example.smsbe.entity.ClassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDetailRepository extends JpaRepository<ClassDetail, Integer> {
}
