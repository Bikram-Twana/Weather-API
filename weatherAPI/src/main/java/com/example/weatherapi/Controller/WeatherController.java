package com.example.weatherapi.Controller;

import com.example.weatherapi.Service.WeatherService;
import com.example.weatherapi.Weather.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("")
    public String homePage(Model model){
        model.addAttribute("currentWeather",weatherService.getCurrentWeather("Bhaktapur","Nepal"));
        model.addAttribute("currentWeathers",weatherService.getCurrentWeather("Delhi","India"));
        model.addAttribute("currentWeather1",weatherService.getCurrentWeather("Shanghai","China"));
        model.addAttribute("currentWeather2",weatherService.getCurrentWeather("Tokyo","Japan"));
        model.addAttribute("currentWeather3",weatherService.getCurrentWeather("London","uk"));
        model.addAttribute("currentWeather4",weatherService.getCurrentWeather("Bangkok","Thailand"));
        return "index";

    }
}
