package com.vanskarner.movie.persistence.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

internal class MovieDeserializer(private val baseImageUrl: String) : JsonDeserializer<MovieDTO> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MovieDTO {
        val jsonObject = json.asJsonObject
        return MovieDTO(
            jsonObject["id"].asInt,
            getStringValue(jsonObject, "overview"),
            baseImageUrl + getStringValue(jsonObject, "poster_path"),
            baseImageUrl + getStringValue(jsonObject, "backdrop_path"),
            getStringValue(jsonObject, "release_date"),
            getStringValue(jsonObject, "title"),
            jsonObject["vote_count"].asInt,
            jsonObject["vote_average"].asFloat
        )
    }

    private fun getStringValue(jsonObject: JsonObject, key: String): String {
        val element = jsonObject[key]
        return if (element.isJsonNull) "" else element.asString
    }

}