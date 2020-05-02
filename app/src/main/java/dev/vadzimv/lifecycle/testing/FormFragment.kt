package dev.vadzimv.lifecycle.testing

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
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
        viewModel.progressVisible.observe(viewLifecycleOwner) {
            if (it) {
                progressBar.visibility = View.VISIBLE
                nameLayout.visibility = View.GONE
                ageConfirmation.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                nameLayout.visibility = View.VISIBLE
                ageConfirmation.visibility = View.VISIBLE
            }
        }
    }
}

class FormViewModel : ViewModel() {

    val name = MutableLiveData("")

    private val _progressVisible = MutableLiveData<Boolean>(false)
    val progressVisible: LiveData<Boolean> get() = _progressVisible

    init {
        viewModelScope.launch {
            _progressVisible.value = true
            delay(500)
            name.value = "test value from server"
            _progressVisible.value = false
        }
    }
}

fun EditText.bindLiveData(
    mutableLiveData: MutableLiveData<String>,
    lifecycleOwner: LifecycleOwner
) {
    mutableLiveData.observe(lifecycleOwner) {
        Log.d("binding", "live data changed to $it")
        if (it != this.text.toString()) {
            this.setText(it)
        }
    }
    this.doAfterTextChanged { editable ->
        val newValue = editable.toString()
        Log.d("binding", "edit text changed to $newValue")
        if (newValue != mutableLiveData.value) {
            mutableLiveData.value = newValue
        }
    }
}