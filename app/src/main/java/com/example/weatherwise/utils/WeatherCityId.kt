package com.example.weatherwise.utils

class WeatherCityId {
    companion object {
        fun getCityId(name: String) : String {
            when (name) {
                "Bekescsaba" -> return "722437"
                "Budapest" -> return "3054643"
                "Debrecen" -> return "721472"
                "Eger" -> return "721239"
                "Gyor" -> return "3052009"
                "Kaposvar" -> return "3050616"
                "Kecskemet" -> return "3050434"
                "Miskolc" -> return "717582"
                "Nyíregyháza" -> return "716935"
                "Pecs" -> return "3046526"
                "Salgótarján" -> return "3045643"
                "Szeged" -> return "715429"
                "Szekszard" -> return "3044760"
                "Szekesfehervar" -> return "3044774"
                "Szolnok" -> return "715126"
                "Szombathely" -> return "3044310"
                "Tatabanya" -> return "3044082"
                "Veszprem" -> return "3042929"
                "Zalaegerszeg" -> return "3042638"
            }
            return "0"
        }
    }
}