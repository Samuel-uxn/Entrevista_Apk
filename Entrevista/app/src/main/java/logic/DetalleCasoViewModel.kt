package logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleCasoViewModel(
    private val casoDao: CasoDao,
    private val entrevistaDao: EntrevistaDao,
    private val evidenciaDao: EvidenciaDao
) : ViewModel() {

    private val _casoActual = MutableStateFlow<Caso?>(null)
    val casoActual: StateFlow<Caso?> = _casoActual.asStateFlow()

    private val _entrevistas = MutableStateFlow<List<Entrevista>>(emptyList())
    val entrevistas: StateFlow<List<Entrevista>> = _entrevistas.asStateFlow()

    private val _evidencias = MutableStateFlow<List<Evidencia>>(emptyList())
    val evidencias: StateFlow<List<Evidencia>> = _evidencias.asStateFlow()

    // 1. Cargar toda la información de un caso específico al abrir la pantalla
    fun cargarDetallesDelCaso(casoId: Int) {
        viewModelScope.launch {
            _casoActual.value = casoDao.obtenerCasoId(casoId)
        }

        viewModelScope.launch {
            entrevistaDao.obtenerEntrevistasPorCaso(casoId).collect { lista ->
                _entrevistas.value = lista
            }
        }

        viewModelScope.launch {
            evidenciaDao.obtenerEvidenciasCaso(casoId).collect { lista ->
                _evidencias.value = lista
            }
        }
    }

    // 2. Guardar una nueva entrevista
    fun agregarEntrevista(casoId: Int, nombre: String, rol: String, fecha: String, hallazgos: String) {
        viewModelScope.launch {
            val nuevaEntrevista = Entrevista(
                casoId = casoId,
                nombre = nombre,
                rol = rol,
                fecha = fecha,
                hallazgos = hallazgos
            )
            entrevistaDao.insertEntrevista(nuevaEntrevista)
        }
    }

    // 3. Guardar una nueva evidencia (archivos)
    fun agregarEvidencia(casoId: Int, nombreArchivo: String, tipo: String, tamano: String) {
        viewModelScope.launch {
            val nuevaEvidencia = Evidencia(
                casoId = casoId,
                nombreArchivo = nombreArchivo,
                tipo = tipo,
                tamano = tamano
            )
            evidenciaDao.insertarEvidencia(nuevaEvidencia)
        }
    }

    // 4. Actualizar la conclusión y cerrar el caso
    fun guardarConclusionYCerrar(casoId: Int, nuevaConclusion: String) {
        viewModelScope.launch {
            val caso = casoDao.obtenerCasoId(casoId)
            if (caso != null) {
                val casoActualizado = caso.copy(
                    conclusion = nuevaConclusion,
                    estado = "Cerrada" // Cambia el estado automáticamente como pide el negocio
                )
                casoDao.actualizarCaso(casoActualizado)
                _casoActual.value = casoActualizado // Actualiza la pantalla
            }
        }
    }
}