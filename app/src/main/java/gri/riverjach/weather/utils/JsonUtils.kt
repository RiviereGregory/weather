package gri.riverjach.weather.utils

import android.content.res.Resources
import android.util.Log
import gri.riverjach.weather.R
import org.json.JSONArray
import java.io.InputStream

data class CityName(
    val name: String,
    val country: String
) {
    override fun toString(): String {
        return "$name, $country"
    }
}


class JsonUtils {
    companion object JsonUtils {
        fun readJson(resources: Resources): ArrayList<CityName> {

            var citiesNames = arrayListOf<CityName>()

            try {
                parseJson(resources, R.raw.city_list, citiesNames)
            } catch (out: OutOfMemoryError) {
                citiesNames.clear()
                shortListOfCountries(resources, citiesNames)
                Log.i("JsonUtils", "OutMemory load less pays")
            } catch (e: Exception) {
                Log.e("JsonUtils", "Erreur parsing", e)
            }
            return citiesNames
        }

        private fun shortListOfCountries(
            resources: Resources,
            citiesNames: ArrayList<CityName>
        ) {
            parseJson(resources, ListCountries.FR.pays, citiesNames)
            parseJson(resources, ListCountries.IT.pays, citiesNames)
            parseJson(resources, ListCountries.ES.pays, citiesNames)
            parseJson(resources, ListCountries.US.pays, citiesNames)
            parseJson(resources, ListCountries.GB.pays, citiesNames)
            parseJson(resources, ListCountries.CA.pays, citiesNames)
            parseJson(resources, ListCountries.DE.pays, citiesNames)
            parseJson(resources, ListCountries.BE.pays, citiesNames)
            parseJson(resources, ListCountries.CH.pays, citiesNames)
        }

        private fun parseJson(
            resources: Resources,
            pays: Int,
            citiesNames: ArrayList<CityName>
        ) {
            var json: String? = null
            val inputStream: InputStream = resources.openRawResource(pays)
            json = inputStream.bufferedReader().use { it.readText() }

            var jsonarr = JSONArray(json)

            for (i in 0..jsonarr.length() - 1) {
                var jsonobj = jsonarr.getJSONObject(i)
                citiesNames.add(
                    CityName(
                        jsonobj.getString("name"),
                        jsonobj.getString("country")
                    )
                )
            }
        }
    }
}
