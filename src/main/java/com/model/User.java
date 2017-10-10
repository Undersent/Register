package com.model;

import com.validators.ExtendedEmailValidator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @Size(min=1)
    @NotNull(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @Size(min=1)
    @NotNull(message = "*Please provide your name")
    private String name;

    @Column(name = "last_name")
    @Size(min=1)
    @NotNull(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ExtendedEmailValidator
    private String email;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "confirmation_Email_token")
    String confirmationEmailToken;
}
