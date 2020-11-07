package pl.maszyny_polskie.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USR_ID")
    private int id;
    @Column(name = "USR_NAME")
    private String userName;
    @Column(name = "USR_PASSWORD")
    private String password;
    @Column(name = "USR_EMAIL")
    private String email;
    @Column(name = "USR_FIRSTNAME")
    private String firstName;
    @Column(name = "USR_LASTNAME")
    private String lastName;
    @Column(name = "USR_COUNTRY")
    private String country;
    @Column(name = "USR_IS_ACTIVE")
    private boolean active;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "USR_ROLE", joinColumns = @JoinColumn(name = "USR_ID"), inverseJoinColumns = @JoinColumn(name = "ROL_ID"))
    private Set<Role> roles;
}
