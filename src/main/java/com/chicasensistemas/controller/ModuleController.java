package com.chicasensistemas.controller;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.request.DeleteVideoByModuleRequest;
import com.chicasensistemas.model.request.ModuleRequest;
import com.chicasensistemas.model.request.UpdateModuleRequest;
import com.chicasensistemas.model.request.UpdateVideoIdRequest;
import com.chicasensistemas.model.response.ListModuleResponse;
import com.chicasensistemas.model.response.ModuleResponse;
import com.chicasensistemas.service.ModuleServiceImpl;
import com.chicasensistemas.service.abstraction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private IModuleService iModuleService;

    @Autowired
    ModuleServiceImpl moduleService;

    @PostMapping("/create")
    public ResponseEntity<ModuleResponse> create(@Valid @RequestBody ModuleRequest request) {
        ModuleResponse response = null;
        try {
            response = iModuleService.create(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<Void> enableModule(@PathVariable String id) {
        try {
            iModuleService.enableModule(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<Void> disableModule(@PathVariable String id) {
        try {
            iModuleService.disableModule(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<ModuleResponse> update(@Valid @RequestBody UpdateModuleRequest updateModuleRequest, @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iModuleService.update(updateModuleRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<ListModuleResponse> getModules() {
        return ResponseEntity.ok().body(iModuleService.getModules());
    }

    @PutMapping("/removeVideo")
    public ResponseEntity<Void> removeVideo(@RequestBody @Valid DeleteVideoByModuleRequest request) {
        iModuleService.removeVideoFromModule(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addVideo")
    public ResponseEntity<ModuleResponse> addVideo(@RequestBody @Valid UpdateVideoIdRequest request) {
        try {
            return ResponseEntity.ok().body(moduleService.addVideo(request));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ModuleResponse> findModule(@PathVariable String id) throws
            EntityNotFoundException {
        return ResponseEntity.ok().body(iModuleService.getByIdResponse(id));
    }
}
