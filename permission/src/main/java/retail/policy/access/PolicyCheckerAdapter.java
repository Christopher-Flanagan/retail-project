package retail.policy.access;

import org.springframework.security.core.Authentication;
import retail.policy.PolicyActions;
import retail.policy.PolicyChecker;

import static retail.policy.access.PolicyDecision.ACCESS_UNDECIDED;

public class PolicyCheckerAdapter implements PolicyChecker {

    @Override
    public PolicyDecision check(Authentication authentication, Object target, PolicyActions... action) {
        return ACCESS_UNDECIDED;
    }

    @Override
    public PolicyDecision check(Authentication authentication, Object target, PolicyActions action) {
        return ACCESS_UNDECIDED;
    }

    @Override
    public boolean supports(PolicyActions action) {
        return false;
    }

    @Override
    public boolean supports(PolicyActions... action) {
        return false;
    }
}
