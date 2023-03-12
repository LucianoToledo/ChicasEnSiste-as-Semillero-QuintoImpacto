package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.entity.ModuleEntity;
import com.chicasensistemas.model.entity.VideoEntity;
import com.chicasensistemas.model.request.UpdateVideoRequest;
import com.chicasensistemas.model.request.VideoRequest;
import com.chicasensistemas.model.response.ListVideoResponse;
import com.chicasensistemas.model.response.VideoResponse;

import java.util.List;

public interface IVideoService {

    VideoResponse create(VideoRequest videoRequest) throws Exception;

    void enableVideo(String id) throws EntityNotFoundException;

    void disableVideo(String id) throws EntityNotFoundException;

    VideoResponse update(UpdateVideoRequest videoRequest);

    ListVideoResponse list() throws EntityNotFoundException;

    VideoResponse getByIdResponse(String id) throws EntityNotFoundException;

    VideoResponse updateConnected (VideoEntity videoEntity) throws Exception;

    VideoEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    void removeModuleVideo(VideoEntity videoEntity, ModuleEntity moduleEntity);

    String getModuleByVideoId(String videoId);

    List<VideoEntity> getVideoByModuleId(String idModulo);

}
