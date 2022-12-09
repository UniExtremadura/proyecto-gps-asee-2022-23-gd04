package es.unex.giiis.medicinex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.unex.giiis.medicinex.data.database.MedicinexDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloRoom
{
    private const val MEDICINE_DB_NAME = "medicinex_db"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context : Context) = Room.databaseBuilder(context, MedicinexDB::class.java, MEDICINE_DB_NAME).build()


    @Singleton
    @Provides
    fun provideMedicineDao(dataBase : MedicinexDB) = dataBase.medicineDao()

    @Singleton
    @Provides
    fun provideLetterDao(dataBase : MedicinexDB) = dataBase.letterDao()

    @Singleton
    @Provides
    fun provideCategorieDao(dataBase : MedicinexDB) = dataBase.sectionDao()
}