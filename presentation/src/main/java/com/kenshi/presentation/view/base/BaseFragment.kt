package com.kenshi.presentation.view.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kenshi.presentation.R

abstract class BaseFragment<VB : ViewBinding>(@LayoutRes val layoutId: Int) : Fragment() {

    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    abstract fun getViewBinding(): VB

    private val loadingDialog: AppCompatDialog by lazy {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_loading, null)
        AppCompatDialog(requireContext()).apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setContentView(view)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    fun showProgress() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    fun hideProgress() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}