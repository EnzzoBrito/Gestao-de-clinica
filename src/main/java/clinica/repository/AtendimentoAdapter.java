package clinica.repository;

import clinica.model.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AtendimentoAdapter implements JsonSerializer<Atendimento>, JsonDeserializer<Atendimento> {

    @Override
    public JsonElement serialize(Atendimento atendimento, Type type, JsonSerializationContext context) {
        JsonObject obj = context.serialize(atendimento).getAsJsonObject();
        obj.addProperty("classe", atendimento.getClass().getSimpleName());
        return obj;
    }

    @Override
    public Atendimento deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String classe = obj.get("classe").getAsString();

        if ("Consulta".equals(classe)) {
            return context.deserialize(json, Consulta.class);
        }

        if ("Procedimento".equals(classe)) {
            return context.deserialize(json, Procedimento.class);
        }

        throw new JsonParseException("Tipo de atendimento desconhecido: " + classe);
    }
}
