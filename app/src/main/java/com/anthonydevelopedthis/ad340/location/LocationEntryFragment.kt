package com.anthonydevelopedthis.ad340.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.anthonydevelopedthis.ad340.R

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        // update UI, get view references
        val enterButton: Button = view.findViewById(R.id.enterButton)
        val zipcodeEditText: EditText = view.findViewById(R.id.zipcodeEditText)

        enterButton.setOnClickListener {
            val enteredMsg = zipcodeEditText.text.toString()

            if (enteredMsg.length == 5) {
                findNavController().navigateUp()
            } else {
                if (enteredMsg.length < 5) {
                    // User entered to few digits
                    val finalMsg = getString(R.string.ErrorMsgFew)
                    Toast.makeText(requireContext(), finalMsg, Toast.LENGTH_SHORT).show()

                } else if (enteredMsg.length > 5) {
                    // User entered to many digits
                    val finalMsg = getString(R.string.ErrorMsgMany)
                    Toast.makeText(requireContext(), finalMsg, Toast.LENGTH_SHORT).show()
                }
            }

        } // end of enterButton

        return view
    }

}
