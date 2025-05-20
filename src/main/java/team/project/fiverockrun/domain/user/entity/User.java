package team.project.fiverockrun.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import team.project.fiverockrun.common.entity.Timestamped;
import team.project.fiverockrun.domain.user.enums.UserRole;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    public User(String email, String phoneNumber, String name, String password, UserRole userRole) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }


}
