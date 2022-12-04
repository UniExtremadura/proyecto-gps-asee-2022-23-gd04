package es.unex.giiis.medicinex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity

@Dao
interface MedicineDAO
{
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT nRegistro, nombre FROM medicines WHERE seccion = :letra")
    suspend fun buscarPorLetra(letra: Char): MutableList<MedicinaEntity>

    @Query("SELECT * FROM medicines WHERE cluster = :cluster")
    suspend fun buscarPorCategoria(cluster: String): MutableList<MedicinaEntity>

    @Query("SELECT * FROM medicines WHERE nombre LIKE ''|| :nombre || '%'")
    suspend fun buscarPorNombre(nombre: String): MutableList<MedicinaEntity>?

    @Query("SELECT * FROM medicines WHERE nRegistro = :nregistro LIMIT 1")
    suspend fun buscarPorNRegistro(nregistro : String) : MedicinaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicine: MedicinaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicines: MutableList<MedicinaEntity>)

    /*@Delete
    suspend fun delete(medicine: Medicina)*/

    @Query("DELETE FROM medicines WHERE nRegistro = :nregistro")
    suspend fun delete(nregistro: String)

    @Query("DELETE FROM medicines")
    suspend fun deleteAll()
}