package com.example.parquecientificouncp.fragments
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.parquecientificouncp.*
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View =  inflater.inflate(R.layout.fragment_perfil, container, false)

        val name_user: TextView = view.findViewById(R.id.tv_names)
        val tv_phone: TextView = view.findViewById(R.id.tv_phone)
        val tv_email: TextView = view.findViewById(R.id.tv_email)
        val tv_addres: TextView = view.findViewById(R.id.tv_addres)
        val tbn_modificar : Button = view.findViewById(R.id.tbn_modificar)
        tbn_modificar.setOnClickListener{
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        val iv_investigador: ImageView = view.findViewById(R.id.iv_user)

        Picasso.get().load(UserContextApplication.context.getFotoUser()).into(iv_investigador);
        name_user.setText(UserContextApplication.context.getNameUser() + "\n" + UserContextApplication.context.getLastNameUser())

        iv_investigador.setOnClickListener {
            val intent = Intent(activity, UpdatefotoActivity::class.java)
            startActivity(intent)
        }
        tv_phone.setText(UserContextApplication.context.getPhoneUser())
        tv_email.setText(UserContextApplication.context.getEmailUser())
        tv_addres.setText(UserContextApplication.context.getAddressUser())

        return view
    }

}