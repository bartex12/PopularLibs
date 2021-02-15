package com.bartex.states.view.main.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bartex.states.R

class MessageDialog : DialogFragment() {


    companion object {
        const val MESSAGE = "MESSAGE"
        fun newInstance(message: String) =
            MessageDialog().apply {
                arguments = Bundle().apply {
                    putString(MESSAGE, message)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)
        val inflater =requireActivity().getLayoutInflater()
        val view = inflater.inflate(R.layout.dialog_message, null)

        val tvMessage: TextView = view.findViewById(R.id.textViewMessage)
        tvMessage.setText(arguments?.getString(MESSAGE))

        val buttonOk: Button = view.findViewById(R.id.buttonOkMessage)
        builder.setView(view)

        //действия при нажатии кнопки OK
        buttonOk.setOnClickListener{ dismiss() }
        return builder.create()
    }

}