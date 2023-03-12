package com.chicasensistemas.mapper;

import com.chicasensistemas.model.entity.VideoEntity;
import com.chicasensistemas.model.request.UpdateVideoIdRequest;
import com.chicasensistemas.model.request.VideoRequest;
import com.chicasensistemas.model.response.ListVideoResponse;
import com.chicasensistemas.model.response.VideoResponse;
import com.chicasensistemas.service.ModuleServiceImpl;
import com.chicasensistemas.service.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VideoMapper {
    @Lazy
    @Autowired
    VideoServiceImpl videoService;

    @Lazy
    @Autowired
    ModuleServiceImpl moduleService;

    public VideoResponse map(VideoEntity videoEntity) {
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setId(videoEntity.getId());
        videoResponse.setName(videoEntity.getName());
        videoResponse.setUrl(videoEntity.getUrl());
        videoResponse.setSoftDelete(videoEntity.getSoftDelete());
        videoResponse.setDuration(videoEntity.getDuration());
        videoResponse.setConnected(videoEntity.getConnected());
        return videoResponse;
    }

    public VideoEntity map(VideoRequest videoRequest) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setName(videoRequest.getName());
        videoEntity.setUrl(videoRequest.getUrl());
        videoEntity.setDuration(videoRequest.getDuration());
        return videoEntity;
    }

    public List<VideoResponse> map(List<VideoEntity> list) {
        List<VideoResponse> videoResponses = new ArrayList<>(list.size());
        for (VideoEntity video : list) {
            VideoResponse response = map(video);
            String idModule = videoService.getModuleByVideoId(video.getId());
            response.setIdModulo(idModule != null ? idModule : null);
            response.setModuleName(idModule != null ? moduleService.getModuleName(idModule) : null);
            videoResponses.add(response);
        }
        return videoResponses;
    }

    public ListVideoResponse mapResponseList(List<VideoResponse> videoResponses) {
        ListVideoResponse list = new ListVideoResponse();
        list.setVideoResponses(videoResponses);
        return list;
    }

    public VideoEntity map(VideoResponse response) { //response to entity
        VideoEntity entity = new VideoEntity();
        entity.setId(response.getId());
        entity.setUrl(response.getUrl());
        entity.setName(response.getName());
        entity.setDuration(response.getDuration());
        entity.setConnected(response.getConnected());
        entity.setSoftDelete(response.getSoftDelete());
        return entity;
    }

    public UpdateVideoIdRequest map(String idVideo, String idModule) {
        UpdateVideoIdRequest request = new UpdateVideoIdRequest();
        request.setIdVideo(idVideo);
        request.setIdModulo(idModule);
        return request;
    }
}

