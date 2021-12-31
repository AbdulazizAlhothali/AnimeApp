package com.example.animeapp.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.animeapp.MainActivity
import com.example.animeapp.R
import com.example.animeapp.databinding.SettingsFragmentBinding
import com.example.animeapp.databinding.SignInFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.chang_language.view.*
import java.util.*

class SettingsFragment : Fragment() {
    private lateinit var binding: SettingsFragmentBinding


    private lateinit var preferences: SharedPreferences
    private lateinit var settings :SharedPreferences


    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= SettingsFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvChangeLanguage.setOnClickListener {
            dialogChangeLanguage()

        }


    }



    private fun dialogChangeLanguage() {

        val view: View = layoutInflater.inflate(R.layout.chang_language, null)

        val builder = BottomSheetDialog(requireView().context!!)
        builder.setTitle("Change Language")

        val btnChangeLanguage = view.btnChangeLanguage

        var radioGroup = view.radioGroup

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            var selectedLanguage:RadioButton=view.findViewById(checkedId)
            if (selectedLanguage != null) btnChangeLanguage.setOnClickListener {

                Log.e("language","${selectedLanguage.text.toString()}")


                if (selectedLanguage.text.toString()=="Arabic"){

                    setLocaleFeather("ar")

                }else if (selectedLanguage.text.toString()=="English"){
                    setLocaleFeather("en")

                }
            }

        }

        builder.setContentView(view)

        btnChangeLanguage.setOnClickListener {

            if (view is RadioButton) {
                val checked = view.isChecked

                when (view.getId()) {
                    R.id.englishLanguageXml ->
                        if (checked) {
                            Log.e("Language","English")
                        }
                    R.id.arabicLanguageXml ->
                        if (checked) {
                            Log.e("Language","عربي")
                        }
                }
            }
        }
        builder.show()

    }



    //------------------------------------------------------------------
    private fun setLocaleFeather(localeName: String) {

        val locale = Locale(localeName.toString())
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale

        //---------------------------------------------------------------
        context?.resources?.updateConfiguration(config, requireContext().resources.displayMetrics)

        settings = this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = settings.edit()
        editor.putString("Settings", locale.toString())
        editor.apply()
        val refresh = Intent(context, MainActivity::class.java)
        startActivity(refresh)
    }

}