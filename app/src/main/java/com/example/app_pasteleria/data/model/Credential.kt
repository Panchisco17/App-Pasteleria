package com.example.app_pasteleria.data.model

data class Credential(val username : String ,val password :String){
    //objeto que permite acceder a la instancia de la clase
    companion object{
        val Admin = Credential(username="admin",password= "123")
        val usuarioDuoc = Credential(username="usuario@duocuc.cl", password= "duoc123")
        val Mayor50 = Credential(username="mayor50@mayor.cl", password= "mayor123")

        val usuarios = listOf(Admin, usuarioDuoc, Mayor50)
    }//fin companion

}//fin del dataclass credential