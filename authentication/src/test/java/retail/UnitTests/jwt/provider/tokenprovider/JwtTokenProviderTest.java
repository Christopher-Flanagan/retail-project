package retail.UnitTests.jwt.provider.tokenprovider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import retail.jwt.properties.JwtProperties;
import retail.jwt.provider.tokenprovider.JwtTokenProvider;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static retail.jwt.provider.tokenprovider.JwtTokenProvider.HEADER_STRING;
import static retail.jwt.provider.tokenprovider.JwtTokenProvider.TOKEN_PREFIX;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    private static JwtProperties jwtProperties;
    @Mock
    private MockHttpServletRequest mockHttpServletRequest;
    @Mock
    private Authentication authentication;

    private static JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(jwtProperties.getKey()).thenReturn("sdfsgfdafefergfbdadfewhrthshfhsreer");
        when(jwtProperties.getDuration()).thenReturn("600000");
        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
        jwtTokenProvider.init();
    }

    @Test
    void missingAuthRequestHeader() {
        String token = jwtTokenProvider.extractToken(mockHttpServletRequest);
        assertNull(token);
    }

    @Test
    void BearerRequestHeader() {
        when(mockHttpServletRequest.getHeader(HEADER_STRING)). thenReturn(TOKEN_PREFIX + "token");
        String token = jwtTokenProvider.extractToken(mockHttpServletRequest);
        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    void missingBearerRequestHeader() {
        when(mockHttpServletRequest.getHeader(HEADER_STRING)). thenReturn("");
        String token = jwtTokenProvider.extractToken(mockHttpServletRequest);
        assertNull(token);
    }

    @Test
    void emptyBearerRequestHeader() {
        when(mockHttpServletRequest.getHeader(HEADER_STRING)). thenReturn(TOKEN_PREFIX);
        String token = jwtTokenProvider.extractToken(mockHttpServletRequest);
        assertNull(token);
    }

    @Test
    void generateTokenTest() {
        when(authentication.getPrincipal()).thenReturn("someUser");
        String token = jwtTokenProvider.generateToken(authentication);
        String username = jwtTokenProvider.extractUsername(token);
        Date expiredAt = jwtTokenProvider.extractExpirationData(token);
        boolean withinRange = withInRange(expiredAt);

        assertNotNull(token);
        assertEquals("someUser", username);
        assertTrue(withinRange);
    }

    private boolean withInRange(Date expiredAt) {
        long duration = Long.parseLong(jwtProperties.getDuration());
        Date minusFiveSeconds = new Date( System.currentTimeMillis() + (duration - 5000L));
        Date plusFiveSeconds = new Date( System.currentTimeMillis() + (duration + 5000L));

        return minusFiveSeconds.before(expiredAt) && plusFiveSeconds.after(expiredAt);
    }
}
