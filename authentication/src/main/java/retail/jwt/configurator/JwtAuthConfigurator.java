package retail.jwt.configurator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import retail.jwt.provider.authprovider.JWTTokenAuthProvider;
import retail.jwt.service.JwtUserDetailsService;

public class JwtAuthConfigurator extends SecurityConfigurerAdapter<AuthenticationManager, AuthenticationManagerBuilder> {

    private AuthenticationProvider jwtTokenAuthProvider;
    private UserDetailsService jwtUserDetailsService;
    private PasswordEncoder passwordEncoder;

    public JwtAuthConfigurator(JWTTokenAuthProvider jwtTokenAuthProvider,
                               JwtUserDetailsService jwtUserDetailsService,
                               PasswordEncoder passwordEncoder) {
        this.jwtTokenAuthProvider = jwtTokenAuthProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void init(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(jwtTokenAuthProvider);
    }
}
