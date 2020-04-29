package com.anthonydevelopedthis.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterButton: Button = findViewById(R.id.enterButton)
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)

        enterButton.setOnClickListener {
            val enteredMsg = zipcodeEditText.text.toString()


            if (enteredMsg.length == 5) {
                val finalMsg = getString(R.string.enteredZipMsg, enteredMsg)
                Toast.makeText(this, finalMsg, Toast.LENGTH_SHORT).show()
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

        }
    }
}
