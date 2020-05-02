package dev.vadzimv.lifecycle.testing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.text_and_button.*

class NotSavedState : Fragment(R.layout.text_and_button) {

    var counter = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = "+1"
        button.setOnClickListener {
            counter++
            text.text = counter.toString()
        }
    }
}