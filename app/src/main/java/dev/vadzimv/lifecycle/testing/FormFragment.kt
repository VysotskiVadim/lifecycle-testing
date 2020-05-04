package dev.vadzimv.lifecycle.testing

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.fragment_form.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FormFragment : Fragment(R.layout.fragment_form) {

    private val viewModel: FormViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        name.bindLiveData(viewModel.name, viewLifecycleOwner)
        ageConfirmation.bindLiveData(viewModel.ageConfirmed, viewLifecycleOwner)
        viewModel.progressVisible.observe(viewLifecycleOwner) { progressVisible ->
            if (progressVisible) {
                progressBar.visibility = View.VISIBLE
                nameLayout.visibility = View.GONE
                ageConfirmation.visibility = View.GONE
                submitButton.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                nameLayout.visibility = View.VISIBLE
                ageConfirmation.visibility = View.VISIBLE
                submitButton.visibility = View.VISIBLE
            }
        }
    }
}

class FormViewModel : ViewModel() {

    val ageConfirmed = MutableLiveData<Boolean>(false)
    val name = MutableLiveData("")

    private val _progressVisible = MutableLiveData<Boolean>(false)
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    init {
        viewModelScope.launch {
            _progressVisible.value = true
            delay(900) // emulate network request
            name.value = "test value from server"
            ageConfirmed.value = false
            _progressVisible.value = false
        }
    }
}

fun SwitchCompat.bindLiveData(
    mutableLiveData: MutableLiveData<Boolean>,
    lifecycleOwner: LifecycleOwner
) {
    mutableLiveData.observe(lifecycleOwner) {
        if (it != this.isChecked) {
            isChecked = it
        }
    }
    this.setOnCheckedChangeListener { _, isChecked ->
        if (mutableLiveData.value != isChecked) {
            mutableLiveData.value = isChecked
        }
    }
}

fun EditText.bindLiveData(
    mutableLiveData: MutableLiveData<String>,
    lifecycleOwner: LifecycleOwner
) {
    mutableLiveData.observe(lifecycleOwner) {
        if (it != this.text.toString()) {
            this.setText(it)
        }
    }
    this.doAfterTextChanged { editable ->
        val newValue = editable.toString()
        if (newValue != mutableLiveData.value) {
            mutableLiveData.value = newValue
        }
    }
}