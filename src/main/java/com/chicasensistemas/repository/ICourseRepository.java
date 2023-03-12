package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseEntity, String> {

    CourseEntity findByNameCourse(String nameCourse);

    CourseEntity findByCourseIdAndSoftDeleteFalse(String id);

    CourseEntity findByCourseId(String id);
}
