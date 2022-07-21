package com.example.weatherapi.Service;

import com.example.weatherapi.Weather.WeatherEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;

@Service
public class WeatherService {
    private static final String weather_url = "http://api.openweathermap.org/data/2.5/weather?q={city},{country}&APPID={apikey}";

    @Value("${apiKey}")
    private String apikey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    MathContext m =new MathContext(4);



    public WeatherService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }


    //uri template
    public WeatherEntity getCurrentWeather(String city,String country){
        URI uri=null;
        uri = new UriTemplate(weather_url).expand(city,country,apikey);
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return convert(response);
    }

    public WeatherEntity convert(ResponseEntity<String> responseEntity){
        try{
            JsonNode node = objectMapper.readTree(responseEntity.getBody());
            return new WeatherEntity(node.path("name").asText(),
                    node.path("weather").get(0).path("description").asText(),
                    BigDecimal.valueOf(node.path("main").path("feels_like").asDouble()-273.15).round(m),
                    BigDecimal.valueOf(node.path("main").path("temp").asDouble()-273.15).round(m),
                    node.path("sys").path("country").asText());
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error parsing JSON" + e);
        }
    }
}
