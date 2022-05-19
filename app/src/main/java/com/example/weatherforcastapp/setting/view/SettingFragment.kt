package com.example.weatherforcastapp.setting.view

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.weatherforcastapp.MainActivity
import com.example.weatherforcastapp.R
import java.util.*


class SettingFragment :  PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference,rootKey)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val language = sharedPreferences.getString("LANGUAGE_SYSTEM", "").toString()
        preferenceManager.findPreference<Preference>("LANGUAGE_SYSTEM")!!
            .setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener { preference, newValue ->
                Log.i("tasnem","iam in language change")

                startActivity(Intent(requireActivity(), MainActivity::class.java))
                return@OnPreferenceChangeListener true
            })


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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }
}