package com.chicasensistemas.service;

import com.chicasensistemas.auth.RoleType;
import com.chicasensistemas.auth.config.filter.JwtUtils;
import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.exception.InvalidCredentialsException;
import com.chicasensistemas.mapper.UserMapper;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.AuthenticationRequest;
import com.chicasensistemas.model.request.IdUserRequest;
import com.chicasensistemas.model.request.UpdateUserRequest;
import com.chicasensistemas.model.response.AuthenticationResponse;
import com.chicasensistemas.model.response.ListUserResponse;
import com.chicasensistemas.model.response.UserResponse;
import com.chicasensistemas.repository.ILoginRepository;
import com.chicasensistemas.repository.IRoleRepository;
import com.chicasensistemas.repository.IUserRepository;
import com.chicasensistemas.service.abstraction.IGoogle;

import java.util.List;

import com.chicasensistemas.service.abstraction.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService, ILoginRepository, IGoogle {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    IRoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public UserResponse update(UpdateUserRequest request) throws Exception {
        UserEntity user = userRepository.findByUserIdAndSoftDeleteFalse(request.getUserId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        if (!user.getEmail().equals(request.getEmail())){
            if (emailExist(request.getEmail()) == false){
                user.setEmail(request.getEmail());
            } else {
                throw new Exception("Email already exist");
            }
            user.setEmail(request.getEmail());
        }
        if (!request.getPassword().isEmpty()) {
            validatePassword(request.getPassword());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setPhone(request.getPhone());
        user.setPhoto(request.getPhoto());
        user.setDescription(request.getDescription());
        return userMapper.map(userRepository.save(user));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enableUser(IdUserRequest idUserRequest) throws EntityNotFoundException {
        UserEntity user = findById(idUserRequest.getIdUser());
        if (user.isEnabled()) {
            throw new EntityNotFoundException("User is already enable");
        }
        user.setSoftDelete(false);
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableUser(IdUserRequest idUserRequest) throws EntityNotFoundException {

        UserEntity user = findById(idUserRequest.getIdUser());
        if (!user.isEnabled()) {
            throw new EntityNotFoundException("User is already disable");
        }
        user.setSoftDelete(true);
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public AuthenticationResponse login(AuthenticationRequest request) throws InvalidCredentialsException {
        UserEntity user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return new AuthenticationResponse(user.getEmail(), user.getUserId(), jwtUtils.generateToken(user));
    }

    @Transactional(rollbackFor = {Exception.class})
    private UserEntity findById(String id) {
        Optional<UserEntity> opt = Optional.ofNullable(userRepository.findByUserId(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("User not found.");
        }
        return opt.get();
    }

    private ListUserResponse buildListResponse(List<UserEntity> users) {
        List<UserResponse> userResponses = userMapper.map(users);
        ListUserResponse listUserResponse = new ListUserResponse();
        listUserResponse.setUserResponses(userResponses);
        return listUserResponse;
    }

    @Transactional
    @Override
    public UserResponse getByIdResponse(String email) throws EntityNotFoundException {
        return userMapper.map(userRepository.findByEmailAndSoftDeleteFalse(email));
    }

    @Override
    public UserResponse getById(String userId) throws EntityNotFoundException {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return userMapper.map(user);
    }

    @Override
    public ListUserResponse getAll() {
        List<UserEntity> users = userRepository.findAll();
        return buildListResponse(users);
    }

    @Override
    public UserEntity getByIdAndEnabled(String id) throws EntityNotFoundException {
        Optional<UserEntity> opt = Optional.ofNullable(userRepository.findByUserIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("User not found or disabled");
        }
        return opt.get();
    }

    public Boolean emailExist(String email) {
        Optional<UserEntity> optional = Optional.ofNullable(userRepository.findByEmail(email));
        if (optional.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public AuthenticationResponse loginGoogle(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return new AuthenticationResponse(user.getEmail(), user.getUserId(), jwtUtils.generateToken(user));
    }

    @Override
    public AuthenticationResponse registerGoogle(String email, String firstName, String lastName) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(List.of(roleRepository.findByName(RoleType.USER.getFullRoleName())));
        user.setDescription("STANDARD_USER");
        userRepository.save(user);

        return new AuthenticationResponse(
                email,
                userRepository.findByEmail(user.getEmail()).getUserId(),
                jwtUtils.generateToken(user));
    }

    private void validatePassword(String password) throws Exception {
        int size = password.length();
        if (size < 8) {
            throw new Exception("The password must be at least eight characters.");
        }
    }
}
