package es.unex.giiis.medicinex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.unex.giiis.medicinex.data.database.dao.CategorieDAO
import es.unex.giiis.medicinex.data.database.dao.LetterDAO
import es.unex.giiis.medicinex.data.database.dao.MedicineDAO
import es.unex.giiis.medicinex.data.database.entities.CategorieEntity
import es.unex.giiis.medicinex.data.database.entities.LetterEntity
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity

@Database(entities = [MedicinaEntity::class, LetterEntity::class, CategorieEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MedicinexDB : RoomDatabase()
{
    abstract fun medicineDao() : MedicineDAO
    abstract fun letterDao() : LetterDAO
    abstract fun sectionDao() : CategorieDAO
}
