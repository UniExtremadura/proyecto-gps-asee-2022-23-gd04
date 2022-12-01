package es.unex.giiis.medicinex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.giiis.medicinex.data.database.entities.LetterEntity

@Dao
interface LetterDAO
{
    @Query("SELECT * FROM letters WHERE letter = :letter")
    suspend fun getLetter(letter : Char) : LetterEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLetter(letterEntity: LetterEntity)
}