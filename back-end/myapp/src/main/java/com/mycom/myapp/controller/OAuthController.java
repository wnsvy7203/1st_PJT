package com.mycom.myapp.controller;

import com.mycom.myapp.service.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(
        origins = {"http://localhost:8080", "https://i8e206.p.ssafy.io"},
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
)
public class OAuthController {

    @Autowired
    OAuthService service;

    @GetMapping(value = "/isRegisteredUser")
    public HashMap<String, String> isRegisteredUser(String email) {
        System.out.println("email : " + email);
        return service.isRegisteredUser(email);
    }

    @PostMapping(value = "/kakaoLogin")
    public HashMap<String, String> kakaoLogin(String accessToken) {
        System.out.println("accessToken : " + accessToken);
        return service.getKakaoUserInfo(accessToken);
    }
}
