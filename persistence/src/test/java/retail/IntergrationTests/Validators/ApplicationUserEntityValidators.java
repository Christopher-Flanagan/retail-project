package retail.IntergrationTests.Validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retail.model.ApplicationUser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationUserEntityValidators {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void minFirstNameSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxFirstNameSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void minLastNameSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxLastNameSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("tetttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void minUsernameSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("test")
                .username("")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxUsernameSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("test")
                .username("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void minPasswordSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxPasswordSize() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void invalidEmail() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("testgmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void nullEntries() {
        assertThrows(NullPointerException.class, () -> ApplicationUser.builder()
                .firstName(null)
                .lastName(null)
                .username(null)
                .password(null)
                .emailAddress(null)
                .build());
    }

    @Test
    void validEntries() {
        ApplicationUser applicationUser = ApplicationUser.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<ApplicationUser>> violations = validator.validate(applicationUser);
        assertTrue(violations.isEmpty());
    }
}
