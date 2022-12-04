package es.unex.giiis.medicinex.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule
{
    @Singleton
    @Provides
    fun provideFireRealtime() : FirebaseDatabase
    {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideFireAuth() : FirebaseAuth
    {
        return FirebaseAuth.getInstance()
    }
}