package com.chicasensistemas.mapper;

import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.UserRequest;
import com.chicasensistemas.model.response.UserEmailResponse;
import com.chicasensistemas.model.response.UserResponse;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserResponse map(UserEntity user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setPhoto(user.getPhoto());
        userResponse.setDescription(user.getDescription());
        userResponse.setResetToken(user.getResetToken());
        userResponse.setRole(user.getRoles().get(0).getName());
        return userResponse;
    }

    public UserEntity map(UserRequest request, String passwordEncrypted) {
        UserEntity userRegister = new UserEntity();
        userRegister.setFirstName(request.getFirstName());
        userRegister.setLastName(request.getLastName());
        userRegister.setEmail(request.getEmail());
        userRegister.setPassword(passwordEncrypted);
        return userRegister;
    }

    public List<UserResponse> map(List<UserEntity> users) {
        List<UserResponse> responses = new ArrayList<>(users.size());
        for (UserEntity user : users) {
            responses.add(map(user));
        }
        return responses;
    }

    public UserEmailResponse map(String firstName, String lastName, String email) {
        UserEmailResponse userEmailResponse = new UserEmailResponse();
        userEmailResponse.setFirstName(firstName);
        userEmailResponse.setLastName(lastName);
        userEmailResponse.setEmail(email);
        return userEmailResponse;
    }
}
