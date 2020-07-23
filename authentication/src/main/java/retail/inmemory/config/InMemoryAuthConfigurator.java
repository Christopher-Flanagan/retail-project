package retail.inmemory.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class InMemoryAuthConfigurator extends SecurityConfigurerAdapter<AuthenticationManager, AuthenticationManagerBuilder> {

    private PasswordEncoder passwordEncoder;

    public InMemoryAuthConfigurator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void init(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("memuser")
                .password(passwordEncoder.encode("password"))
                .roles("USER");
    }
}
