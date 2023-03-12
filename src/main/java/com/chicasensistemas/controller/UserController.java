package com.chicasensistemas.controller;

import com.chicasensistemas.model.request.IdUserRequest;
import com.chicasensistemas.model.request.UpdateUserRequest;
import com.chicasensistemas.model.response.ListUserResponse;
import com.chicasensistemas.model.response.UserResponse;

import java.security.Principal;
import javax.validation.Valid;

import com.chicasensistemas.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
private IUserService iUserService;

    @PutMapping("/modify")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UpdateUserRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iUserService.update(request));
    }

    @PutMapping("/disableUser")
    public ResponseEntity<Void> disableStudent(@RequestBody @Valid IdUserRequest idUserRequest)
        throws Exception {
        iUserService.disableUser(idUserRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/enableUser")
    public ResponseEntity<Void> enableStudent(@RequestBody @Valid IdUserRequest idUserRequest)
        throws Exception {
        iUserService.enableUser(idUserRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    public ResponseEntity<ListUserResponse> list(){
        return ResponseEntity.ok().body(iUserService.getAll());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserDetails(Principal principal){
        return ResponseEntity.ok((iUserService.getByIdResponse(principal.getName())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserDetails(@PathVariable String id){
        return ResponseEntity.ok(iUserService.getById(id));
    }

}
