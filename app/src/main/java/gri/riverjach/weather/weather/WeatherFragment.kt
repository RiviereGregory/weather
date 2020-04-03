package gri.riverjach.weather.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import gri.riverjach.weather.App
import gri.riverjach.weather.R
import gri.riverjach.weather.openweathermap.WeatherWrapper
import gri.riverjach.weather.openweathermap.mapOpenWeatherDataToWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {
    companion object {
        val EXTRA_CITY_NAME = "gri.riverjach.weather.extras.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()
    }

    private val TAG = WeatherFragment::class.java.simpleName

    private lateinit var cityName: String

    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.intent!!.hasExtra(EXTRA_CITY_NAME)) {
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
        }
    }

    private fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName

        val call = App.weatherService.getWeather("$cityName, fr")
        call.enqueue(object : Callback<WeatherWrapper> {
            override fun onResponse(
                call: Call<WeatherWrapper>?,
                response: Response<WeatherWrapper>?
            ) {
                response?.body()?.let {
                    val weather = mapOpenWeatherDataToWeather(it)
                    updateUI(weather)
                    Log.i(TAG, "Weather response: ${weather}")
                }
            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, "Could not load city weather", t)
                Toast.makeText(
                    activity,
                    getString(R.string.weather_message_error_could_not_load_weather),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun updateUI(weather: Weather) {
        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.baseline_cloud_off_black_24dp)
            .into(weatherIcon)

        weatherDescription.text = weather.description
        temperature.text = getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }
}