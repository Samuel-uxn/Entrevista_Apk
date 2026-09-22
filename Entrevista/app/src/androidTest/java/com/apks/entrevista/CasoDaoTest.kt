package com.apks.entrevista

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import logic.BaseDatos
import logic.Caso
import logic.CasoDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CasoDaoTest {

    private lateinit var db: BaseDatos
    private lateinit var casoDao: CasoDao

    @Before
    fun crearBaseDatos() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BaseDatos::class.java)
            .allowMainThreadQueries()
            .build()
        casoDao = db.casoDao()
    }

    @After
    fun cerrarBaseDatos() {
        db.close()
    }

    @Test
    fun insertarYLeerCaso() {
        runBlocking {
            val caso = Caso(
                titulo = "Corrupcion en la obra publica",
                descripcion = "Desvio de fondos en la construccion del puente",
                fecha = "2026-09-21",
                estado = "En investigacion"
            )

            casoDao.insertarCaso(caso)

            val lista = casoDao.obtenerCasos().first()

            Assert.assertEquals(1, lista.size)
            Assert.assertEquals("Corrupcion en la obra publica", lista[0].titulo)
        }
    }
}