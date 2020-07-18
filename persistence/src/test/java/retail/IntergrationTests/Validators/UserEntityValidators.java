package retail.IntergrationTests.Validators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retail.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityValidators {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void minFirstNameSize() {
        User user = User.builder()
                .firstName("")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxFirstNameSize() {
        User user = User.builder()
                .firstName("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void minLastNameSize() {
        User user = User.builder()
                .firstName("test")
                .lastName("")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxLastNameSize() {
        User user = User.builder()
                .firstName("test")
                .lastName("tetttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void minUsernameSize() {
        User user = User.builder()
                .firstName("test")
                .lastName("test")
                .username("")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxUsernameSize() {
        User user = User.builder()
                .firstName("test")
                .lastName("test")
                .username("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void minPasswordSize() {
        User user = User.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void maxPasswordSize() {
        User user = User.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void invalidEmail() {
        User user = User.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("testgmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    void nullEntries() {
        assertThrows(NullPointerException.class, () -> User.builder()
                .firstName(null)
                .lastName(null)
                .username(null)
                .password(null)
                .emailAddress(null)
                .build());
    }

    @Test
    void validEntries() {
        User user = User.builder()
                .firstName("test")
                .lastName("test")
                .username("test")
                .password("testing")
                .emailAddress("test@gmail.com")
                .build();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
}
