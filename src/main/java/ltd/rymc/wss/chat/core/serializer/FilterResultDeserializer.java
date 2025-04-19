package ltd.rymc.wss.chat.core.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ltd.rymc.wss.chat.core.FilterResult;

import java.lang.reflect.Type;

public class FilterResultDeserializer implements JsonDeserializer<FilterResult> {

    @Override
    public FilterResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        String labelStr = obj.has("label") ? obj.get("label").getAsString().toUpperCase() : "UNKNOWN";
        String reason = obj.has("reason") ? obj.get("reason").getAsString() : null;
        float confidence = obj.has("confidence") ? obj.get("confidence").getAsFloat() : 0.0f;

        FilterResult.Result label;
        try {
            label = FilterResult.Result.valueOf(labelStr);
        } catch (IllegalArgumentException e) {
            label = FilterResult.Result.UNKNOWN;
        }

        return new FilterResult(label, confidence, reason);
    }

}
