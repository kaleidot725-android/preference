package jp.kaleidot725.sample.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import jp.kaleidot725.sample.R

class ParentPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.parent_preferences, rootKey)
    }
}
