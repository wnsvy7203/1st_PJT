package com.mycom.myapp.service.oauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycom.myapp.repository.OAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final OAuthRepository oauthRepository;

    @Override
    public HashMap<String, String> isRegisteredUser(String email) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (oauthRepository.isRegisteredUser(email) == 1) {
            map.put("result", "registered");
        } else {
            map.put("result", "not_registered");
        }
        return map;
    }

    @Override
    public HashMap<String, String> getKakaoUserInfo(String accessToken) {
        HashMap<String, String> map = new HashMap<String, String>();
        String URI = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(URI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);
            con.setDoOutput(true);

            System.out.println(con.getRequestProperties());
            System.out.println(con.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JsonElement element = JsonParser.parseString(sb.toString());
            System.out.println("-------------------------");
            System.out.println(element);

            // Java Naming 보다 FrontEnd 에 맞춰서 ...
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            boolean has_email = kakao_account.getAsJsonObject().get("email").getAsBoolean();
            boolean is_email_valid = kakao_account.getAsJsonObject().get("is_email_valid").getAsBoolean();

            // 메율 유효성 검사
            if (!has_email || !is_email_valid) {
                map.put("result", "invalid_email");
                return map;
            }

            if (email != null)
                map.put("email", email);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();

            if (nickname != null)
                map.put("nickname", nickname);
            if (thumbnail_image != null)
                map.put("thumbnail_image", thumbnail_image);

            // 회원가입여부 확인
            if (oauthRepository.isRegisteredUser(email) == 1) {
                map.put("result", "registerd");
            } else {
                map.put("result", "not_registered");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}