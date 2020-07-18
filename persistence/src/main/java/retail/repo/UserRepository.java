package retail.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import retail.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
