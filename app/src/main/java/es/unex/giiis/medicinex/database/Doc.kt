package es.unex.giiis.medicinex.database

data class Doc
    (
    var tipo : Int?, // Tipo de documento: [1: Ficha Técnica, 2: Prospecto, 3: Informe Público Evaluación, 4: Plan de gestión de riesgos].
    var url : String?, // URL para acceder al documento.
    var urlHtml : String?, // URL para acceder al documento en HTML.
    var secc : Boolean? // Indica si el documento está disponible en HTML por secciones.
)
