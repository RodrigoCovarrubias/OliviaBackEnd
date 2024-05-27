package cl.panaderia.productos.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class IntegrationIgService {

    private String token;
    private static final String url = "https://graph.instagram.com/me?fields=id,username,account_type,media_count&access_token=";

    public Map<String, Object> getMedia() {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url + token, Map.class);
        return response;
    }

}
