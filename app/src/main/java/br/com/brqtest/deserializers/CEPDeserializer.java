package br.com.brqtest.deserializers;

import br.com.brqtest.model.Endereco;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CEPDeserializer implements JsonDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement element = json.getAsJsonObject();

        if(json.getAsJsonObject() != null){
            element = json.getAsJsonObject();
        }

        return (new Gson().fromJson(element, Endereco.class));
    }
}
