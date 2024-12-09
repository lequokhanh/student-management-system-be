package com.example.smsbe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@Table(name = "class_detail", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "class_term_id"})
})
public class ClassDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_term_id", nullable = false)
    private ClassTerm classTerm;

    @Column(nullable = false)
    private Boolean isAvailable = true;
}
