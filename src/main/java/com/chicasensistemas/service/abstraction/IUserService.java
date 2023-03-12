package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.IdUserRequest;
import com.chicasensistemas.model.request.UpdateUserRequest;
import com.chicasensistemas.model.response.ListUserResponse;
import com.chicasensistemas.model.response.UserResponse;

public interface IUserService {
    UserResponse getByIdResponse(String email) throws EntityNotFoundException;

    UserResponse getById (String userId) throws EntityNotFoundException;

    ListUserResponse getAll();

    UserEntity getByIdAndEnabled(String id) throws EntityNotFoundException;

    UserResponse update(UpdateUserRequest userRequest) throws Exception;

    void enableUser(IdUserRequest idUserRequest) throws EntityNotFoundException;

    void disableUser(IdUserRequest idUserRequest) throws EntityNotFoundException;

}
