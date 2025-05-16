package team.project.fiverockrun.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.fiverockrun.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
