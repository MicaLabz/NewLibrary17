package com.example.NewLibrary17.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

import static java.lang.Boolean.FALSE;

@Entity
@NoArgsConstructor
@Data
@Table(name = "CLIENTS")
@AllArgsConstructor
@SQLDelete( sql = "UPDATE clients SET soft_delete = true WHERE CLIENT_ID=?" )
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID", nullable = false)
    private Integer clientId;

    @Column(nullable = false, name = "EMAIL")
    private String email;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "LAST_NAME")
    private String lastName;

    @Column(nullable = false, name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn( name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false )
    private Role role;
    @Column( nullable = false, name = "PASSWORD")
    private String password;

    private Timestamp creationDate;
    private Timestamp updateDate;

    @Column(nullable = false, name = "SOFT_DELETE")
    private Boolean softDelete = FALSE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
