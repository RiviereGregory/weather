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
            var json: String? = null

            try {
                val inputStream: InputStream = resources.openRawResource(R.raw.city_list_fr)
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

            } catch (e: Exception) {
                Log.e("JsonUtils", "Erreur parsing", e)
            }
            return citiesNames
        }
    }
}