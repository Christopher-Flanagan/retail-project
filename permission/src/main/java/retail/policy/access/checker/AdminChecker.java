package retail.policy.access.checker;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import retail.policy.PolicyActions;
import retail.policy.access.PolicyCheckerAdapter;
import retail.policy.access.PolicyDecision;

import java.util.Collection;

import static retail.policy.access.PolicyDecision.ACCESS_DENIED;
import static retail.policy.access.PolicyDecision.ACCESS_GRANTED;

@Component
public class AdminChecker extends PolicyCheckerAdapter {

    @Override
    public PolicyDecision check(Authentication authentication, Object target, PolicyActions... actions) {
        Collection<? extends GrantedAuthority> userAuthorities = authentication
                .getAuthorities();

        if (userAuthorities == null || !this.supports(actions)) {
            return ACCESS_DENIED;
        }

        for (GrantedAuthority authority : userAuthorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN") || role.equals("ADMIN")) {
                return ACCESS_GRANTED;
            }
        }

        return ACCESS_DENIED;
    }

    @Override
    public PolicyDecision check(Authentication authentication, Object target, PolicyActions action) {
        Collection<? extends GrantedAuthority> userAuthorities = authentication
                .getAuthorities();

        if (userAuthorities == null || !this.supports(action)) {
            return ACCESS_DENIED;
        }

        for (GrantedAuthority authority : userAuthorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN") || role.equals("ADMIN")) {
                return ACCESS_GRANTED;
            }
        }

        return ACCESS_DENIED;
    }

    @Override
    public boolean supports(PolicyActions action) {
        return true;
    }

    @Override
    public boolean supports(PolicyActions... action) {
        return true;
    }
}
