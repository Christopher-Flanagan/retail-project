package retail.jwt.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import retail.jwt.provider.tokenprovider.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static retail.jwt.provider.tokenprovider.JwtTokenProvider.HEADER_STRING;
import static retail.jwt.provider.tokenprovider.JwtTokenProvider.TOKEN_PREFIX;

@Component
public class JwtUsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private JwtTokenProvider tokenProvider;

    public JwtUsernamePasswordAuthenticationSuccessHandler(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)  {
        String token = tokenProvider.generateToken(authentication);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = tokenProvider.generateToken(authentication);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
