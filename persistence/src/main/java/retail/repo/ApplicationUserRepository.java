package retail.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import retail.model.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);
}
