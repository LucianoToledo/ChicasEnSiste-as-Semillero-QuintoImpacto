package com.chicasensistemas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INSCRIPTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "inscription_id")
    private String inscriptionId;

    @Column(name = "inscription_date")
    private LocalDateTime inscritionDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "softDelete")
    private Boolean softDelete = false;

    public boolean isEnabled() { return !softDelete; }
}
