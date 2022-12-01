package es.unex.giiis.medicinex.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class MedicinaEntity
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