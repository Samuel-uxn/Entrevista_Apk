package logic

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "casos")
data class Caso(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val estado: String,
    val conclusion: String? = null
)

@Entity(
    tableName = "entrevistas",
    foreignKeys = [ForeignKey(
        entity = Caso::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("casoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Entrevista(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val casoId: Int,
    val nombre: String,
    val rol: String,
    val fecha: String,
    val hallazgos: String? = null
)
@Entity(
    tableName = "evidencias",
    foreignKeys = [
        ForeignKey(
            entity = Caso::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("casoId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Evidencia(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val casoId: Int,
    val nombreArchivo: String,
    val tipo: String,
    val tamano: String
)