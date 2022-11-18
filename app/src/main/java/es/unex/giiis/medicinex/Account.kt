package es.unex.giiis.medicinex

data class Account(var username : String, var email : String, var password : String, var fullName : String,  var firstAidKit: MutableList<String> = mutableListOf("-1"))
