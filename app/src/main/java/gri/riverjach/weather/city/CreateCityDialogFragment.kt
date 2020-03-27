package gri.riverjach.weather.city

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import gri.riverjach.weather.R


class CreateCityDialogFragment : DialogFragment() {
    interface CreateCityDialogListener {
        fun onDialogPositiveclick(cityName: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val input = EditText(activity)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.createcity_cityhint)
        }

        builder.setTitle(getString(R.string.createcity_title))
            .setView(input)
            .setPositiveButton(getString(R.string.createcity_positve), DialogInterface.OnClickListener { _, _->
             listener?.onDialogPositiveclick(input.text.toString())
            })
            .setNegativeButton(getString(R.string.createcity_negative),
            DialogInterface.OnClickListener{ dialog, _ ->
                dialog.cancel()
                listener?.onDialogNegativeClick()

            })


        return builder.create()
    }
}