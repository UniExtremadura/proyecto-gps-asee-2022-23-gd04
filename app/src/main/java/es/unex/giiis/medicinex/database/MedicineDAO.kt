package es.unex.giiis.medicinex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings

@Dao
interface MedicineDAO
{
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT nRegistro, nombre FROM medicines WHERE seccion = :letra")
    suspend fun buscarPorLetra(letra: Char): MutableList<Medicina>

    @Query("SELECT * FROM medicines WHERE cluster = :cluster")
    suspend fun buscarPorCategoria(cluster: String): MutableList<Medicina>

    @Query("SELECT * FROM medicines WHERE nombre LIKE ''|| :nombre || '%'")
    suspend fun buscarPorNombre(nombre: String): MutableList<Medicina>?

    @Query("SELECT * FROM medicines WHERE nRegistro = :nregistro LIMIT 1")
    suspend fun buscarPorNRegistro(nregistro : String) : Medicina

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: Medicina)

    /*@Delete
    suspend fun delete(medicine: Medicina)*/

    @Query("DELETE FROM medicines WHERE nRegistro = :nregistro")
    suspend fun delete(nregistro: String)

    @Query("DELETE FROM medicines")
    suspend fun deleteAll()
}