package com.example.parquecientificouncp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import com.example.parquecientificouncp.components.MyToolbar
import com.example.parquecientificouncp.fragments.AvanceFragment
import com.example.parquecientificouncp.fragments.HomeFragment
import com.example.parquecientificouncp.fragments.PerfilFragment
import com.example.parquecientificouncp.models.ChangePass
import com.google.android.material.bottomnavigation.BottomNavigationView


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDark)

        MyToolbar().showToolbar(this, "Menu Principal", false)

        val homeFragment = HomeFragment()
        val avanceFragment = AvanceFragment()
        val perfilFragment = PerfilFragment()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        makeCurrentFragment(homeFragment)




        bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_file -> makeCurrentFragment(avanceFragment)
                R.id.ic_user -> makeCurrentFragment(perfilFragment)
            }
            true
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_context, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logount){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            UserContextApplication.context.setLogout()
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}


