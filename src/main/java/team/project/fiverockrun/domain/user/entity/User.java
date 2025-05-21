package team.project.fiverockrun.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(nullable = false)
    private boolean isDeleted;

    public User(String email, String phoneNumber, String name, String password, UserRole userRole) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.isDeleted = false;
    }

    //유저 이름 업데이트
    public void updateName(String name) {
        this.name = name;
    }

    //유저 이메일 업데이트
    public void updateEmail(String email) {
        this.email = email;
    }

    //유저 전화번호 업데이트
    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //유저 비밀번호 업데이트
    public void updatePassword(String password){
        this.password = password;
    }

    //유저 삭제
    public void deleteUser() { this.isDeleted = true; }

}
