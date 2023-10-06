package com.zukka.ruler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    fun setUm(isChecked: Boolean) {
        _umLiveData.postValue(isChecked)
    }

    private var _umLiveData = MutableLiveData<Boolean>()
    val umLiveData: LiveData<Boolean>
        get() = _umLiveData

}