package es.unex.giiis.medicinex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.giiis.medicinex.data.database.entities.CategorieEntity

@Dao
interface CategorieDAO
{
    @Query("SELECT * FROM categories WHERE categorie = :categorieEntity")
    suspend fun getCategorie(categorieEntity: CategorieEntity) : CategorieEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategorie(categorieEntity : CategorieEntity)
}