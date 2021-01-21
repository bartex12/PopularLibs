package com.example.popularlibs_homrworks.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.popularlibs_homrworks.R
import com.example.popularlibs_homrworks.presenter.MainPresenter

class MyDialofFragment(val presenter:MainPresenter): DialogFragment() {

    lateinit var onCancelListener:OnCancelListener

    interface OnCancelListener{
        fun onCancel(isCancel:Boolean)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCancelListener = context as OnCancelListener
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.convert))
                .setPositiveButton(getString(R.string.cont)) {
                        dialog, id ->
                    dialog.cancel()
                    onCancelListener.onCancel(true)
                }
                .setNegativeButton(getString(R.string.abort)){
                    dialog, id ->
                    dialog.cancel()
                    onCancelListener.onCancel(false)
                }
            builder.create()
        } ?: throw IllegalStateException(getString(R.string.errorDialog))
    }
}