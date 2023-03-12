package com.chicasensistemas.service;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.mapper.UserMapper;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.UserPasswordRecoveryRequest;
import com.chicasensistemas.model.request.UpdateUserPasswordRequest;
import com.chicasensistemas.model.response.UserPasswordRecoveryResponse;
import com.chicasensistemas.repository.IUserRepository;
import com.chicasensistemas.service.abstraction.IPasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordRecoveryServiceImpl implements IPasswordRecoveryService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    UserMapper userMapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;


    @Override
    public UserPasswordRecoveryResponse forgottenPassword(UserPasswordRecoveryRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException {
        UserEntity user = validateUserByEmail(request.getEmail());
        user.setResetToken(generateToken());
        user.setResetTokenCreationDate(LocalDateTime.now());
        userRepository.save(user);
        String link = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName();
        emailService.recoveryPasswordMessage(user.getEmail(), user.getResetToken(), link);
        return new UserPasswordRecoveryResponse(user.getUserId(), user.getEmail(), user.getResetToken());
    }

    @Override
    public UserPasswordRecoveryResponse resetPassword(UpdateUserPasswordRequest updateUserPasswordRequest) throws Exception {
        UserEntity userEntity = validatePasswordResetRequest(updateUserPasswordRequest.getResetToken());
        userEntity.setPassword(passwordEncoder.encode(updateUserPasswordRequest.getPassword()));
        userEntity.setResetToken(null);
        userEntity.setResetTokenCreationDate(null);
        userRepository.save(userEntity);
        return new UserPasswordRecoveryResponse(userEntity.getUserId(), userEntity.getEmail(), userEntity.getResetToken());
    }

    public UserEntity validatePasswordResetRequest(String resetToken) {

        Optional<UserEntity> opt = Optional.ofNullable(userRepository.findByResetTokenAndSoftDeleteFalse(resetToken));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Invalid token.");
        }
        LocalDateTime tokenCreationDate = opt.get().getResetTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            try {
                throw new Exception ("Token expired.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return opt.get();
    }

    private UserEntity validateUserByEmail(String email) throws EntityNotFoundException {
        Optional<UserEntity> opt = Optional.ofNullable(userRepository.findByEmailAndSoftDeleteFalse(email));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("User not found or disabled");
        }
        return opt.get();
    }

    private String generateToken() {
        StringBuilder resetToken = new StringBuilder();

        return resetToken.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    private boolean isTokenExpired(final LocalDateTime resetTokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(resetTokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

}



