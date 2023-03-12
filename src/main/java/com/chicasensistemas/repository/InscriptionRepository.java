package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.InscriptionEntity;
import com.chicasensistemas.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscriptionRepository extends JpaRepository<InscriptionEntity, String> {

    @Query("SELECT i FROM InscriptionEntity i WHERE i.user LIKE :user")
    List<InscriptionEntity> findInscriptionByUserId(@Param("user") UserEntity user);

    @Query("SELECT i FROM InscriptionEntity i WHERE i.user LIKE :user AND i.course LIKE :course")
    InscriptionEntity findInscriptionEntityByUserEntityAndCourseEntity(@Param("user") UserEntity user, @Param("course") CourseEntity course);

    @Query("SELECT i FROM InscriptionEntity i WHERE i.course LIKE :course")
    List<InscriptionEntity> findInscriptionByNameCourse(@Param("course")CourseEntity course);

}
