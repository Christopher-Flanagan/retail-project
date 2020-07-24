package retail.policy;

import org.springframework.security.core.Authentication;
import retail.policy.access.PolicyDecision;

public interface PolicyChecker {

    PolicyDecision check(Authentication authentication, Object target, PolicyActions... action);

    PolicyDecision check(Authentication authentication, Object target, PolicyActions action);

    boolean supports(PolicyActions action);

    boolean supports(PolicyActions... action);

}
