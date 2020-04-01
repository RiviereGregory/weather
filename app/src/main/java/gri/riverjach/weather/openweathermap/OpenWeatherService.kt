package gri.riverjach.weather.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "7dbeadd701197984160e858d6f956075"

interface OpenWeatherService {

    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY
    ): Call<WeatherWrapper>
}