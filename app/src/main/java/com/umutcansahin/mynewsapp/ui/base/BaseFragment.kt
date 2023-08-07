package com.umutcansahin.mynewsapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.umutcansahin.mynewsapp.ui.MainActivity

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    protected fun hideActionBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.hideActionBar()
        }
    }

    protected fun showActionBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.showActionBar()
        }
    }

    protected fun hideBottomBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.hideBottomNavigation()
        }
    }

    protected fun showBottomBar() {
        val activity = requireActivity()
        if (activity is MainActivity) {
            activity.showBottomNavigation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}