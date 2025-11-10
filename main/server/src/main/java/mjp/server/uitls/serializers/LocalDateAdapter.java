/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.uitls.serializers;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 *
 * @author twiki
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
  }
    
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String serializedDate = json.getAsJsonPrimitive().getAsString();
        return LocalDate.parse(serializedDate);
  }
}
