package es.unex.giiis.medicinex.database

import androidx.fragment.app.FragmentActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Medicina::class, Letter::class, Categorie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MedicinexDB : RoomDatabase()
{
    abstract fun medicineDao() : MedicineDAO
    abstract fun letterDao() : LetterDAO
    abstract fun sectionDao() : CategorieDAO

    companion object
    {// Patrón Singleton aplicado.
        @Volatile
        private var INSTANCIA : MedicinexDB? = null

        fun getDatabase(context: FragmentActivity?) : MedicinexDB
        {
            val instanciaTemporal = INSTANCIA

            if(instanciaTemporal != null)
            {
                return instanciaTemporal
            }

            synchronized(this)
            {
                val instancia = Room.databaseBuilder(context!!.applicationContext, MedicinexDB::class.java, "medicinex_db").build()
                INSTANCIA = instancia
                return instancia
            }
        }
    }
}
