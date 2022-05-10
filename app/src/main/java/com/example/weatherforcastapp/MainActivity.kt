package com.example.weatherforcastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.weatherforcastapp.favourite.view.FavouriteFragment
import com.example.weatherforcastapp.home.view.HomeFragment
import com.example.weatherforcastapp.setting.view.SettingFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var  drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView :NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commit()
            navView.setCheckedItem(R.id.home_item)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            //it.isChecked =true
            when(it.itemId){

                R.id.home_item->replaceFragment(HomeFragment(),it.title.toString())
                R.id.favourite_item->replaceFragment(FavouriteFragment(),it.title.toString())
                R.id.setting_item->replaceFragment(SettingFragment(),it.title.toString())

            }
            true
        }


    }

    private fun replaceFragment(fragment:Fragment,title:String){
        var fragmentManger = supportFragmentManager
        var fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}