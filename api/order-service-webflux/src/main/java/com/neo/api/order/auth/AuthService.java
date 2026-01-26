package com.neo.api.order.auth;

import com.neo.api.order.auth.dto.BaseResponseDto;
import com.neo.api.order.auth.dto.LoginRequestDto;
import com.neo.api.order.auth.dto.TokenDto;
import com.neo.api.order.config.redis.SessionStorage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SessionStorage sessionStorage;

    @Value("${keycloak.client-id}")
    private String kcClientId;

    @Value("${keycloak.client-secret}")
    private String kcClientSecret;

    @Value("${keycloak.get-token-url}")
    private String kcGetTokenUrl;

    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String ACCESS_TOKEN = "Access-Token";
    private static final String REFRESH_TOKEN = "Refresh-Token";
    private static final String EXPIRES_IN = "Expires-In";
    private static final String DEVICE_ID = "Device-Id";

    public String getJwtAuthToken(String username, String password, String clientId, String clientSecret) {
        LoginRequestDto request = LoginRequestDto.builder()
                .username(username)
                .password(password)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        log.info("Start to get access token");
        Optional<TokenDto> tokenDto = this.getAccessToken(request);
        if (tokenDto.isPresent()) {
            return tokenDto.get().getAccessToken();
        } else {
            return StringUtils.EMPTY;
        }
    }

    public ResponseEntity<Object> login(LoginRequestDto request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        log.info("Start to get access token");
        String deviceId = servletRequest.getHeader(DEVICE_ID);

        Optional<TokenDto> tokenDto = this.getAccessToken(request);

        servletResponse.addHeader(ACCESS_TOKEN, tokenDto.get().getAccessToken());
        servletResponse.addHeader(EXPIRES_IN, String.valueOf(tokenDto.get().getExpiresIn()));

        sessionStorage.putCache(REFRESH_TOKEN, deviceId, tokenDto.get().getRefreshToken(), 1800);

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .status("SUCCESS")
                .build());
    }

    public ResponseEntity<Object> refreshToken(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        log.info("Start to refresh access token");

        String deviceId = servletRequest.getHeader(DEVICE_ID);
        String refreshToken = (String) sessionStorage.getCache(REFRESH_TOKEN, deviceId);

        Optional<TokenDto> tokenDto = this.getRefreshToken(refreshToken);

        servletResponse.addHeader(ACCESS_TOKEN, tokenDto.get().getAccessToken());
        servletResponse.addHeader(EXPIRES_IN, String.valueOf(tokenDto.get().getExpiresIn()));

        sessionStorage.putCache(REFRESH_TOKEN, deviceId, tokenDto.get().getRefreshToken(), tokenDto.get().getRefreshExpiresIn());

        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .status("SUCCESS")
                .build());
    }

    private Optional<TokenDto> getAccessToken(LoginRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", GRANT_TYPE_PASSWORD);
        //requestBody.add("client_id", kcClientId);
        //requestBody.add("client_secret", kcClientSecret);
        requestBody.add("client_id", request.getClientId());
        requestBody.add("client_secret", request.getClientSecret());
        requestBody.add("username", request.getUsername());
        requestBody.add("password", request.getPassword());

        try {
            log.info("Start to get access token - Before REST call");
            ResponseEntity<TokenDto> response = restTemplate.postForEntity(kcGetTokenUrl,
                    new HttpEntity<>(requestBody, headers), TokenDto.class);
            log.info("Start to get access token - After REST call");
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<TokenDto> getRefreshToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", refreshToken);
        requestBody.add("client_id", kcClientId);
        requestBody.add("client_secret", kcClientSecret);

        ResponseEntity<TokenDto> response = restTemplate.postForEntity(kcGetTokenUrl,
                new HttpEntity<>(requestBody, headers), TokenDto.class);

        return Optional.ofNullable(response.getBody());
    }
}