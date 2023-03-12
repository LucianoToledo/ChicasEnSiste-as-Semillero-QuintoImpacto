package com.chicasensistemas.service;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.mapper.VideoMapper;
import com.chicasensistemas.model.entity.ModuleEntity;
import com.chicasensistemas.model.entity.VideoEntity;
import com.chicasensistemas.model.request.UpdateVideoRequest;
import com.chicasensistemas.model.request.VideoRequest;
import com.chicasensistemas.model.response.ListVideoResponse;
import com.chicasensistemas.model.response.VideoResponse;
import com.chicasensistemas.repository.IVideoRepository;
import com.chicasensistemas.service.abstraction.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements IVideoService {


    @Autowired
    private IVideoRepository videoRepository;

    @Autowired
    private VideoMapper videoMapper;

    @Lazy
    @Autowired
    private ModuleServiceImpl moduleService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public VideoResponse create(VideoRequest videoRequest) throws Exception {
        validateUrl(videoRequest.getUrl());
        VideoEntity video = videoMapper.map(videoRequest);
        VideoResponse videoResponse = videoMapper.map(videoRepository.save(video));
        if (!videoRequest.getIdModulo().isEmpty()) {
            moduleService.addVideo(videoMapper.map(videoResponse.getId(), videoRequest.getIdModulo()));
            video.setConnected(true);
            videoResponse = videoMapper.map(videoRepository.save(video));
            videoResponse.setIdModulo(videoRequest.getIdModulo());
            videoResponse.setModuleName(moduleService.getModuleName(videoRequest.getIdModulo()));
        }
        return videoResponse;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableVideo(String id) throws EntityNotFoundException {
        VideoEntity video = getById(id);
        if (!video.isEnabled()) {
            throw new EntityNotFoundException("VIDEO IS ALREADY DISABLE");
        }
        video.setSoftDelete(true);
        videoRepository.save(video);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enableVideo(String id) throws EntityNotFoundException {
        VideoEntity video = getById(id);
        if (video.isEnabled()) {
            throw new EntityNotFoundException("VIDEO IS ALREADY ENABLE");
        }
        video.setSoftDelete(false);
        videoRepository.save(video);
    }

    @Override
    @Transactional(readOnly = true)
    public VideoResponse getByIdResponse(String id) throws EntityNotFoundException {
        return videoMapper.map(getByIdAndSoftDeleteFalse(id));
    }

    @Override
    @Transactional
    public VideoEntity getByIdAndSoftDeleteFalse(String id) {
        Optional<VideoEntity> opt = Optional.ofNullable(videoRepository.findByIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("VIDEO NOT FOUND");
        }
        return opt.get();
    }

    @Transactional(readOnly = true)
    private VideoEntity getById(String id) {
        Optional<VideoEntity> optional = videoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("VIDEO NOT FOUND");
        }
        return optional.get();
    }

    @Override
    public ListVideoResponse list() throws EntityNotFoundException {
        return videoMapper.mapResponseList(videoMapper.map(videoRepository.findAllBySoftDeleteFalse()));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public VideoResponse update(UpdateVideoRequest videoRequest) {
        VideoEntity videoEntity = getById(videoRequest.getId());
        videoEntity.setName(videoRequest.getName());
        videoEntity.setUrl(videoRequest.getUrl());
        videoEntity.setDuration(videoRequest.getDuration());
        return videoMapper.map(videoRepository.save(videoEntity));
    }

    @Transactional(rollbackFor = {Exception.class})
    public VideoResponse updateConnected(VideoEntity entity) throws Exception {
        validateModuleId(entity.getId());
        entity.setConnected(true);
        return videoMapper.map(videoRepository.save(entity));
    }

    public void removeModuleVideo(VideoEntity videoEntity, ModuleEntity moduleEntity) { //le avisa al video que ya no pertenece al modulo
        videoRepository.deleteByModuleId(videoEntity.getId(), moduleEntity.getModuleId()); //la mejor query que vas a ver en tu vida
        videoEntity.setConnected(false);
        videoRepository.save(videoEntity);
    }

    private void validateUrl(String url) {
        VideoEntity video = videoRepository.findByUrlAndSoftDeleteFalse(url);
        if (video != null) {
            throw new EntityNotFoundException("URL already in use");
        }
    }

    private void validateModuleId(String id) throws Exception {
        VideoEntity video = getById(id);
        if (video.getConnected()) {
            throw new Exception("This video belongs to a module.");
        }
    }

    public List<VideoEntity> getVideoByModuleId(String idModulo) {
        return videoRepository.getVideosByModuleId(idModulo);
    }

    public String getModuleByVideoId(String videoId) {
        return videoRepository.getModuleByVideoId(videoId);
    }

}
