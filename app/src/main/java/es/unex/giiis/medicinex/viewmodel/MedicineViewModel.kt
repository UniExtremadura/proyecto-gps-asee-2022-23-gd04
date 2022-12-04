package es.unex.giiis.medicinex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.unex.giiis.medicinex.data.model.MedicineModel
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor
(

): ViewModel()
{
    val medicineModel = MutableLiveData<MedicineModel>()
}