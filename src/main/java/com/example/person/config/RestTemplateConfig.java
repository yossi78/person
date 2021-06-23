package com.example.person.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;




@Configuration
public class RestTemplateConfig {


/* #######################  MOST ADD  ###########################################################################
            <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.3.4</version>
        </dependency>
/* #########################################################################################################*/



    private final int READ_TIME_OUT=60000;
    private final  int CONNECT_TIME_OUT=60000;



    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory(READ_TIME_OUT, CONNECT_TIME_OUT));
        return restTemplate;
    }



    private ClientHttpRequestFactory clientHttpRequestFactory(int readTimeOut, int connectTimeOut) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(readTimeOut);
        factory.setConnectTimeout(connectTimeOut);
        return factory;
    }
}

