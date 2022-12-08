package es.unex.giiis.medicinex.domain

import android.content.SharedPreferences
import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.MedicinexApp
import es.unex.giiis.medicinex.Preference
import es.unex.giiis.medicinex.data.MedicineRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AutoLoginUseCaseTest
{
    lateinit var app: MedicinexApp.Companion

    @RelaxedMockK
    private lateinit var prefs : Preference

    lateinit var autoLoginUseCase: AutoLoginUseCase

    @Before
    fun onBefore()
    {
        MockKAnnotations.init(this)
        app = MedicinexApp.Companion
        autoLoginUseCase = AutoLoginUseCase()
        app. preferences = prefs
    }

    @Test
    fun `turning on autologin test`() = runBlocking {
        // Given
        coEvery { app.preferences.setAutoFill(any()) } returns Unit
        coEvery { app.preferences.setEmail(any()) } returns Unit
        coEvery { app.preferences.setPassword(any()) } returns Unit

        // When
        val account = Account("josejose", "josejose@gmail.com", "jose1234", "josefino", mutableListOf("Dalsy", "Apiretal", "Omeprazol"))

        val response = autoLoginUseCase(true, account)

        // Then
        coVerify(exactly = 0) { app.preferences.setAutoFill(false) }
        coVerify(exactly = 1) { app.preferences.setAutoFill(true) }
        coVerify(exactly = 1) { app.preferences.setEmail(account.email) }
        coVerify(exactly = 1) { app.preferences.setPassword(account.password) }

        assertTrue(response == Unit)
    }

    @Test
    fun `turning off autologin test`() = runBlocking {
        // Given
        coEvery { app.preferences.setAutoFill(any()) } returns Unit
        coEvery { app.preferences.setEmail(any()) } returns Unit
        coEvery { app.preferences.setPassword(any()) } returns Unit

        // When
        val account = Account("josejose", "josejose@gmail.com", "jose1234", "josefino", mutableListOf("Dalsy", "Apiretal", "Omeprazol"))

        val response = autoLoginUseCase(false, account)

        // Then
        coVerify(exactly = 1) { app.preferences.setAutoFill(false) }
        coVerify(exactly = 0) { app.preferences.setAutoFill(true) }
        coVerify(exactly = 0) { app.preferences.setEmail(account.email) }
        coVerify(exactly = 0) { app.preferences.setPassword(account.password) }

        assertTrue(response == Unit)
    }
}