package com.mycom.myapp.service.oauth;

import java.util.HashMap;
import java.util.List;

public interface OAuthService {
    HashMap<String, String> isRegisteredUser(String email);
    HashMap<String, String> getKakaoUserInfo(String accessToken);
}
