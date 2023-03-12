package com.chicasensistemas.service;

import com.chicasensistemas.auth.RoleType;
import com.chicasensistemas.auth.config.filter.JwtUtils;
import com.chicasensistemas.exception.UserAlreadyExistException;
import com.chicasensistemas.mapper.UserMapper;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.UserRequest;
import com.chicasensistemas.model.response.UserResponse;
import com.chicasensistemas.repository.IRoleRepository;
import com.chicasensistemas.repository.IUserRepository;
import com.chicasensistemas.service.abstraction.IRegisterUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class RegisterServiceImpl implements IRegisterUser{

  @Autowired
  IUserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Autowired
  IRoleRepository roleRepository;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  private EmailService sendMailSender;

  @Override
  public UserResponse register(UserRequest request) throws UserAlreadyExistException, MessagingException {
    if (userRepository.findByEmailAndSoftDeleteFalse(request.getEmail()) != null) {
      throw new UserAlreadyExistException();
    }

    UserEntity user = userMapper.map(request, passwordEncoder.encode(request.getPassword()));

    user.setRoles(List.of(roleRepository.findByName(RoleType.USER.getFullRoleName())));
    user.setDescription("STANDARD_USER");

    UserResponse response = userMapper.map(userRepository.save(user));

    response.setToken(jwtUtils.generateToken(user));

    //sendMailSender.welcomeEmail(user.getEmail(), user.getFirstName());
    //sendMailSender.emailWithAttachment(user.getEmail(),"this is an email with arrachment...","this email has attachment","D:\\Cursos\\EGG\\Codes\\Html - Css\\Ej4\\index.html");
    sendMailSender.sendMail(userMapper.map(user.getFirstName(), user.getLastName(), user.getEmail()));
    return response;
  }

}
