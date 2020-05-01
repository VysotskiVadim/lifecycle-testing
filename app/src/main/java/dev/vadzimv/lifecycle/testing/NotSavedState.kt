package dev.vadzimv.lifecycle.testing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_not_saved_state.*

class NotSavedState : Fragment(R.layout.fragment_not_saved_state) {

    var counter = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        counterButton.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }
    }
}