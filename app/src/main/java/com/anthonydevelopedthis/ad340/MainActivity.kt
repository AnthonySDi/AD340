package com.anthonydevelopedthis.ad340

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anthonydevelopedthis.ad340.details.ForecastDetailsActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)
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
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecast ->
            showForecastDetails(forecast)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        } // end of weeklyForecastObserver
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        //val inflater: MenuInflater = menuInflator
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Handle Item Selection
        return when (item.itemId) {
            R.id.tempDisplaySetting -> {
                //Toast.makeText(this, "clicked Menu Item", Toast.LENGTH_SHORT).show()
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showForecastDetails(forecast: DailyForecast){
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)
    }



}
