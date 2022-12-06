package es.unex.giiis.medicinex.data

import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.data.database.dao.CategorieDAO
import es.unex.giiis.medicinex.data.database.dao.LetterDAO
import es.unex.giiis.medicinex.data.database.dao.MedicineDAO
import es.unex.giiis.medicinex.data.database.entities.CategorieEntity
import es.unex.giiis.medicinex.data.database.entities.LetterEntity
import es.unex.giiis.medicinex.data.database.entities.MedicinaEntity
import es.unex.giiis.medicinex.data.database.entities.toModel
import es.unex.giiis.medicinex.data.model.MedicineModel
import es.unex.giiis.medicinex.data.network.FirebaseAuthenticationService
import es.unex.giiis.medicinex.data.network.MedicineFBService
import javax.inject.Inject

class MedicineRepository @Inject constructor
(
    private val medicineDao : MedicineDAO,
    private val letterDao : LetterDAO,
    private val categorieDao : CategorieDAO,
    private val firebase : MedicineFBService,
    private val auth : FirebaseAuthenticationService
)
{
    suspend fun getMedicineByNRegister(nRegister : String) : MedicineModel?
    {
        return medicineDao.buscarPorNRegistro(nRegister)?.toModel()
    }

    suspend fun getMedicinesByQuery(query : String) : MutableList<MedicineModel>
    {
        val medicines: MutableList<MedicineModel>

        val letter = query[0]
        val letra = letterDao.getLetter(letter)

        if(letra != null)
        {// La letra existía, por tanto, todas las medicinas que empiecen por dicha letra están en local.
            medicines = entitiesToModels(medicineDao.buscarPorNombre(query))
        }
        else
        {// La letra no existía, por tanto, hay que descargar todas las medicinas que comiencen por dicha letra de Firebase.

            val results = firebase.getMedicinesByLetter(letter)
            medicineDao.insert(results.map { MedicinaEntity(nRegistro = it.nRegistro, nombre = it.nombre, labTitular = it.labTitular, cPresc = it.cPresc, comerc = it.comerc, receta = it.receta,
                conduc = it.conduc, ema = it.ema, dosis = it.dosis, docs = it.docs, fotos = it.fotos, pActivos = it.pActivos, excipientes = it.excipientes, vAdministracion = it.vAdministracion,
                presentaciones = it.presentaciones, formaFarma = it.formaFarma, formaFarmaSimpli = it.formaFarmaSimpli, cluster = it.cluster, seccion = it.seccion) }.toMutableList())

            medicines = results.filter { it.nombre!!.startsWith(query, true) } as MutableList<MedicineModel>
            letterDao.insertLetter(LetterEntity(letter))
        }

        return medicines
    }

    suspend fun getMedicinesBySection(categorie : CategorieEntity) : MutableList<MedicineModel>
    {
        var medicines : MutableList<MedicineModel> = mutableListOf()

        val section = categorieDao.getCategorie(categorie)

        if(section != null)
        {// La sección existía, por tanto, todas las medicinas que pertenezcan a dicha sección están en local.
            medicines = entitiesToModels(medicineDao.buscarPorCategoria(section.categorie))
        }
        else
        {// La sección no existía, por tanto, hay que descargar todas las medicinas que pertenezcan a dicha sección de Firebase.

            medicines = firebase.getCategorieMedicines(categorie.categorie)
            medicineDao.insert(medicines.map { MedicinaEntity(nRegistro = it.nRegistro, nombre = it.nombre, labTitular = it.labTitular, cPresc = it.cPresc, comerc = it.comerc, receta = it.receta,
                conduc = it.conduc, ema = it.ema, dosis = it.dosis, docs = it.docs, fotos = it.fotos, pActivos = it.pActivos, excipientes = it.excipientes, vAdministracion = it.vAdministracion,
                presentaciones = it.presentaciones, formaFarma = it.formaFarma, formaFarmaSimpli = it.formaFarmaSimpli, cluster = it.cluster, seccion = it.seccion) }.toMutableList())

            categorieDao.insertCategorie(categorie)
        }

        return medicines
    }

    suspend fun getMedicineByName(name : String) : MedicineModel?
    {
        var medicine : MedicineModel? = null

        medicine = medicineDao.buscarPorNombre(name)?.first()?.toModel()

        if(medicine == null)
        {
            medicine = firebase.getMedicineByName(name)
        }

        return medicine
    }

    suspend fun editProfile(account : Account) : Boolean
    {
        var success = false

        if(auth.updatePassword(account.password))
        {
            firebase.editAccount(account)
            success = true
        }

        return success
    }

    suspend fun createProfile(account : Account) : Boolean
    {
        var success = false

        if(auth.createAccount(account.email, account.password))
        {
            firebase.registerAccount(account)
            success = true
        }

        return success
    }

    suspend fun logIn(email : String, password : String) : Boolean
    {
        var success = false

        if(auth.signIn(email, password))
        {
            success = true
        }

        return success
    }

    suspend fun logOut()
    {
        auth.logOut()
    }

    suspend fun sendResetPasswordMail(email : String) : Boolean
    {
        var success = false

        if(auth.resetAccountPassword(email))
        {
            success = true
        }

        return success
    }

    private fun entitiesToModels(medicines : MutableList<MedicinaEntity>?) : MutableList<MedicineModel> = medicines!!.map {
         MedicineModel(nRegistro = it.nRegistro, nombre = it.nombre, labTitular = it.labTitular, cPresc = it.cPresc, comerc = it.comerc, receta = it.receta,
             conduc = it.conduc, ema = it.ema, dosis = it.dosis, docs = it.docs, fotos = it.fotos, pActivos = it.pActivos, excipientes = it.excipientes, vAdministracion = it.vAdministracion,
             presentaciones = it.presentaciones, formaFarma = it.formaFarma, formaFarmaSimpli = it.formaFarmaSimpli, cluster = it.cluster, seccion = it.seccion)
    }.toMutableList()
}