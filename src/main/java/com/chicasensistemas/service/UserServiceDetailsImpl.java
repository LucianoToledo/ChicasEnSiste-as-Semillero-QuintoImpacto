package com.chicasensistemas.service;

import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {

  private static final String USER_NOT_FOUND_MESSAGE = "User not found.";

  @Autowired
  IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUser(username);
  }
  private UserEntity getUser(String username) {
    UserEntity user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);
    }
    return user;
  }

}
