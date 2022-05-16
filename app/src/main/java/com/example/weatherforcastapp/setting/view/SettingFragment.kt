package com.example.weatherforcastapp.setting.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.weatherforcastapp.R
import com.example.weatherforcastapp.map.view.MapsActivity
import java.util.*


class SettingFragment :  PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
//        val units = sharedPreferences.getString("UNIT_SYSTEM","metric")

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val language = sharedPreferences.getString("LANGUAGE_SYSTEM", "").toString()


        val mapPreference: Preference? = findPreference("CUSTOM_LOCATION")
        var test =sharedPreferences.getBoolean("CUSTOM_LOCATION", true)
        mapPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (sharedPreferences.getBoolean("CUSTOM_LOCATION", true)) {

                startActivity(Intent(requireContext(),MapsActivity::class.java))
            }
            true
        }

        // get the language and apply iy
        val configuration: Configuration = requireContext().resources.configuration
        var locale = Locale(language)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)

        } else {
            configuration.locale = locale
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            activity?.getApplicationContext()?.createConfigurationContext(configuration);
            resources.updateConfiguration(configuration, resources.displayMetrics)
            setPreferencesFromResource(R.xml.preference, rootKey)

        } else {
            setPreferencesFromResource(R.xml.preference, rootKey)
        }


    }




}