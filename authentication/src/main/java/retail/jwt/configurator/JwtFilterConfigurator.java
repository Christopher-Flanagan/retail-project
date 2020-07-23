package retail.jwt.configurator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import retail.jwt.filters.JWTAuthFilter;
import retail.jwt.filters.JwtUsernamePasswordAuthenticationFilter;
import retail.jwt.handlers.JwtUsernamePasswordAuthenticationFailureHandler;
import retail.jwt.handlers.JwtUsernamePasswordAuthenticationSuccessHandler;
import retail.jwt.provider.tokenprovider.JwtTokenProvider;

public class JwtFilterConfigurator extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private JwtUsernamePasswordAuthenticationFailureHandler failureHandler;
    private JwtUsernamePasswordAuthenticationSuccessHandler successHandler;

    public JwtFilterConfigurator(AuthenticationManager authenticationManager,
                                 JwtTokenProvider jwtTokenProvider,
                                 JwtUsernamePasswordAuthenticationFailureHandler failureHandler,
                                 JwtUsernamePasswordAuthenticationSuccessHandler successHandler) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
    }

    @Override
    public void configure(HttpSecurity http)  {
        http.addFilterBefore(new JWTAuthFilter(authenticationManager, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new JwtUsernamePasswordAuthenticationFilter( failureHandler, successHandler, authenticationManager), JWTAuthFilter.class);
    }
}
