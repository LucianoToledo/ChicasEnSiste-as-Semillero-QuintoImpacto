package com.chicasensistemas.model.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "ROLES")
@Getter
@Setter
public class RoleEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "ROLE_ID", nullable = false)
  private String roleId;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @CreationTimestamp
  @Column(name = "TIMESTAMP")
  private Timestamp timestamp;
}
