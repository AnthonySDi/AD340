package com.anthonydevelopedthis.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterButton: Button = findViewById(R.id.enterButton)
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)

        enterButton.setOnClickListener {
            val enteredMsg = zipcodeEditText.text.toString()


            if (enteredMsg.length == 5) {
                val finalMsg = getString(R.string.enteredZipMsg, enteredMsg)
                //Toast.makeText(this, finalMsg, Toast.LENGTH_SHORT).show()
                forecastRepository.loadForecast(enteredMsg)
            } else {
                if (enteredMsg.length < 5) {
                    // User entered to few digits
                    val finalMsg = getString(R.string.ErrorMsgFew)
                    Toast.makeText(this, finalMsg, Toast.LENGTH_SHORT).show()

                } else if (enteredMsg.length > 5) {
                    // User entered to many digits
                    val finalMsg = getString(R.string.ErrorMsgMany)
                    Toast.makeText(this, finalMsg, Toast.LENGTH_SHORT).show()
                }
            }

        } // end of enterButton

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)
        val dailyForecastAdapter = DailyForecastAdapter(){forecastItem ->
            val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        } // end of weeklyForecastObserver
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

    }
}
