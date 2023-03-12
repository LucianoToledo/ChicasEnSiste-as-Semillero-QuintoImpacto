package com.chicasensistemas.repository;

import com.chicasensistemas.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity,String> {

  RoleEntity findByName(String name);
}
