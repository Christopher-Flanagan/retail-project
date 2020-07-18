package retail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("retail.repo")
class RetailSpringApplicationTest {
    @Test
    void contextLoads() {
    }
}
