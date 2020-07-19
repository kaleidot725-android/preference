package jp.kaleidot725.sample.setting

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import jp.kaleidot725.sample.R

class ParentPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.parent_preferences, rootKey)

        val editTextPreference = findPreference<EditTextPreference>("editTextPreference")
        editTextPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> {
            "value :" + it.text
        }
    }
}
