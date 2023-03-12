package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVideoRepository extends JpaRepository<VideoEntity, String> {
    VideoEntity findByIdAndSoftDeleteFalse(String id);

    VideoEntity findByUrlAndSoftDeleteFalse(String url);


    @Query(value = "SELECT * FROM video WHERE module_id  LIKE :moduleId", nativeQuery = true)
        //TODO: agregar el softDelete
    List<VideoEntity> getVideosByModuleId(@Param("moduleId") String moduleId);

    @Modifying
    @Query(value = "UPDATE video SET module_id = null WHERE video_id LIKE :videoId AND module_id LIKE :moduleId", nativeQuery = true)
    void deleteByModuleId(@Param("videoId") String videoId, @Param("moduleId") String moduleId);

    @Query(value = "SELECT module_id FROM video WHERE video_id LIKE :videoId AND soft_delete = false", nativeQuery = true)
    String getModuleByVideoId(@Param("videoId") String videoId);

    List<VideoEntity> findAllBySoftDeleteFalse();
}
