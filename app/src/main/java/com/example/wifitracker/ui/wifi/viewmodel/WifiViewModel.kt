package com.example.wifitracker.ui.wifi.viewmodel

import android.net.wifi.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WifiViewModel : ViewModel() {

    private val _wifiNetworks = MutableStateFlow<List<ScanResult>>(emptyList())
    val wifiNetwork: StateFlow<List<ScanResult>> = _wifiNetworks

    private val _ssid = MutableLiveData<String>()
    val ssid: LiveData<String> = _ssid

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?> = _password

    private val _switchScanner = MutableLiveData<Boolean>()
    val switchScanner: LiveData<Boolean> = _switchScanner


    fun getNameWifi(ssid: String) {
        _ssid.postValue(ssid)
    }


    fun changedPassword(password: String?) {
        _password.postValue(password)
    }

    fun returnSSID(ssid: String): ScanResult? {
        return _wifiNetworks.value.find { it.SSID == ssid }
    }

    fun changedSwitchState(state: Boolean) {
        _switchScanner.postValue(state)
    }

    fun changeScan(observer: List<ScanResult>) {
        viewModelScope.launch {
            if (_switchScanner.value == true) {
                _wifiNetworks.value = emptyList()
            } else {
                delay(3000)
                _wifiNetworks.value = observer
            }
        }
    }

}