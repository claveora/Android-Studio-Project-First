package com.byatara.aplikasipertama

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton

class ExitConfirmationFragment : DialogFragment() {

    // Interface untuk komunikasi dengan Activity
    interface ExitDialogListener { // ⬅️ NAMA INTERFACE HARUS SAMA PERSIS
        fun onExitConfirmed()
    }

    private var listener: ExitDialogListener? = null

    // Pastikan Anda mengimplementasikan onAttach dengan benar:
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ExitDialogListener
        } catch (e: ClassCastException) {
            // Ini akan memastikan Activity mengimplementasikan listener
            throw ClassCastException("$context must implement ExitDialogListener")
        }
    }

    // ... Bagian onCreateView dan onViewCreated lainnya ...
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.activity_exit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnExit = view.findViewById<MaterialButton>(R.id.btn_exit)
        val btnCancel = view.findViewById<MaterialButton>(R.id.btn_cancel)

        btnExit.setOnClickListener {
            listener?.onExitConfirmed() // Panggil listener
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}