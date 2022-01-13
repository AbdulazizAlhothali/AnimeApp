package com.example.animeapp.ui

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import com.example.animeapp.R
import com.example.animeapp.Utils
import com.example.animeapp.databinding.SettingsFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.chang_language.view.*
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: SettingsFragmentBinding
    private val auth = FirebaseAuth.getInstance()
    private val settings by lazy {
        this.requireActivity().getSharedPreferences(Utils.SETTINGS, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= SettingsFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvChangeLanguage.setOnClickListener {
            dialogChangeLanguage()
        }
        binding.switchDarkMode.isChecked = settings.getBoolean(Utils.CHECK,false)

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
            settings.edit()
                .putBoolean(Utils.CHECK, isChecked)
                .apply()
        }

        binding.SignOut.setOnClickListener {
            auth.signOut()
            settings.edit()
                .clear()
                .apply()
            val action = SettingsFragmentDirections.actionSettingsFragmentToSignInFragment()
            findNavController().navigate(action)
        }
    }

    private fun dialogChangeLanguage() {

        val view: View = layoutInflater.inflate(R.layout.chang_language, null)
        val builder = BottomSheetDialog(requireView().context!!)
        builder.setTitle("Change Language")
        val btnChangeLanguage = view.btnChangeLanguage
        val radioGroup = view.radioGroup
        val radioButton1= view.findViewById<RadioButton>(R.id.englishLanguageXml)
        val radioButton2= view.findViewById<RadioButton>(R.id.arabicLanguageXml)
        val language = settings.getString(Utils.LANGUAGE, "")
        if (language == "ar"){
            radioButton2.isChecked = true
        } else {
            radioButton1.isChecked = true
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedLanguage:RadioButton=view.findViewById(checkedId)
            btnChangeLanguage.setOnClickListener {
                if (selectedLanguage.text.toString()==getString(R.string.arabic)){
                    setLocaleLang("ar")
                    recreate(context as Activity)
                }else if (selectedLanguage.text.toString()==getString(R.string.english)){
                    setLocaleLang("en")
                    recreate(context as Activity)
                }
            }
        }
        builder.setContentView(view)
        builder.show()
    }

    private fun setLocaleLang(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context?.resources?.updateConfiguration(config, requireContext().resources.displayMetrics)
        val editor = settings.edit()
        editor.putString(Utils.LANGUAGE, locale.toString())
        editor.apply()
    }
}