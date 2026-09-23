package com.apks.entrevista


import kotlinx.coroutines.test.runTest
import logic.Caso
import logic.DetalleCasoViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetalleCasoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `agregarEntrevista guarda la entrevista para el caso`() = runTest {
        val casoDao = FakeCasoDao()
        val entrevistaDao = FakeEntrevistaDao()
        val evidenciaDao = FakeEvidenciaDao()

        val casoId = casoDao.insertarCaso(
            Caso(
                titulo = "Caso 1",
                descripcion = "desc",
                fecha = "2026-09-01",
                estado = "En investigacion"
            )
        ).toInt()

        val viewModel = DetalleCasoViewModel(casoDao, entrevistaDao, evidenciaDao)
        viewModel.cargarDetallesDelCaso(casoId)

        viewModel.agregarEntrevista(
            casoId = casoId,
            nombre = "Ana Torres",
            rol = "Testigo",
            fecha = "2026-09-02",
            hallazgos = "Vio el vehiculo"
        )

        assertEquals(1, viewModel.entrevistas.value.size)
        assertEquals("Ana Torres", viewModel.entrevistas.value[0].nombre)
    }

    @Test
    fun `guardarConclusionYCerrar actualiza estado y conclusion`() = runTest {
        val casoDao = FakeCasoDao()
        val entrevistaDao = FakeEntrevistaDao()
        val evidenciaDao = FakeEvidenciaDao()

        val casoId = casoDao.insertarCaso(
            Caso(titulo = "Caso 1", descripcion = "desc", fecha = "2026-09-01", estado = "En investigacion")
        ).toInt()

        val viewModel = DetalleCasoViewModel(casoDao, entrevistaDao, evidenciaDao)
        viewModel.cargarDetallesDelCaso(casoId)

        viewModel.guardarConclusionYCerrar(casoId, "Se confirmo el desvio de fondos")

        val caso = viewModel.casoActual.value
        assertEquals("Cerrada", caso?.estado)
        assertEquals("Se confirmo el desvio de fondos", caso?.conclusion)
    }

    @Test
    fun `cambiarEstado actualiza el estado del caso`() = runTest {
        val casoDao = FakeCasoDao()
        val entrevistaDao = FakeEntrevistaDao()
        val evidenciaDao = FakeEvidenciaDao()

        val casoId = casoDao.insertarCaso(
            Caso(titulo = "Caso 1", descripcion = "desc", fecha = "2026-09-01", estado = "En investigacion")
        ).toInt()

        val viewModel = DetalleCasoViewModel(casoDao, entrevistaDao, evidenciaDao)
        viewModel.cargarDetallesDelCaso(casoId)

        viewModel.cambiarEstado(casoId, "Publicado")

        assertEquals("Publicado", viewModel.casoActual.value?.estado)
    }
}