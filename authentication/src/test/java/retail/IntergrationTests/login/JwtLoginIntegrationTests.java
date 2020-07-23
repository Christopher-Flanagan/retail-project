package retail.IntergrationTests.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static retail.jwt.provider.tokenprovider.JwtTokenProvider.HEADER_STRING;
import static retail.jwt.provider.tokenprovider.JwtTokenProvider.TOKEN_PREFIX;

@SpringBootTest({"jwt.duration=60000",
        "jwt.key=kcNczHeVV7c47fSXjQef2jLVBtpTD13IImtNjtGChIFXgAHw1P8wTlpQzJDlnJvqP2RCwI"})
@AutoConfigureMockMvc
class JwtLoginIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    private String LOGIN_URL = "/rest/login";
    private String DUMMY_URL = "/rest/api/dummy";

    @Test
    void doLoginTest() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                .param("username", "memuser")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(HEADER_STRING));
    }

    @Test
    void doLoginFailTest() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                .param("username", "memuser")
                .param("password", "passsword")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist(HEADER_STRING));
    }

    @Test
    void doGetRequestNoCredentials() throws Exception {
        mockMvc.perform(get(DUMMY_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void doPostRequestWithLoginCredentials() throws Exception {
        mockMvc.perform(post(DUMMY_URL)
                .param("username", "memuser")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void doGetRequestWithJwtCredentials() throws Exception {
         mockMvc.perform(post(LOGIN_URL)
                .param("username", "memuser")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(HEADER_STRING))
                .andDo(result -> {
                    String token = result.getResponse().getHeader(HEADER_STRING);
                    mockMvc.perform(get(DUMMY_URL)
                            .header(HEADER_STRING, token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
                });
    }

    @Test
    void doGetRequestWithRandomJwtCredentials() throws Exception {
        mockMvc.perform(post(DUMMY_URL)
                .param(HEADER_STRING, TOKEN_PREFIX + "mefdfdgdfgsfdgsgeatrearebhfxgkdtuo43erghrtkyiipergae3454w5ugmuser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
