package org.damc.pruebasemana9.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.damc.pruebasemana9.services.AuthManager
import org.damc.pruebasemana9.services.Faculty
import org.damc.pruebasemana9.services.InternacionalizacionInstance

class FacultyViewModel: ViewModel() {
    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState
    private val _facultyList = MutableStateFlow<List<Faculty>>(emptyList())
    val facultyList: StateFlow<List<Faculty>> = _facultyList

    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage

    init {
        viewModelScope.launch {
            try {
                _loginState.value = AuthManager.performAutoLogin()
                if (_loginState.value == true) {
                    loadFaculties()
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error de conexión: ${e.message}"
                _loginState.value = false
            }
        }
    }

    fun loadFaculties() {
        viewModelScope.launch {
            try {
                val response = InternacionalizacionInstance.api.getAllFaculties()
                if (response.status == "success") {
                    _facultyList.value = response.data
                    _toastMessage.value = "Datos cargados: ${response.data?.size ?: 0} facultades"
                } else {
                    _toastMessage.value = response.message ?: "Error al cargar facultades"
                }
            } catch (e: Exception) {
                _toastMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = ""
    }
}