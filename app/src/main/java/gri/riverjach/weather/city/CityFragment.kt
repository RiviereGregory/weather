package gri.riverjach.weather.city

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment

import gri.riverjach.weather.App
import gri.riverjach.weather.Database
import gri.riverjach.weather.R

class CityFragment : Fragment() {
    private lateinit var cities: MutableList<City>
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        cities = mutableListOf()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_city, container, false)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_create_city -> {
                showCreateCityDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener =
            object : CreateCityDialogFragment.CreateCityDialogListener {
                override fun onDialogPositiveclick(cityName: String) {
                    saveCity(City(cityName))
                }

                override fun onDialogNegativeClick() {
                    TODO("Not yet implemented")
                }
            }
        fragmentManager?.let { createCityFragment.show(it, "CreateCityDialogFragment") }
    }

    private fun saveCity(city: City) {
        if (database.createCity(city)) {
            cities.add(city)
        } else {
            Toast.makeText(
                context,
                "Could not create city",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}