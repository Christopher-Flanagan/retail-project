package retail.IntergrationTests.Persistance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import retail.repo.ApplicationUserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ApplicationUserEntityRepositoryTest {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    void injectedComponentsAreNotNull(){
        assertNotNull(applicationUserRepository);
    }
}
