package com.anthonydevelopedthis.ad340

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String{
    // return String.format("%.2f°", temp)
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f F°", temp)
        TempDisplaySetting.Celsius -> {
            val temp = (temp - 32f) * (5f/9f)
            String.format("%.2f C°", temp)
        }
    }
}


fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager){
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Select temperature unit to use")
        .setPositiveButton("F°") {_, _ ->
            // Toast.makeText(this, "Fahrenheit Selected", Toast.LENGTH_SHORT).show()
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C°") {_, _ ->
            // Toast.makeText(this, "Celsius Selected", Toast.LENGTH_SHORT).show()
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener{
            Toast.makeText(context, "Changes take affect on app restart", Toast.LENGTH_SHORT).show()
        }
    dialogBuilder.show()
}