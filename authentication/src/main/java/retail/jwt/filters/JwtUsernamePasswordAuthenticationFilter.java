package retail.jwt.filters;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import retail.dto.LoginDetails;
import retail.jwt.handlers.JwtUsernamePasswordAuthenticationFailureHandler;
import retail.jwt.handlers.JwtUsernamePasswordAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUsernamePasswordAuthenticationFailureHandler failureHandler;
    private JwtUsernamePasswordAuthenticationSuccessHandler successHandler;

    public JwtUsernamePasswordAuthenticationFilter(JwtUsernamePasswordAuthenticationFailureHandler failureHandler,
                                                   JwtUsernamePasswordAuthenticationSuccessHandler successHandler,
                                                   AuthenticationManager authenticationManager) {
        this.failureHandler = failureHandler;
        this.successHandler = successHandler;
        super.setAuthenticationManager(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/rest/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        LoginDetails loginDetails = new LoginDetails( obtainUsername(request), obtainPassword(request));
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);

        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication request failed: " + failed.toString(), failed);
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
            logger.debug("Delegating to authentication failure handler " + failureHandler);
        }
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
