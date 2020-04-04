package gri.riverjach.weather.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import gri.riverjach.weather.R
import kotlinx.android.synthetic.main.create_city.*


class CreateCityDialogFragment(arraysCityName: Array<String?>) : DialogFragment() {
    interface CreateCityDialogListener {
        fun onDialogPositiveclick(cityName: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null
    var arraysCityName = arraysCityName
    private lateinit var autoCompleteTextView: AutoCompleteTextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = autoCompleteTextView()

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.createcity_title))
            .setView(view)
            .setPositiveButton(
                getString(R.string.createcity_positve),
                DialogInterface.OnClickListener { _, _ ->
                    if (arraysCityName.contains(autoCompleteTextView.text.toString())) {
                        listener?.onDialogPositiveclick(autoCompleteTextView.text.toString())
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.city_entry_error, autoCompleteTextView.text.toString()),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            .setNegativeButton(getString(R.string.createcity_negative),
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                    listener?.onDialogNegativeClick()

                })


        return builder.create()
    }

    private fun CreateCityDialogFragment.autoCompleteTextView(): View? {
        val adapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_dropdown_item_1line,
            arraysCityName
        )
        for (i in 11..20) {
            Log.i("CityFragment", "${arraysCityName[i]}")
        }

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.create_city, null, false)
        autoCompleteTextView = view.findViewById(R.id.city_name)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.threshold = 2
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    context,
                    getString(R.string.city_selected, selectedItem),
                    Toast.LENGTH_SHORT
                ).show()
            }

        return view
    }
}