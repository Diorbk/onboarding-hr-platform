package uk.ac.cf.spring.Group13Project1.security;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaService {
    @Value("${google.recaptcha.key.secret}")
    private String recaptchaSecretKey;
    @Value("${google.recaptcha.url}")
    private String verifyUrl;

    public boolean verifyRecaptcha(String clientRecaptchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", recaptchaSecretKey);
        requestMap.add("response", clientRecaptchaResponse);

        ResponseEntity<Map> response = restTemplate.postForEntity(verifyUrl, requestMap, Map.class);

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null) {
            Boolean isSuccess = (Boolean) responseBody.get("success");
            return Boolean.TRUE.equals(isSuccess);
        }

        return false;
    }

}
