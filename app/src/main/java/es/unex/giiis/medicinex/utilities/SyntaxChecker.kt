package es.unex.giiis.medicinex.utilities

import es.unex.giiis.medicinex.Account
import es.unex.giiis.medicinex.EmailProviders

abstract class SyntaxChecker
{
    companion object
    {
        private const val MIN_LENGTH : Int = 6

        fun isValidPasswordSyntax(password : String): Boolean
        {
            if (password.length >= MIN_LENGTH  && // Minimum characters.
                //password.matches(".*[A-Z].*".toRegex()) && // Must contain an uppercase letter (A-Z).
                //password.matches(".*[a-z].*".toRegex()) && // Must contain an lowercase letter (a-z).
                //password.matches(".*[0-9].*".toRegex()) && // Must contain a number (0-9).
                password.hasNoSpaces())
            {
                return true
            }
            return false
        }

        fun isValidMailSyntax(email : String) : Boolean
        {
            if(email.isNotEmpty() && email.hasNoSpaces() && (email.length - email.mailValidExtensionLength()) >= 4 && email.hasMailExtension() && !email.substring(0, email.indexOf('@')).contains('@'))
            {
                return true
            }
            return false
        }

        private fun isValidUsernameSyntax(username : String) : Boolean
        {
            if(username.isNotEmpty() && username.hasNoSpaces() && username.length >= MIN_LENGTH)
            {
                return true
            }
            return false
        }

        private fun isValidNameSyntax(name : String) : Boolean
        {
            if(name.isNotEmpty() && (name.last() != ' ') && name.length >= MIN_LENGTH)
            {
                return true
            }
            return false
        }

        fun isValidAccountSyntax(account : Account) : Boolean
        {
            if(isValidUsernameSyntax(account.username) &&
                isValidMailSyntax(account.email) &&
                isValidPasswordSyntax(account.password) &&
                isValidNameSyntax(account.fullName)
            )
            {
                return true
            }
            return false
        }

        private fun String.hasNoSpaces() : Boolean
        {
            return !this.contains(' ')
        }

        private fun String.mailValidExtensionLength() : Int
        {// Extension function over String class.
            for(item in EmailProviders.values())
            {
                val index : Int = this.indexOf('@')
                if(index >= 4)
                {
                    if(this.substring(index) == item.getExtension())
                    {
                        return item.getExtension().length
                    }
                }
            }
            return 0
        }

        private fun String.hasMailExtension() : Boolean
        {
            for(item in EmailProviders.values())
            {
                if(this.matches((".*" + item.getExtension()).toRegex()))
                {
                    return true
                }
            }
            return false
        }
    }
}