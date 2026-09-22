package logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CasoViewModel(private val casoDao: CasoDao) : ViewModel() {

    private val _casos = MutableStateFlow<List<Caso>>(emptyList())
    val casos: StateFlow<List<Caso>> = _casos.asStateFlow()

    init {
        cargarCasos()
    }

    private fun cargarCasos() {
        viewModelScope.launch {
            casoDao.obtenerCasos().collect { lista ->
                _casos.value = lista
            }
        }
    }

    fun guardarCaso(titulo: String, descripcion: String, fecha: String, estado: String) {
        viewModelScope.launch {
            val nuevoCaso = Caso(
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                estado = estado
            )
            casoDao.insertarCaso(nuevoCaso)
        }
    }

    fun buscarCaso(texto: String) {
        viewModelScope.launch {
            if (texto.isBlank()) {
                cargarCasos()
            } else {
                casoDao.buscarCasos(texto).collect { resultados ->
                    _casos.value = resultados
                }
            }
        }
    }
}