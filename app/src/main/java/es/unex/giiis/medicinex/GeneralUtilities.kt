package es.unex.giiis.medicinex

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import es.unex.giiis.medicinex.database.*

abstract class GeneralUtilities
{
    companion object
    {
        fun isThereInternet(context: Context): Boolean
        {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null)
            {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                {
                    return true
                }
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                {
                    return true
                }
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                {
                    return true
                }
            }
            return false
        }

        fun getAccountNameByMail(email : String) : String
        {
            return email.substring(0, email.indexOf('@'))
        }

        fun parseMedicine(ref : DataSnapshot) : Medicina
        {
            val med = Medicina("", null, null, null, null, null, null, null, null,
                mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), FormaFarmaceutica(null, null),
                FormaFarmaceuticaSimplificada(null, null), null, null)

            lateinit var documento : Doc
            lateinit var excipiente  : Excipiente
            lateinit var presentacion  : Presentacion
            lateinit var principioActivo  : PrincipioActivo
            lateinit var viaAdministracion  : ViaAdministracion

            for(child in ref.children)
            {
                when(child.key)
                {
                    "Comerc" -> med.comerc = child.value as Boolean
                    "Conduc" -> med.conduc = child.value as Boolean
                    "Cpresc" -> med.cPresc = child.value as String
                    "Docs" ->
                    {
                        for(doc in child.children)
                        {
                            documento = Doc(null, null, null, null)
                            for(doc_field in doc.children)
                            {
                                when(doc_field.key)
                                {
                                    "Secc" -> documento.secc = doc_field.value as Boolean
                                    "Tipo" -> documento.tipo = doc_field.getValue<Int>()
                                    "Url" ->  documento.url = doc_field.value as String
                                    "UrlHtml" -> documento.urlHtml = doc_field.value as String
                                    else -> {}
                                }
                            }
                            med.docs?.add(documento)
                        }
                    }
                    "Dosis" -> med.dosis = child.value as String
                    "Ema" -> med.ema = child.value as Boolean
                    "Excipientes" ->
                    {
                        for(exc in child.children)
                        {
                            excipiente  = Excipiente(null, null, null, null, null)
                            for(exc_fields in exc.children)
                            {
                                when(exc_fields.key)
                                {
                                    "Cantidad" -> excipiente.cantidad = exc_fields.value as String
                                    "Id" -> excipiente.id = exc_fields.getValue<Int>()
                                    "Nombre" -> excipiente.nombre = exc_fields.value as String
                                    "Orden" -> excipiente.orden = exc_fields.getValue<Int>()
                                    "Unidad" -> excipiente.unidad = exc_fields.value as String
                                    else -> {}
                                }
                            }
                            med.excipientes?.add(excipiente)
                        }
                    }
                    "FormaFarmaceutica" ->
                    {
                        for(ff in child.children)
                        {
                            when (ff.key)
                            {
                                "Id" -> med.formaFarma?.id = ff.getValue<Int>()
                                "Nombre" -> med.formaFarma?.nombre = ff.value as String
                            }
                        }
                    }
                    "FormaFarmaceuticaSimplificada" ->
                    {
                        for(ffs in child.children)
                        {
                            when (ffs.key)
                            {
                                "Id" -> med.formaFarmaSimpli?.id = ffs.getValue<Int>()
                                "Nombre" -> { med.formaFarmaSimpli?.nombre = ffs.value as String; med.cluster = ffs.value as String}
                            }
                        }
                    }
                    "Labtitular" -> med.labTitular = child.value as String
                    "Nombre" -> { med.nombre = child.value as String; med.seccion = med.nombre!![0] }
                    "Nregistro" -> med.nRegistro = child.value as String
                    "Presentaciones" ->
                    {
                        for(pres in child.children)
                        {
                            presentacion = Presentacion(null, null)
                            for(pres_field in pres.children)
                            {
                                when (pres_field.key)
                                {
                                    "Cn" -> presentacion.cn = pres_field.value as String
                                    "Nombre" -> presentacion.nombre = pres_field.value as String
                                }
                            }
                            med.presentaciones?.add(presentacion)
                        }
                    }

                    "PrincipiosActivos" ->
                    {
                        for(pa in child.children)
                        {
                            principioActivo = PrincipioActivo(null, null, null, null, null, null)
                            for(pa_field in pa.children)
                            {
                                when(pa_field.key)
                                {
                                    "Cantidad" -> principioActivo.cantidad = pa_field.value as String
                                    "Codigo" -> principioActivo.codigo = pa_field.value as String
                                    "Id" -> principioActivo.id = pa_field.getValue<Int>()
                                    "Nombre" -> principioActivo.nombre = pa_field.value as String
                                    "Orden" -> principioActivo.orden = pa_field.getValue<Int>()
                                    "Unidad" -> principioActivo.unidad = pa_field.value as String
                                }
                            }
                            med.pActivos?.add(principioActivo)
                        }
                    }

                    "Receta" -> med.receta = child.value as Boolean
                    "ViasAdministracion" ->
                    {
                        for(va in child.children)
                        {
                            viaAdministracion = ViaAdministracion(null, null)
                            for(va_field in va.children)
                            {
                                when(va_field.key)
                                {
                                    "Id" -> viaAdministracion.id = va_field.getValue<Int>()
                                    "Nombre" -> viaAdministracion.nombre = va_field.value as String
                                }
                            }
                            med.vAdministracion?.add(viaAdministracion)
                        }
                    }
                    else -> {}
                }
            }
            return med
        }
    }
}