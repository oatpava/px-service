package com.px.share.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author OPAS
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formatDateTime = localDateTime.format(formatter);
        String[] arrayInputDateTime =  formatDateTime.split(" ");
        int year = 0;
        String dm = arrayInputDateTime[0].substring(0, arrayInputDateTime[0].length()-4);
        String y = arrayInputDateTime[0].substring(arrayInputDateTime[0].length()-4);
        year = Integer.parseInt(y)+543;
        String resultDate = dm+year+" "+arrayInputDateTime[1];
        return new JsonPrimitive(resultDate);
    }
    
}
