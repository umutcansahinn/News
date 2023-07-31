package com.umutcansahin.mynewsapp.ui.setting_screen

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.umutcansahin.mynewsapp.R
import com.umutcansahin.mynewsapp.databinding.FragmentSettingBinding
import com.umutcansahin.mynewsapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val languageList = listOf("Turkish","English")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,languageList)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }
}