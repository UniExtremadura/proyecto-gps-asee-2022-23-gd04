package es.unex.giiis.medicinex.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "letters")
data class LetterEntity
(
    @PrimaryKey(autoGenerate = false) var letter : Char, // Letra.
)