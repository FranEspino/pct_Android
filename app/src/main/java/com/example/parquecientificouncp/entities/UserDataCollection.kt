package com.example.parquecientificouncp.entities

data class PersonaResponse(
    val apellido: String,
    val correo: String,
    val direccion: String,
    val dni: String,
    val fecha_nacimiento: String,
    val foto: String,
    val id_persona: Int,
    val nombre: String,
    val telefono: String
)

data class LoginResponse(
    val persona: List<Persona>,
    val rol: Rol
)

data class Persona(
    val apellido: String,
    val correo: String,
    val direccion: String,
    val dni: String,
    val foto: String,
    val id_persona: Int,
    val nombre: String,
    val telefono: String
)

data class Rol(
    val tipo: String,
    var id_rol: Int,


val cambiar_clave: Int
)

data class InvestigacionResponse(
    val investigacion: List<Investigacion>
)

data class Investigacion(

    val apellido: String,
    val avance: Int,
    val estado: String,
    val correo: String,
    val descripcion: String,
    val fecha_inicio: String,
    val foto: String,
    val id_investigacion: Int,
    val id_rol: Int,
    val nombre: String,
    val telefono: String,
    val titulo: String,
    val url_archivo: String
)
class ComentsResponse : ArrayList<ComentsResponseItem>()

data class ComentsResponseItem(
    val apellido: String,
    val comentario: String,
    val correo: String,
    val dni: String,
    val foto: String,
    val id_comentario: Int,
    val id_investigacion: Int,
    val id_persona: Int,
    val nombre: String
)

data class CitesResponse(
    val cites: List<Cite>
)

data class Cite(
    val descripcion: String,
    val fecha: String,
    val hora: String,
    val id_cita: Int,
    val id_investigacion: Int,
    val link: String,
    val titulo: String
)


data class ResponseUpdatePdf(
    val msg: String
)

data class UpdateFoto(
    val foto: Foto,
    val id_persona: Int
)

data class Foto(
    val archivo: String,
    val extencion: String
)

data class ResponseFoto(
    val foto: String,
    val msg: String
)