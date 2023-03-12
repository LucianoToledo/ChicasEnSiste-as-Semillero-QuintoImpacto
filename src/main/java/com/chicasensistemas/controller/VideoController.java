package com.chicasensistemas.controller;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.request.UpdateVideoRequest;
import com.chicasensistemas.model.request.VideoRequest;
import com.chicasensistemas.model.response.ListVideoResponse;
import com.chicasensistemas.model.response.VideoResponse;
import com.chicasensistemas.service.abstraction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private IVideoService iVideoService;

    @PostMapping("/create")
    public ResponseEntity<VideoResponse> create(@Valid @RequestBody VideoRequest request){
        VideoResponse videoResponse;
        try {
            videoResponse = iVideoService.create(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(videoResponse);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enableVideo(@PathVariable String id) {
        try {
            iVideoService.enableVideo(id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disableVideo(@PathVariable String id) {
        try {
            iVideoService.disableVideo(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<VideoResponse> update(@RequestBody @Valid UpdateVideoRequest request, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iVideoService.update(request));
    }

    @GetMapping("/list")
    public  ResponseEntity<ListVideoResponse> list(){
        return ResponseEntity.ok().body(iVideoService.list());
    }

    @GetMapping("/find/{id}")
    public  ResponseEntity<VideoResponse> findVideo (@PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok(iVideoService.getByIdResponse(id));
    }
}
