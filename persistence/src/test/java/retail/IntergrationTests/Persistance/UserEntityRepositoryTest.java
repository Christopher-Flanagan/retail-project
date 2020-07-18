package retail.IntergrationTests.Persistance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import retail.repo.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class UserEntityRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void injectedComponentsAreNotNull(){
        assertNotNull(userRepository);
    }
}
