package es.unex.giiis.medicinex.database

data class PrincipioActivo
    (
    var id : Int?, // ID del principio activo (genérico).
    var codigo : String?, // Código identificativo del principio activo.
    var nombre : String?, // Nombre del principio activo.
    var cantidad : String?, // Cantidad del principio activo.
    var unidad : String?, // Unidad para la cantidad.
    var orden : Int?, // Orden en la lista de principios activos del medicamento.
)
{
    override fun toString() : String
    {
        return "$nombre: $cantidad $unidad"
    }
}
