package com.example.parquecientificouncp.storage

import android.app.Application
import android.content.Context

class SharedPreferenceContext(val context: Context) {
    val SHARED_NAME = "information_user"
    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    //LOGIN
    val LOGIN = "islogin"
    fun setLogin(login:Boolean){
        storage.edit().putBoolean(LOGIN, login).apply()
    }
    fun isLogin():Boolean{
        return  storage.getBoolean(LOGIN, false)
    }
    fun setLogout(){
        storage.edit().clear().commit();
    }

    //INFORMATION USER

    //ROL USER
        val TIPO_ROL = "tipouser"
        fun setTypeUser(tipo: String){
            storage.edit().putString(TIPO_ROL, tipo).apply()
        }
        fun getTypeUser():String{
            return  storage.getString(TIPO_ROL,"")!!
        }

    //TIPO_ROL
        val ID_ROL = "idrol"
        fun setIdRol(id_rol: Int){
            storage.edit().putInt(ID_ROL, id_rol).apply()
        }
        fun getIdRol():Int{
            return  storage.getInt(ID_ROL,0)!!
        }




    //ID_INVESTIGACION
        val ID_INVESTIGATION = "idinvestigation"
        fun setIdInvestigation(id_investigation: Int){
            storage.edit().putInt(ID_INVESTIGATION, id_investigation).apply()
        }
        fun getIdInvestigation():Int{
            return  storage.getInt(ID_INVESTIGATION,0)!!
        }




    //ID_PERSONA
        val ID_PERSONA = "idpersona"
        fun setIdPersona(id_persona: Int){
            storage.edit().putInt(ID_PERSONA, id_persona).apply()
        }
        fun getIdPersona():Int{
            return  storage.getInt(ID_PERSONA,0)!!
        }
    //NAME USER
        val NAME_USER = "nameuser"
        fun setNameUser(name_user: String){
            storage.edit().putString(NAME_USER, name_user).apply()
        }
        fun getNameUser():String{
            return  storage.getString(NAME_USER,"")!!
        }

    //LASTNAME USER
        val LASTNAME_USER = "lastnameuser"
        fun setLastNameUser(lastname_user: String){
            storage.edit().putString(LASTNAME_USER, lastname_user).apply()
        }
        fun getLastNameUser():String{
            return  storage.getString(LASTNAME_USER,"")!!
        }
    //FOTO USER
        val FOTO_USER = "foto"
        fun setFotoUser(foto_user: String){
            storage.edit().putString(FOTO_USER, foto_user).apply()
        }
        fun getFotoUser():String{
            return  storage.getString(FOTO_USER,"")!!
        }

    //ADDRESS USER
        val ADDRESS_USER = "addressuser"
        fun setAddressUser(address_user: String){
            storage.edit().putString(ADDRESS_USER, address_user).apply()
        }
        fun getAddressUser():String{
            return  storage.getString(ADDRESS_USER,"")!!
        }

    //PHONE USER
        val PHONE_USER = "phone"
        fun setPhoneUser(phone_user: String){
            storage.edit().putString(PHONE_USER, phone_user).apply()
        }
        fun getPhoneUser():String{
            return  storage.getString(PHONE_USER,"")!!
        }

    //EMAIL USER
        val EMAIL_USER = "email"
        fun setEmailUser(phone_user: String){
            storage.edit().putString(EMAIL_USER, phone_user).apply()
        }
        fun getEmailUser():String{
            return  storage.getString(EMAIL_USER,"")!!
        }

    //URL FILE
    val FILE_USER = "file"
    fun setFileUser(file_user: String){
        storage.edit().putString(FILE_USER, file_user).apply()
    }
    fun getFileUser():String{
        return  storage.getString(FILE_USER,"")!!
    }

    //ID DINAMIC INVESTIGATION
    val ID_ITEMINVESTIGATION = "iditeminvestigation"
    fun setIdItemInvestigation(id_iteminvestigation: Int){
        storage.edit().putInt(ID_ITEMINVESTIGATION, id_iteminvestigation).apply()
    }
    fun getIdItemInvestigation():Int{
        return  storage.getInt(ID_ITEMINVESTIGATION,0)
    }

    //ID DINAMIC INVESTIGATION
    val ID_ITEMINVESTIGATOR = "iditeminvestigator"
    fun setIdItemInvestigator(id_iteminvestigator: Int){
        storage.edit().putInt(ID_ITEMINVESTIGATOR, id_iteminvestigator).apply()
    }
    fun getIdItemInvestigator():Int{
        return  storage.getInt(ID_ITEMINVESTIGATOR,0)
    }


    //URL  DINAMIC INVESTIGATION
    val URL_ITEMINVESTIGATION = "urliteminvestigation"
    fun setUtlItemInvestigation(id_iteminvestigator: String){
        storage.edit().putString(URL_ITEMINVESTIGATION, id_iteminvestigator).apply()
    }
    fun getUtlItemInvestigation():String{
        return  storage.getString(URL_ITEMINVESTIGATION,"")!!
    }



}