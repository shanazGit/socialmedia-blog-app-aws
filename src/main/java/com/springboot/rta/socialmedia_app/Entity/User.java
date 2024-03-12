package com.springboot.rta.socialmedia_app.Entity;
        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.util.Set;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="userName",unique = true,nullable = false)
    private String username;
    @Column(name="email",unique = true,nullable = false)
    private String email;
    @Column(name="password",nullable = false)
    private String password;
    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name="user_roles",
            joinColumns=@JoinColumn(name="user_id",referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName="id")
    )
    private Set<Role> roles;

}
