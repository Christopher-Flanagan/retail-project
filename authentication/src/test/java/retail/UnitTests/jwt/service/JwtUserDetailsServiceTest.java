package retail.UnitTests.jwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import retail.jwt.service.JwtUserDetailsService;
import retail.model.ApplicationUser;
import retail.repo.ApplicationUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @Mock
    private ApplicationUserRepository applicationUserRepository;
    @Mock
    private ApplicationUser applicationUser;


    private JwtUserDetailsService jwtUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtUserDetailsService = new JwtUserDetailsService(applicationUserRepository);
    }

    @Test
    void getUserByNameTest() {
        Optional<ApplicationUser> userOptional = Optional.of(applicationUser);
        when(applicationUserRepository.findByUsername("user")).thenReturn(userOptional);
        when(applicationUser.getUsername()).thenReturn("user");
        when(applicationUser.getPassword()).thenReturn("password");

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("user");

        assertEquals("user", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void getUserByNameExceptionTest() {
        assertThrows(UsernameNotFoundException.class, () -> {
            Optional<ApplicationUser> userOptional = Optional.empty();
            when(applicationUserRepository.findByUsername("user")).thenReturn(userOptional);
            jwtUserDetailsService.loadUserByUsername("user");
        });
    }
}
