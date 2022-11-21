package es.unex.giiis.medicinex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "letters")
data class Letter
    (
    @PrimaryKey(autoGenerate = false) var letter : Char, // Letra.
)