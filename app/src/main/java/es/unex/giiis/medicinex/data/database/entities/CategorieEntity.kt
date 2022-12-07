package es.unex.giiis.medicinex.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategorieEntity
(
    @PrimaryKey(autoGenerate = false) var categorie : String, // Nombre de la categoría.
)