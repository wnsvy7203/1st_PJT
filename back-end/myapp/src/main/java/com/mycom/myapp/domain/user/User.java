package com.mycom.myapp.domain.user;

import com.mycom.myapp.domain.Address;
import com.mycom.myapp.domain.Alert;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Table(name = "user")
@Getter @Setter
// 회원
public class User implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "user_seq")
    private Long seq;

    @Column(name = "user_name", length = 20, nullable = false)
    private String name;

    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    @Column(name = "user_password", length = 16, nullable = false)
    private String password;

    @Column(name = "user_phone_number", length = 11, nullable = false)
    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_profile_image_url")
    private String profileImageUrl;

    @Column(name = "user_register_date", nullable = false)
    private LocalDateTime userRegisterDate;

    @Column(name = "user_delete_date")
    private LocalDateTime userDeleteDate;

    @Column(name = "user_code", length = 2, nullable = false)
    private String userCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    // Alert
    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private List<Alert> alerts = new ArrayList<>();

    @PrePersist
    public void userRegisterDate() {
        this.userRegisterDate = LocalDateTime.now();
    }
}
