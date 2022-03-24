package com.example.parquecientificouncp.models

data class Login(
    val dni: String,
    val clave:String)

data class UpdatePdf(
    val documents: String
    )

data class Comment(
    val id_investigacion: Int,
    val id_persona: Int,
    val comentario: String
)
data class Cite(
    val id_investigacion: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val hora: String,
    val link: String
)



data class ChangePass(
    val id_persona: Int,
    val nueva_clave: String
)
data class ModifyInfo(
    val id_persona: Int,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val direccion: String,
    val correo: String

)

data class UpdateProgress(
    val id_investigacion: Int,
    val estado: String,
    val avance: Int

)