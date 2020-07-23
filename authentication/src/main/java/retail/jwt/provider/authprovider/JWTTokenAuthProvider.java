package retail.jwt.provider.authprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import retail.jwt.authtoken.JwtAuthenticationToken;
import retail.jwt.provider.tokenprovider.JwtTokenProvider;
import retail.model.ApplicationUser;
import retail.repo.ApplicationUserRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class JWTTokenAuthProvider implements AuthenticationProvider {

    private JwtTokenProvider tokenProvider;
    private ApplicationUserRepository applicationUserRepository;

    public JWTTokenAuthProvider(JwtTokenProvider tokenProvider, ApplicationUserRepository applicationUserRepository) {
        this.tokenProvider = tokenProvider;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getDetails();
        String username  = tokenProvider.extractUsername(token);
        Optional<ApplicationUser> user = applicationUserRepository.findByUsername( username );

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No User associated with the following name :" + username);
        }
        else  {
            return new JwtAuthenticationToken(user.get().getUsername(), null, emptyList(), token);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }
}
