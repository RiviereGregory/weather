package gri.riverjach.weather.openweathermap

import gri.riverjach.weather.weather.Weather

fun mapOpenWeatherDataToWeather(weatherWrapper: WeatherWrapper): Weather {
    val weatherFirst = weatherWrapper.weather.first()
    return Weather(
        description = weatherFirst.description,
        temperature = weatherWrapper.main.temperature,
        humidity = weatherWrapper.main.humidity,
        pressure = weatherWrapper.main.pressure,
        iconUrl = "https://openweathermap.org/img/wn/${weatherFirst.icon}.png"
    )

}