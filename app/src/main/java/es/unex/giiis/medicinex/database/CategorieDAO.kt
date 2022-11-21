package es.unex.giiis.medicinex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategorieDAO
{
    @Query("SELECT * FROM categories WHERE categorie = :categorie")
    suspend fun getCategorie(categorie: Categorie) : Categorie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategorie(categorie : Categorie)
}