/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.openlabs.newclient.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author vitor-s-silva
 */
@RestController
public class HomeController {
    
    @RequestMapping("/getUserDetails")
    public String requestParamMethodNameHere(OAuth2Authentication user) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) user.getDetails();
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .getMessageConverters()
                .add(new MappingJackson2HttpMessageConverter());
        String endpointURL = "localhost:8090/userDetails";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", details.getTokenType() + " " + details.getTokenValue());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    endpointURL,
                    HttpMethod.GET,
                    entity,
                    String.class);
            return response.getBody();
        } catch (RestClientException e) {
        }
        return "fail";
    }
//    @GetMapping("/getUserDetails")
//    public String index() {
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl = "http://localhost:8090/userDetails";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl, String.class);
////        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//        return "Oi";
//    }
}
