package es.unex.giiis.medicinex.domain

import es.unex.giiis.medicinex.data.MedicineRepository
import es.unex.giiis.medicinex.utilities.SyntaxChecker
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository : MedicineRepository)
{
    suspend operator fun invoke(email : String, password : String) : Boolean
    {
        if(SyntaxChecker.isValidMailSyntax(email) && SyntaxChecker.isValidPasswordSyntax(password))
        {
            return repository.logIn(email, password)
        }
        else return false
    }
}