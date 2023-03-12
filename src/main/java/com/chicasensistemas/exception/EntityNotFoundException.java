package com.chicasensistemas.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String error) {
        super(error);
    }
}
