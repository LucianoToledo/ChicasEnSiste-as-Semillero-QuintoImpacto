package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IModuleRepository extends JpaRepository<ModuleEntity, String> {

    ModuleEntity findByModuleIdAndSoftDeleteFalse(String id);

    @Query(value = "SELECT * FROM module WHERE course_id LIKE :courseId", nativeQuery = true)
    List<ModuleEntity> getModulesByCourseId(@Param("courseId") String courseId);

    @Query(value = "SELECT course_id FROM module WHERE module_id LIKE :moduleId AND soft_delete = false", nativeQuery = true)
    String getCourseByModuleId (@Param("moduleId")String moduleId);
    @Modifying
    @Query(value = "UPDATE module SET course_id = null WHERE module_id LIKE :moduleId AND course_id LIKE :courseId", nativeQuery = true)
    void deleteModuleByCourseId(@Param("moduleId") String moduleId, @Param("courseId") String courseId);
}
