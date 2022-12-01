package es.unex.giiis.medicinex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.unex.giiis.medicinex.data.model.MedicineModel

class MedicineViewModel : ViewModel()
{
    val medicineModel = MutableLiveData<MedicineModel>()
}