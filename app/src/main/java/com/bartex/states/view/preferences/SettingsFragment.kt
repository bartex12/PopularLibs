package com.bartex.states.view.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bartex.states.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey)
    }
}