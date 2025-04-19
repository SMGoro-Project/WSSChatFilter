package ltd.rymc.wss.chat.core.llm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import lombok.RequiredArgsConstructor;
import ltd.rymc.wss.chat.core.FilterResult;
import ltd.rymc.wss.chat.core.exception.ModelResponseFailedException;
import ltd.rymc.wss.chat.core.serializer.FilterResultDeserializer;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.core.serializer.FilterResultSerializer;
import ltd.rymc.wss.chat.utils.FunctionalUtil;
import ltd.rymc.wss.chat.utils.StringUtil;
import retrofit2.HttpException;

import java.util.Collections;

@RequiredArgsConstructor
public class LLMFilterService implements FilterService {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(FilterResult.class, new FilterResultDeserializer())
            .registerTypeAdapter(FilterResult.class, new FilterResultSerializer())
            .create();

    private final ClientV4 client;
    private final String prompt;
    private final String model;
    private final float temperature;
    private final int maxAttempts;

    public FilterResult filter(String playerMessage) {

       String prompt = String.format(this.prompt, playerMessage);

       ChatCompletionRequest req = ChatCompletionRequest.builder()
               .model(model)
               .stream(Boolean.FALSE)
               .invokeMethod(Constants.invokeMethod)
               .messages(Collections.singletonList(new ChatMessage(ChatMessageRole.USER.value(), prompt)))
               .build();
       req.setTemperature(temperature);

       ModelApiResponse response = FunctionalUtil.retry(() -> invoke(req), maxAttempts);
       if (response == null) throw new ModelResponseFailedException(ModelResponseFailedException.Type.API_FAILED);

       String content = StringUtil.extractCodeBlock((String) response.getData().getChoices().get(0).getMessage().getContent());
       try {
           return gson.fromJson(content, FilterResult.class);
       } catch (JsonParseException exception) {
           throw new ModelResponseFailedException(ModelResponseFailedException.Type.JSON_PARSE);
       }

    }

    private ModelApiResponse invoke(ChatCompletionRequest req){
        try {
            return client.invokeModelApi(req);
        } catch (HttpException exception) {
            return null;
        }
    }

    public static LLMFilterServiceBuilder builder(String apiKey) {
        return new LLMFilterServiceBuilder(apiKey);
    }

}
