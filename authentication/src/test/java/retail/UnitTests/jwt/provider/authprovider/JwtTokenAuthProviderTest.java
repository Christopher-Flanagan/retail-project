package retail.UnitTests.jwt.provider.authprovider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import retail.jwt.authtoken.JwtAuthenticationToken;
import retail.jwt.provider.authprovider.JWTTokenAuthProvider;
import retail.jwt.provider.tokenprovider.JwtTokenProvider;
import retail.model.ApplicationUser;
import retail.repo.ApplicationUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenAuthProviderTest {

    private JWTTokenAuthProvider authProvider;
    @Mock
    private ApplicationUserRepository applicationUserRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    Authentication authentication;
    @Mock
    private ApplicationUser applicationUser;


    @BeforeEach
     void setUp() {
        MockitoAnnotations.initMocks(this);
        authProvider = new JWTTokenAuthProvider(jwtTokenProvider, applicationUserRepository);
        when(authentication.getDetails()).thenReturn("token");
        when(jwtTokenProvider.extractUsername("token")).thenReturn("user");

    }

    @Test
    void authenticateTest() {
        Optional<ApplicationUser> user = Optional.of(applicationUser);
        when(applicationUserRepository.findByUsername("user")).thenReturn(user);
        when(applicationUser.getUsername()).thenReturn("user");
        Authentication auth = authProvider.authenticate(authentication);

        assertTrue(auth instanceof JwtAuthenticationToken);
        assertEquals(auth.getPrincipal(), "user");
        assertEquals(auth.getDetails(), "token");
    }

    @Test
    void userNotFoundTest() {
        assertThrows(UsernameNotFoundException.class, () -> {
            Optional<ApplicationUser> user = Optional.empty();
            when(applicationUserRepository.findByUsername("user")).thenReturn(user);
            Authentication auth = authProvider.authenticate(authentication);
        });
    }
}
