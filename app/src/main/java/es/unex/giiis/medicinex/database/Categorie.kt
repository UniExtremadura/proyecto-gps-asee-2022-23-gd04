package es.unex.giiis.medicinex.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Categorie
    (
    @PrimaryKey(autoGenerate = false) var categorie : String, // Nombre de la categoría.
)