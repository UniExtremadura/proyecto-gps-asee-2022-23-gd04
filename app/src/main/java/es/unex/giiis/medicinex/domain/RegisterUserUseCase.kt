package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.data.MedicineRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(account : Account) : Boolean
    {
        return repository.createProfile(account)
    }
}