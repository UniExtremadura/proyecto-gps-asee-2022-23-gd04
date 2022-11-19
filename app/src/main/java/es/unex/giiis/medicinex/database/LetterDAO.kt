package es.unex.giiis.medicinex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LetterDAO
{
    @Query("SELECT * FROM letters WHERE letter = :letter")
    suspend fun getLetter(letter : Char) : Letter?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLetter(letter: Letter)
}