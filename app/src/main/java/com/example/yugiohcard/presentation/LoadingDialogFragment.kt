package com.example.yugiohcard.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.yugiohcard.R

// 7/14/2022
// 10119907
// T DONI INDRAPRASTA PRADANA
// IF10K
object LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        activity?.let {
            AlertDialog.Builder(it)
                .setView(requireActivity().layoutInflater.inflate(R.layout.dialog_loading, null))
                .setCancelable(false)
                .create()
        } ?: throw IllegalStateException(getString(R.string.activity_not_found_error_message))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog?.setCanceledOnTouchOutside(false)
        this.dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (this.dialog?.isShowing == true) return
        super.show(manager, tag)
    }

    override fun dismiss() {
        this.dialog ?: return
        super.dismiss()
    }
}