package es.unex.giiis.medicinex.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters
{
    @TypeConverter
    fun docListToJson(value : List<Doc>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToDocList(value : String?) = Gson().fromJson(value, Array<Doc>::class.java).toMutableList()

    @TypeConverter
    fun fotoListToJson(value : List<Foto>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToFotoList(value : String?) = Gson().fromJson(value, Array<Foto>::class.java).toMutableList()

    @TypeConverter
    fun principioActivoListToJson(value : List<PrincipioActivo>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToPrincipioActivoList(value : String?) = Gson().fromJson(value, Array<PrincipioActivo>::class.java).toMutableList()

    @TypeConverter
    fun excipienteListToJson(value : List<Excipiente>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToExcipienteList(value : String?) = Gson().fromJson(value, Array<Excipiente>::class.java).toMutableList()

    @TypeConverter
    fun viaAdministracionListToJson(value : List<ViaAdministracion>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToViaAdministracionList(value : String?) = Gson().fromJson(value, Array<ViaAdministracion>::class.java).toMutableList()

    @TypeConverter
    fun presentacionListToJson(value : List<Presentacion>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToPresentacionList(value : String?) = Gson().fromJson(value, Array<Presentacion>::class.java).toMutableList()

    @TypeConverter
    fun formaFarmaceuticaSimplificadaToJson(value : FormaFarmaceuticaSimplificada?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToFormaFarmaSimplificada(value : String?) = Gson().fromJson(value, FormaFarmaceuticaSimplificada::class.java)!!

    @TypeConverter
    fun formaFarmaceuticaToJson(value : FormaFarmaceutica?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToFormaFarmaceutica(value : String?) = Gson().fromJson(value, FormaFarmaceutica::class.java)!!

    @TypeConverter
    fun categorieToDb(value : Categorie?) = value!!.categorie
}