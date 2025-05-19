package team.project.fiverockrun.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
