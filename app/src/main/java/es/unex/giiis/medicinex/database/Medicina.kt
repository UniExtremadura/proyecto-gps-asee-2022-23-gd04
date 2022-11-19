package es.unex.giiis.medicinex.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicina
    (
    @PrimaryKey(autoGenerate = false) var nRegistro : String, // Número de registro del medicamento.
    @ColumnInfo(name = "nombre") var nombre: String?, // Nombre completo del medicamento.
    @ColumnInfo(name = "lab_titular") var labTitular: String?, // Laboratorio titular del medicamento.
    @ColumnInfo(name = "c_presc") var cPresc: String?, // Condiciones de prescripción del medicamento.
    @ColumnInfo(name = "comerc") var comerc: Boolean?, // ¿Tiene formato comercial?
    @ColumnInfo(name = "receta") var receta: Boolean?, // ¿Necesita una receta médica?
    @ColumnInfo(name = "conduc") var conduc: Boolean?, // ¿Afecta a la conducción?
    @ColumnInfo(name = "ema") var ema: Boolean?, // ¿Ha sido registrado por la EMA (European Medicines Agency)?
    @ColumnInfo(name = "dosis") var dosis: String?, // Información sobre la dosis.
    @ColumnInfo(name = "docs") var docs : MutableList<Doc>?, // Documentos informativos asociados al medicamento.
    @ColumnInfo(name = "fotos") var fotos : MutableList<Foto>?, // Fotos del medicamento.
    @ColumnInfo(name = "p_activos") var pActivos : MutableList<PrincipioActivo>?, // Lista de información de los principios activos.
    @ColumnInfo(name = "excipientes") var excipientes : MutableList<Excipiente>?, // Lista de información de excipientes.
    @ColumnInfo(name = "v_administracion") var vAdministracion : MutableList<ViaAdministracion>?, // Vías de administración del medicamento.
    @ColumnInfo(name = "presentaciones") var presentaciones : MutableList<Presentacion>?, // Presentaciones del medicamento.
    @ColumnInfo(name = "forma_farma") var formaFarma : FormaFarmaceutica?, // Forma farmacéutica.
    @ColumnInfo(name = "forma_farma_simpli") var formaFarmaSimpli : FormaFarmaceuticaSimplificada?, // Forma farmacéutica simplificada.
    @ColumnInfo(name = "cluster") var cluster : String?, // Categoría del medicamento.
    @ColumnInfo(name = "seccion") var seccion : Char?, // Letra de la sección a la que pertenece el medicamento (coincide con la primera letra del nombre del medicamento).
)
{
    override fun toString() : String
    {
        var medicineText = ""

        medicineText += "Nombre: $nombre\n\n"
        medicineText += "Número de registro: ${nRegistro}\n\n"
        medicineText += "Creado por: $labTitular\n\n"
        medicineText += "$cPresc\n\n"

        medicineText += if(comerc == true) {"Se comercializa\n\n"} else {"No se comercializa\n\n"}

        medicineText += if(receta == true) {"Necesita receta\n\n"} else {"No necesita receta\n\n"}

        medicineText += if(conduc == true) {"Afecta a la conducción\n\n"} else {"No afecta a la conducción\n\n"}

        medicineText += if(ema == true) {"Registrado por la EMA\n\n"} else {"No registrado por la EMA\n\n"}

        medicineText += "Dosis: $dosis\n\n"

        if(docs != null)
        {
            for(doc in docs!!)
            {
                if(doc.url != null) {  medicineText += "PDF: ${doc.url}\n\n" }
                if(doc.urlHtml != null) {  medicineText += "WEB con más info: ${doc.urlHtml}\n\n" }
            }
        }

        if(fotos != null)
        {
            for(foto in fotos!!)
            {
                if(foto.url != null) {  medicineText += "Foto: ${foto.url}\n\n" }
            }
        }

        if(pActivos != null)
        {
            medicineText += "Principios activos:\n\n"

            for(pActivo in pActivos!!)
            {
                //if(pActivo != null)
                medicineText += "\t- ${pActivo.nombre} : ${pActivo.cantidad} ${pActivo.unidad}\n\n"
            }
        }

        if(excipientes != null)
        {
            medicineText += "Excipientes:\n\n"

            for(excipiente in excipientes!!)
            {
                //if(excipiente != null)
                medicineText += "\t- ${excipiente.nombre} : ${excipiente.cantidad} ${excipiente.unidad}\n\n"
            }
        }

        if(vAdministracion != null)
        {
            medicineText += "Vías de adminsitración:\n\n"

            for(via in vAdministracion!!)
            {
                //if(via != null)
                medicineText += "\t- ${via.nombre}\n\n"
            }
        }

        if(presentaciones != null)
        {
            medicineText += "Presentaciones:\n\n"

            for(presentacion in presentaciones!!)
            {
                //if(presentacion != null)
                medicineText += "\t- ${presentacion.nombre}\n\n"
            }
        }

        if(formaFarma != null)
        {
            medicineText += "Forma farmacéutica: ${formaFarma!!.nombre}\n\n"
        }

        if(formaFarmaSimpli != null)
        {
            medicineText += "Forma farmacéutica simplificada: ${formaFarmaSimpli!!.nombre}\n\n"
        }

        return medicineText
    }
}