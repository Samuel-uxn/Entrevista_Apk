package logic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Caso::class, Entrevista::class, Evidencia::class],
    version = 1,
    exportSchema = false
)
abstract class BaseDatos : RoomDatabase() {

    abstract fun casoDao(): CasoDao
    abstract fun entrevistaDao(): EntrevistaDao
    abstract fun evidenciaDao(): EvidenciaDao

    companion object {
        @Volatile
        private var INSTANCE: BaseDatos? = null

        fun obtenerBaseDatos(context: Context): BaseDatos {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "investigacion"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}