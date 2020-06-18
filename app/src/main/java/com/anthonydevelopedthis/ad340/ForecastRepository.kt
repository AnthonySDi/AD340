package com.anthonydevelopedthis.ad340

import android.net.DnsResolver
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anthonydevelopedthis.ad340.api.CurrentWeather
import com.anthonydevelopedthis.ad340.api.WeeklyForecast
import com.anthonydevelopedthis.ad340.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.random.Random


class ForecastRepository {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast: LiveData<WeeklyForecast> = _weeklyForecast

    fun loadWeeklyForecast(zipcode: String){
        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY)

        call.enqueue(object : retrofit2.Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading location for weekly forecast",t )
            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()

                if (weatherResponse != null) {
                    // load 7 date forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    forecastCall.enqueue(object: retrofit2.Callback<WeeklyForecast>{
                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName, "Error loading weekly forecast")
                        }

                        override fun onResponse(call: Call<WeeklyForecast>, response: Response<WeeklyForecast>) {
                            val weeklyForecastResponse = response.body()
                            if(weeklyForecastResponse != null){
                                _weeklyForecast.value = weeklyForecastResponse
                            }
                        }

                    })
                }
            }
        })
    } // end of loadForecast

    fun loadCurrentForecast(zipcode: String){

        val call = createOpenWeatherMapService().currentWeather(zipcode, "imperial", BuildConfig.OPEN_WEATHER_MAP_API_KEY )

        call.enqueue(object : retrofit2.Callback<CurrentWeather> {
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

            override fun onResponse( call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    _currentWeather.value = weatherResponse
                }
            }

        })
    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "You planning a trip to the hawaii, yet?"
            in 0f.rangeTo(32f) -> "The commute to work could be icy!"
            in 32f.rangeTo(55f) -> "Light jacket with gloves and hat!"
            in 55f.rangeTo(65f) -> "Almost perfect!"
            in 65f.rangeTo(75f) -> "Perfection!"
            in 75f.rangeTo(85f) -> "Just over perfect!"
            in 85f.rangeTo(95f) -> "Get the shorts out!"
            in 95f.rangeTo(105f) -> "Hope you got AC!"
            in 105f.rangeTo(Float.MAX_VALUE) -> "Call out, and make margaritas!"
            else -> "Does not compute. :("
        }

    }

}