package ltd.rymc.wss.chat.core.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ltd.rymc.wss.chat.core.FilterResult;

import java.lang.reflect.Type;

public class FilterResultSerializer implements JsonSerializer<FilterResult> {

    @Override
    public JsonElement serialize(FilterResult src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("label", src.getResult().name().toLowerCase());
        obj.addProperty("confidence", src.getConfidence());
        src.getReason().ifPresent(reason -> obj.addProperty("reason", reason));
        return obj;
    }

}
