package dev.code_n_roll.jcon.example;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class SomethingJava {

    private static final MyResponseJava DEFAULT_RESPONSE = new MyResponseJava("", 0.0);

    public final MyResponseJava makeSomethingRequest(RequestObjectJava req) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String port = "8080";
        ResponseEntity<MyResponseJava> responseEntity = restTemplate
                .getForEntity("http://localhost:" + port, MyResponseJava.class);
        MyResponseJava body = responseEntity.getBody();
        return Objects.requireNonNullElse(body, DEFAULT_RESPONSE);
    }
}
