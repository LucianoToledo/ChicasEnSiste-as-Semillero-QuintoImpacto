package com.chicasensistemas.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "COURSE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "course_id")
    private String courseId;

    @CreationTimestamp
    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDateTime startDate = LocalDateTime.now(); //fechaAlta

    @Column(name = "name_course", nullable = false, unique = true)
    private String nameCourse;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    @Column(name ="duration")
    private String duration;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private List<ModuleEntity> modules;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_admin_id")
    private UserEntity userEntity;

    public boolean isEnabled() { return !softDelete; }
}
