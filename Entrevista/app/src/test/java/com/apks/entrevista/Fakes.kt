package com.apks.entrevista

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import logic.Caso
import logic.CasoDao
import logic.Entrevista
import logic.EntrevistaDao
import logic.Evidencia
import logic.EvidenciaDao

class FakeCasoDao : CasoDao {
    private val casos = mutableListOf<Caso>()
    private val flujo = MutableStateFlow<List<Caso>>(emptyList())
    private var siguienteId = 1

    override suspend fun insertarCaso(caso: Caso): Long {
        val nuevo = caso.copy(id = siguienteId++)
        casos.add(nuevo)
        flujo.value = casos.toList()
        return nuevo.id.toLong()
    }

    override suspend fun actualizarCaso(caso: Caso) {
        val index = casos.indexOfFirst { it.id == caso.id }
        if (index != -1) {
            casos[index] = caso
            flujo.value = casos.toList()
        }
    }

    override suspend fun eliminarCaso(caso: Caso) {
        casos.removeAll { it.id == caso.id }
        flujo.value = casos.toList()
    }

    override fun obtenerCasos(): Flow<List<Caso>> = flujo

    override fun buscarCasos(busqueda: String): Flow<List<Caso>> {
        val filtrados = casos.filter {
            it.titulo.contains(busqueda, ignoreCase = true) ||
                    it.descripcion.contains(busqueda, ignoreCase = true)
        }
        return MutableStateFlow(filtrados)
    }

    override suspend fun obtenerCasoId(id: Int): Caso? = casos.find { it.id == id }
}

class FakeEntrevistaDao : EntrevistaDao {
    private val entrevistas = mutableListOf<Entrevista>()
    private val flujo = MutableStateFlow<List<Entrevista>>(emptyList())

    override suspend fun insertEntrevista(entrevista: Entrevista) {
        entrevistas.add(entrevista)
        flujo.value = entrevistas.filter { it.casoId == entrevista.casoId }
    }

    override fun obtenerEntrevistasPorCaso(casoId: Int): Flow<List<Entrevista>> {
        flujo.value = entrevistas.filter { it.casoId == casoId }
        return flujo
    }
}

class FakeEvidenciaDao : EvidenciaDao {
    private val evidencias = mutableListOf<Evidencia>()
    private val flujo = MutableStateFlow<List<Evidencia>>(emptyList())

    override suspend fun insertarEvidencia(evidencia: Evidencia) {
        evidencias.add(evidencia)
        flujo.value = evidencias.filter { it.casoId == evidencia.casoId }
    }

    override fun obtenerEvidenciasCaso(casoId: Int): Flow<List<Evidencia>> {
        flujo.value = evidencias.filter { it.casoId == casoId }
        return flujo
    }
}