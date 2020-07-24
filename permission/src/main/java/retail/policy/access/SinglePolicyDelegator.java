package retail.policy.access;

import org.springframework.security.core.Authentication;
import retail.policy.AccessDeniedException;
import retail.policy.PolicyActions;
import retail.policy.PolicyChecker;
import retail.policy.PolicyDelegator;

import java.util.List;

/**
 * Delegator class used to approve access to a resource if a single checker has approved the request.
 */
public class SinglePolicyDelegator implements PolicyDelegator {

    private List<PolicyChecker> polices;
    private boolean allowUndecidedOutcome = false;

    public SinglePolicyDelegator(List<PolicyChecker> polices) {
        this.polices = polices;
    }

    public SinglePolicyDelegator(List<PolicyChecker> polices, boolean allowUndecidedOutcome) {
        this.polices = polices;
        this.allowUndecidedOutcome = allowUndecidedOutcome;
    }

    @Override
    public void hasPermission(Authentication authentication, Object target, PolicyActions... actions) {
        int deny = 0;

        for (PolicyChecker policy : polices) {
            PolicyDecision result = policy.check(authentication, target, actions);

            switch (result) {
                case ACCESS_DENIED:
                    deny++;
                    break;
                case ACCESS_GRANTED:
                    return;
                default:
                    break;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException("Access to resource has been denied, user does not have the correct rights");
        }

        undecidedAllowed();
    }

    @Override
    public void hasPermission(Authentication authentication, Object target, PolicyActions action) {
        int deny = 0;

        for (PolicyChecker policy : polices) {
            PolicyDecision result = policy.check(authentication, target, action);

            switch (result) {
                case ACCESS_DENIED:
                    deny++;
                    break;
                case ACCESS_GRANTED:
                    return;
                default:
                    break;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException("Access to resource has been denied, user does not have the correct rights");
        }

        undecidedAllowed();
    }

    private void undecidedAllowed() {
        if (!allowUndecidedOutcome) {
            throw new AccessDeniedException("Undecided outcome not allowed");
        }
    }
}
