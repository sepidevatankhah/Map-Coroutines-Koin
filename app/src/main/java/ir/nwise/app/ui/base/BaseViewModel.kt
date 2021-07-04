package ir.nwise.app.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<ViewState> : ViewModel() {
    val liveData = MutableLiveData<ViewState>()
}
