package com.umutcansahin.mynewsapp.ui.setting_screen

import android.content.res.Configuration
import android.widget.ArrayAdapter
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.umutcansahin.mynewsapp.R
import com.umutcansahin.mynewsapp.common.enums.CountryType
import com.umutcansahin.mynewsapp.data.preference.DataStorePreference
import com.umutcansahin.mynewsapp.databinding.FragmentSettingBinding
import com.umutcansahin.mynewsapp.manager.language.LanguageManager
import com.umutcansahin.mynewsapp.manager.theme.ThemeManager
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    @Inject
    lateinit var dataStorePreference: DataStorePreference

    @Inject
    lateinit var languageManager: LanguageManager

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onResume() {
        super.onResume()
        initView()
        getDropdownMenu()
        getCurrentLanguage()
        changeAppTheme()
        setSwitchWithUiMode()
    }

    private fun initView() {
        val languageList = listOf(
            getString(R.string.turkish),
            getString(R.string.english),
            getString(R.string.arabic),
            getString(R.string.german),
            getString(R.string.russian),
            getString(R.string.french)
        )
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languageList)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun getCurrentLanguage() {
        when (languageManager.getCurrentLanguage()) {
            CountryType.TURKEY.countryCode -> setDropdownMenu(getString(R.string.turkish))
            CountryType.FRANCE.countryCode -> setDropdownMenu(getString(R.string.french))
            CountryType.GERMANY.countryCode -> setDropdownMenu(getString(R.string.german))
            CountryType.RUSSIA.countryCode -> setDropdownMenu(getString(R.string.russian))
            CountryType.UNITED_ARAB_EMIRATES.countryCode -> setDropdownMenu(getString(R.string.arabic))
            else -> setDropdownMenu(getString(R.string.english))
        }
    }

    private fun setCurrentLanguage(selectedLanguageCode: String) {
        lifecycleScope.launch {
            dataStorePreference.saveSelectedLanguage(selectedLanguageCode)
            dataStorePreference.getSelectedLanguage.flowWithLifecycle(lifecycle)
                .collect { languageCode ->
                    languageManager.takeLanguageCode(languageCode)
                }
        }
    }

    private fun setDropdownMenu(language: String) {
        binding.autoCompleteTextView.setText(language, false)
    }

    private fun getDropdownMenu() {
        binding.autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            when (parent.getItemAtPosition(position).toString()) {
                getString(R.string.turkish) -> setCurrentLanguage(CountryType.TURKEY.countryCode)
                getString(R.string.english) -> setCurrentLanguage(CountryType.UNITED_STATE.countryCode)
                getString(R.string.arabic) -> setCurrentLanguage(CountryType.UNITED_ARAB_EMIRATES.countryCode)
                getString(R.string.german) -> setCurrentLanguage(CountryType.GERMANY.countryCode)
                getString(R.string.russian) -> setCurrentLanguage(CountryType.RUSSIA.countryCode)
                getString(R.string.french) -> setCurrentLanguage(CountryType.FRANCE.countryCode)
            }
        }
    }

    private fun changeAppTheme() {
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModeEnabled(isChecked.not())
        }
    }

    private fun saveDarkModeEnabled(isDarkModeEnabled: Boolean) {
        lifecycleScope.launch {
            dataStorePreference.saveDarkModeEnabled(isDarkModeEnabled)
            dataStorePreference.isDarkModeEnabled.flowWithLifecycle(lifecycle).collect {
                themeManager.setUiTheme(it)
            }
        }
    }

    private fun setSwitchWithUiMode() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {

            Configuration.UI_MODE_NIGHT_NO -> binding.switchDarkMode.isChecked = false
            Configuration.UI_MODE_NIGHT_YES -> binding.switchDarkMode.isChecked = true
        }
    }
}