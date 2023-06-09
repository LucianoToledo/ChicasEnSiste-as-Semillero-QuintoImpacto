package com.chicasensistemas.seeder;

import com.chicasensistemas.auth.RoleType;
import com.chicasensistemas.model.entity.RoleEntity;
import com.chicasensistemas.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder implements CommandLineRunner {

  @Autowired
  private IRoleRepository roleRepository;

  @Override
  @Order(1)
  public void run(String... args) throws Exception { seedRoleTable(); }

  private void seedRoleTable(){
    if(roleRepository.count() == 0){
      createRole(RoleType.ADMIN);
      createRole(RoleType.USER);
    }
  }

  private void createRole(RoleType roleType){
    RoleEntity role = new RoleEntity();
    role.setName(roleType.getFullRoleName());
    role.setDescription(roleType.name());
    roleRepository.save(role);
  }
}
