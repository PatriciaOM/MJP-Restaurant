/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.uitls.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Joan Renau Valls
 */
public class MessageDataContainer {
//    public static Gson gson = (new GsonBuilder()).registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();  //TODO do smth with this line. Probably it should be a service dont know i can put services on a junitClass
     public static Gson gson = (new GsonBuilder())
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
        .create();  
    
    
    public static <InputType> String serialize(InputType item){
        return gson.toJson(item);
    }
    
    public static <OutputType> OutputType deserialize(String serializedItem, Class<OutputType> clazz){
        return gson.fromJson(serializedItem, clazz);
    }
    

    
}
