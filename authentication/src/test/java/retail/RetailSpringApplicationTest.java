package retail;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import retail.jwt.config.entrypoints.RestAuthenticationEntryPoint;
import retail.jwt.configurator.JwtAuthConfigurator;
import retail.jwt.configurator.JwtFilterConfigurator;
import retail.jwt.handlers.JwtUsernamePasswordAuthenticationFailureHandler;
import retail.jwt.handlers.JwtUsernamePasswordAuthenticationSuccessHandler;
import retail.jwt.provider.authprovider.JWTTokenAuthProvider;
import retail.jwt.provider.tokenprovider.JwtTokenProvider;
import retail.jwt.service.JwtUserDetailsService;

@SpringBootApplication
class RetailSpringApplicationTest {

    @EnableWebSecurity
    static class WebConfiguration extends WebSecurityConfigurerAdapter {

        private JWTTokenAuthProvider jwtTokenAuthProvider;
        private JwtUserDetailsService jwtUserDetailsService;
        private JwtTokenProvider jwtTokenProvider;
        private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
        private JwtUsernamePasswordAuthenticationFailureHandler failureHandler;
        private JwtUsernamePasswordAuthenticationSuccessHandler successHandler;
        private String LOGIN_URL = "/login";

        WebConfiguration(JWTTokenAuthProvider jwtTokenAuthProvider,
                         JwtUserDetailsService jwtUserDetailsService,
                         JwtTokenProvider jwtTokenProvider,
                         RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                         JwtUsernamePasswordAuthenticationFailureHandler failureHandler,
                         JwtUsernamePasswordAuthenticationSuccessHandler successHandler)
        {
            this.jwtTokenAuthProvider = jwtTokenAuthProvider;
            this.jwtUserDetailsService = jwtUserDetailsService;
            this.jwtTokenProvider = jwtTokenProvider;
            this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
            this.failureHandler = failureHandler;
            this.successHandler = successHandler;
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        @SuppressWarnings( "deprecation" )
        public PasswordEncoder NoOpPasswordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
                    .apply(new JwtFilterConfigurator(authenticationManagerBean(), jwtTokenProvider, failureHandler, successHandler))
                    .and()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .anonymous().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.apply(new JwtAuthConfigurator(jwtTokenAuthProvider, jwtUserDetailsService, NoOpPasswordEncoder()));

        }
    }
}
