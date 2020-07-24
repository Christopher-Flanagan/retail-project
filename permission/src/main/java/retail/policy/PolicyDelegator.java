package retail.policy;

import org.springframework.security.core.Authentication;

public interface PolicyDelegator {

    void hasPermission(Authentication authentication, Object target, PolicyActions... action);

    void hasPermission(Authentication authentication, Object target, PolicyActions action);

}
