package es.unex.giiis.medicinex

enum class EmailProviders
{
    GMAIL, OUTLOOK_COM, OUTLOOK_ES, HOTMAIL_COM, HOTMAIL_ES, YAHOO;

    fun getExtension() : String
    {
        return when(this)
        {
            GMAIL -> "@gmail.com"
            OUTLOOK_COM -> "@outlook.com"
            OUTLOOK_ES -> "@outlook.es"
            HOTMAIL_COM -> "@hotmail.com"
            HOTMAIL_ES -> "@hotmail.es"
            YAHOO -> "@yahoo.com"
        }
    }
}

