package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByEmailAndSoftDeleteFalse(String email);

  UserEntity findByEmail(String email);

  //@Query("SELECT u FROM UserEntity u WHERE u.id LIKE :id ")
  UserEntity findByUserIdAndSoftDeleteFalse(String id);

  UserEntity findByUserId(String user);
  UserEntity findByResetTokenAndSoftDeleteFalse(String token);
  List<UserEntity> findAll();

}
