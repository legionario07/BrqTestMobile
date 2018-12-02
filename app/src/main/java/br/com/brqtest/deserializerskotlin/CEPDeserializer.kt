package br.com.brqtest.deserializerskotlin

import br.com.brqtest.modelkotlin.Endereco
import com.google.gson.*
import java.lang.reflect.Type

class CEPDeserializer : JsonDeserializer<Any> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any {

        var element: JsonElement = json.asJsonObject

        if (json.asJsonObject != null) {
            element = json.asJsonObject
        }

        return Gson().fromJson(element, Endereco::class.java)
    }
}