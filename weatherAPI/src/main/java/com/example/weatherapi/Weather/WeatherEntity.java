package com.example.weatherapi.Weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WeatherEntity implements Serializable {

    String name;
    String description;
    BigDecimal feelsLike;
    BigDecimal temperature;
    String country;

}
