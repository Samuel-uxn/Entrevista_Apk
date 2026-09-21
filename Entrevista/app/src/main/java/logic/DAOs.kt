package logic

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CasoDao{
    //crear y guardar caso
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCaso(caso: Caso): Long
    //Editar caso
    @Update
    suspend fun actualizarCaso(caso: Caso)
    //Eliminar caso
    @Delete
    suspend fun eliminarCaso(caso: Caso)
    //Lista de casos
    @Query("SELECT * FROM casos ORDER BY id DESC")
    fun obtenerCasos(): Flow<List<Caso>>
    //Buscar caso por titulo o descripcion
    @Query("SELECT * FROM casos WHERE titulo LIKE '%' || :busqueda || '%' OR descripcion LIKE '%' || :busqueda || '%'")
    fun buscarCasos(busqueda: String): Flow<List<Caso>>
    // Caso especifico al darle click
    @Query("SELECT * FROM casos WHERE id = :id")
    suspend fun obtenerCasoId(id: Int): Caso?
}

@Dao
interface EntrevistaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntrevista(entrevista: Entrevista)

    @Query("SELECT * FROM entrevistas WHERE casoId = :casoId")
    fun obtenerEntrevistasPorCaso(casoId: Int): Flow<List<Entrevista>>
}

@Dao
interface EvidenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvidencia(evidencia: Evidencia)

    @Query("SELECT * FROM evidencias WHERE casoId = :casoId")
    fun obtenerEvidenciasCaso(casoId: Int): Flow<List<Evidencia>>
}