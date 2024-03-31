package com.legit.housing.entity.main;

import com.legit.housing.entity.main.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends AuditableEntityBase implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    public User(String username, String password, String firstname, String lastname, Role role, Boolean active) {
        super();
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.active = active;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        User that = (User) o;
        return Objects.equals(this.username, that.username);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
