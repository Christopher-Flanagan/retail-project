package retail.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dummy Controllers used to validate the authentication via jwt
 */
@RestController
@RequestMapping("/rest/api/dummy")
public class DummyRestController {

    @GetMapping
    public HttpStatus getHomePageStatus() {
        return HttpStatus.OK;
    }

    @PostMapping
    public HttpStatus postHomePageStatus() {
        return HttpStatus.OK;
    }
}
