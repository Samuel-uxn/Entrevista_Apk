package com.apks.entrevista

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import logic.Caso
import logic.CasoViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class CasoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `guardarCaso agrega un caso a la lista`() = runTest {
        val dao = FakeCasoDao()
        val viewModel = CasoViewModel(dao)

        viewModel.guardarCaso(
            titulo = "Caso de prueba",
            descripcion = "Descripcion de prueba",
            fecha = "2026-09-22",
            estado = "En investigacion"
        )

        val casos = viewModel.casos.value
        assertEquals(1, casos.size)
        assertEquals("Caso de prueba", casos[0].titulo)
    }

    @Test
    fun `buscarCaso con texto vacio recarga todos los casos`() = runTest {
        val dao = FakeCasoDao()
        dao.insertarCaso(Caso(titulo = "Uno", descripcion = "A", fecha = "2026-01-01", estado = "En investigacion"))
        dao.insertarCaso(
            Caso(
                titulo = "Dos",
                descripcion = "B",
                fecha = "2026-01-02",
                estado = "En investigacion"
            )
        )

        val viewModel = CasoViewModel(dao)

        viewModel.buscarCaso("Uno")
        assertEquals(1, viewModel.casos.value.size)

        viewModel.buscarCaso("")
        assertEquals(2, viewModel.casos.value.size)
    }
}