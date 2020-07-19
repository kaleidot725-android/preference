package jp.kaleidot725.sample

import androidx.appcompat.app.AppCompatActivity
<<<<<<< Updated upstream
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MainActivity : AppCompatActivity(R.layout.activity_main), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{
    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
=======
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MainActivity : AppCompatActivity(R.layout.activity_main), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        // Instantiate the new Fragment
>>>>>>> Stashed changes
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, pref.fragment)
        fragment.arguments = args
        fragment.setTargetFragment(caller, 0)

<<<<<<< Updated upstream
        supportFragmentManager.beginTransaction()
=======
        supportFragmentManager
            .beginTransaction()
>>>>>>> Stashed changes
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
