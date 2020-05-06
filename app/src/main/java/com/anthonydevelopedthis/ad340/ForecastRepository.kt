package com.anthonydevelopedthis.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random


class ForecastRepository {
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String){
        val randomValues = List(7){ Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp, getTempDescription(temp))
        }

        _weeklyForecast.setValue(forecastItems)

    } // end of loadForecast

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