package com.chicasensistemas.model.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements UserDetails { //TODO: en el registro no va el telefono ni la imagen, se agregar en el update
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id")
    protected  String userId;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(name = "email", nullable = false, unique = true)
    protected String email;

    @Column(name = "password")
    protected String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "photo")
    private String photo;

    @Column (name = "description")
    private String description;

    @Column(name= "resetToken")
    private String resetToken;

    @Column(name = "timestamps_reset_token")
    private LocalDateTime resetTokenCreationDate;

    @CreationTimestamp
    @Column(name = "timestamps", nullable = false, updatable = false)
    protected LocalDateTime timestamps;

    @Column(name = "soft_delete", nullable = false)
    protected Boolean softDelete = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    protected List<RoleEntity> roles;

    @Override
    public Collection getAuthorities() {
        return this.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return !softDelete; }
}
