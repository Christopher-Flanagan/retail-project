package retail.unittests.policy.checker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import retail.policy.access.PolicyDecision;
import retail.policy.access.checker.AdminChecker;
import retail.unittests.policy.DummyPolicyActions;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminCheckerTest {

    private AdminChecker adminChecker = new AdminChecker();

    @Mock
    private Authentication authentication;
    @Mock
    private GrantedAuthority grantedAuthority;

    @Test
    void doSupportsSinglePolicyTest() {
        assertTrue(adminChecker.supports(DummyPolicyActions.DUMMY_ISSUE));
    }

    @Test
    void doSinglePolicyCheckTest() {
        when(grantedAuthority.getAuthority()).thenReturn("ADMIN");
        doReturn(Collections.singleton(grantedAuthority)).when(authentication).getAuthorities();
        PolicyDecision decision = adminChecker.check(authentication, new Object(), DummyPolicyActions.DUMMY_ISSUE);

        assertEquals(PolicyDecision.ACCESS_GRANTED, decision);
    }

    @Test
    void doSinglePolicyInvalidRoleCheckTest() {
        when(grantedAuthority.getAuthority()).thenReturn("USER");
        doReturn(Collections.singleton(grantedAuthority)).when(authentication).getAuthorities();
        PolicyDecision decision = adminChecker.check(authentication, new Object(), DummyPolicyActions.DUMMY_ISSUE);

        assertEquals(PolicyDecision.ACCESS_DENIED, decision);
    }

    @Test
    void doSinglePolicyEmptyAuthorityTest() {
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        PolicyDecision decision = adminChecker.check(authentication, new Object(), DummyPolicyActions.DUMMY_ISSUE);

        assertEquals(PolicyDecision.ACCESS_DENIED, decision);
    }

    @Test
    void doSupportsMultiplePolicyTest() {
        assertTrue(adminChecker.supports(DummyPolicyActions.DUMMY_ISSUE, DummyPolicyActions.DUMMY_WRITE));
    }

    @Test
    void doMultiplePolicyCheckTest() {
        when(grantedAuthority.getAuthority()).thenReturn("ADMIN");
        doReturn(Collections.singleton(grantedAuthority)).when(authentication).getAuthorities();
        PolicyDecision decision = adminChecker.check(authentication, new Object(), DummyPolicyActions.DUMMY_ISSUE,
                DummyPolicyActions.DUMMY_READ);

        assertEquals(PolicyDecision.ACCESS_GRANTED, decision);
    }

    @Test
    void doMultiplePolicyEmptyAuthorityTest() {
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        PolicyDecision decision = adminChecker.check(authentication, new Object(), DummyPolicyActions.DUMMY_ISSUE,
                DummyPolicyActions.DUMMY_READ);

        assertEquals(PolicyDecision.ACCESS_DENIED, decision);
    }

    @Test
    void doMultiplePolicyInvalidRoleCheckTest() {
        when(grantedAuthority.getAuthority()).thenReturn("USER");
        doReturn(Collections.singleton(grantedAuthority)).when(authentication).getAuthorities();
        PolicyDecision decision = adminChecker.check(authentication, new Object(), DummyPolicyActions.DUMMY_ISSUE);

        assertEquals(PolicyDecision.ACCESS_DENIED, decision);
    }


}
