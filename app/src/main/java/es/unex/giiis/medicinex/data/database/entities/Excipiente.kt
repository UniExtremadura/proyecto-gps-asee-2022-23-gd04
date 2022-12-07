package es.unex.giiis.medicinex.data.database.entities

data class Excipiente
    (
    var id : Int?, // Identificador del excipiente.
    var nombre : String?, // Nombre del excipiente.
    var cantidad : String?, // Cantidad de excipiente.
    var unidad : String?, // Unidad de la cantidad.
    var orden : Int?, // Orden del excipiente.
)
{
    override fun toString() : String
    {
        return "$nombre: $cantidad $unidad"
    }
}

