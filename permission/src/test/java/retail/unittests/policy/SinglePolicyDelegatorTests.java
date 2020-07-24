package retail.unittests.policy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import retail.policy.AccessDeniedException;
import retail.policy.PolicyActions;
import retail.policy.PolicyChecker;
import retail.policy.access.PolicyDecision;
import retail.policy.access.SinglePolicyDelegator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SinglePolicyDelegatorTests {

    @Mock
    private PolicyChecker grantedChecker;
    @Mock
    private PolicyChecker deniedChecker;
    @Mock
    private PolicyChecker undecidedChecker;
    @Mock
    private Authentication authentication;

    private SinglePolicyDelegator delegator;

    @Test
    void emptyPolicyListTest() {
        assertThrows(AccessDeniedException.class, () -> {
            delegator = new SinglePolicyDelegator(Collections.emptyList());
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });
    }

    @Test
    void emptyPolicyListAllowUndecidedStatusTest() {
        assertDoesNotThrow(() -> {
            delegator = new SinglePolicyDelegator(Collections.emptyList(), true);
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });
    }

    @Test
    void grantedPolicyCheckTest() {
        assertDoesNotThrow(() -> {
            doReturn(PolicyDecision.ACCESS_GRANTED).when(grantedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            delegator = new SinglePolicyDelegator(Collections.singletonList(grantedChecker));
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });

    }

    @Test
    void deniedPolicyCheckTest() {
        assertThrows(AccessDeniedException.class, () -> {
            doReturn(PolicyDecision.ACCESS_DENIED).when(deniedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            delegator = new SinglePolicyDelegator(Collections.singletonList(deniedChecker));
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });
    }

    @Test
    void undecidedPolicyCheckTest() {
        assertThrows(AccessDeniedException.class, () -> {
            doReturn(PolicyDecision.ACCESS_UNDECIDED).when(undecidedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            delegator = new SinglePolicyDelegator(Collections.singletonList(undecidedChecker));
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });
    }

    @Test
    void allowUndecidedPolicyCheckTest() {
        assertDoesNotThrow(() -> {
            doReturn(PolicyDecision.ACCESS_UNDECIDED).when(undecidedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            delegator = new SinglePolicyDelegator(Collections.singletonList(undecidedChecker), true);
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });
    }

    @Test
    void MultiPolicyCheckTest() {
        assertDoesNotThrow(() -> {
            List<PolicyChecker> list = Arrays.asList(deniedChecker, undecidedChecker, grantedChecker);
            doReturn(PolicyDecision.ACCESS_DENIED).when(deniedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            doReturn(PolicyDecision.ACCESS_UNDECIDED).when(undecidedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            doReturn(PolicyDecision.ACCESS_GRANTED).when(grantedChecker).check(any(Authentication.class), any(), any(PolicyActions.class));
            delegator = new SinglePolicyDelegator(list);
            delegator.hasPermission(authentication, new Object(), DummyPolicyActions.DUMMY_READ);
        });
    }

}
