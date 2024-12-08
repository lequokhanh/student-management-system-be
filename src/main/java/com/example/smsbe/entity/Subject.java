package com.example.smsbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class Subject extends BaseEntity {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String subject;

    @Column(nullable = false)
    private Double efficient;
}
