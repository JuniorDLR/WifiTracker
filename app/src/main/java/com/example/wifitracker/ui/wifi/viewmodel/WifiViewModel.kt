package com.example.wifitracker.ui.wifi.viewmodel

import android.net.wifi.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.pow


class WifiViewModel : ViewModel() {

    private val _wifiNetworks = MutableStateFlow<List<ScanResult>>(emptyList())
    val wifiNetwork: StateFlow<List<ScanResult>> = _wifiNetworks

    private val _ssid = MutableLiveData<String>()
    val ssid: LiveData<String> = _ssid

    private val _password = MutableLiveData<String?>()
    val password: LiveData<String?> = _password

    private val _switchScanner = MutableLiveData<Boolean>()
    val switchScanner: LiveData<Boolean> = _switchScanner

    private val _isFoundPassword = MutableLiveData<Boolean>()
    val isFoundPassword: LiveData<Boolean> = _isFoundPassword


    private val _frequency = MutableStateFlow<Double?>(null)
    val frequency: StateFlow<Double?> = _frequency


    fun calcularFrequencia(scanResult: ScanResult) {
        val frequency = calcularEstimado(scanResult)
        _frequency.value = frequency
    }

    private fun calcularEstimado(scanResult: ScanResult): Double {
        val frequency = scanResult.frequency.toDouble()
        val level = scanResult.level.toDouble()
        val exp =(27.55 -(20* log10(frequency)) + abs(level)) / 20.0
        return 10.0.pow(exp)
    }

    fun getNameWifi(ssid: String) {
        _ssid.postValue(ssid)
    }


    fun foundPassword(isFound: Boolean) {
        _isFoundPassword.postValue(isFound)
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

                _wifiNetworks.value = observer
            }
        }
    }

}