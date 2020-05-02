package dev.vadzimv.lifecycle.testing

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.text_and_button.*

private const val USER_CHOICE_KEY = "user-choice"

class DialogExample : Fragment(R.layout.text_and_button) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.text = "Choose a pill"
        val text = text
        if (savedInstanceState != null) {
            text.text = savedInstanceState.getString(USER_CHOICE_KEY)
        }
        button.setOnClickListener {
            WrongDialogFragment()
                .apply { callback = { text.text = it } }
                .show(childFragmentManager, null)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(USER_CHOICE_KEY, text.text.toString())
    }
}

class WrongDialogFragment : DialogFragment() {

    var callback: (String) -> Unit = { }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Red or Blue pill?")
            .setPositiveButton("Red") { _, _ -> callback("RED") }
            .setNegativeButton("Blue") { _, _ -> callback("BLUE") }
            .create()
    }
}