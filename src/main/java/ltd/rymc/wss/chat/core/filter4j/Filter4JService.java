package ltd.rymc.wss.chat.core.filter4j;

import ltd.rymc.function.throwable.ThrowableFunction;
import ltd.rymc.io.IOProcessor;
import ltd.rymc.wss.chat.core.FilterResult;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.core.filter4j.layer.Layer;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Filter4JService implements FilterService {

    private final List<Layer> compiledScript;
    private final Tokenizer tokenizer;

    public Filter4JService(ThrowableFunction<String, InputStream, IOException> loader) {
        try {
            compiledScript = IOProcessor.bufferedRead("model/judge.model", loader, reader -> {
                String[] script = reader.lines().toArray(String[]::new);
                return ScriptCompiler.compile(script);
            });

            tokenizer = IOProcessor.bufferedRead("model/tokenize.model", loader, reader -> {
                int size = Integer.parseInt(reader.readLine());
                String[] vocab = new String[size];
                for (int i = 0; i < size; i++) {
                    vocab[i] = reader.readLine();
                }
                return new Tokenizer(vocab);
            });

        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public FilterResult filter(String playerMessage) {
        return MinimalRuntime.doAi(tokenizer.tokenize(playerMessage), compiledScript) == 1 ? FilterResult.UNSAFE : FilterResult.SAFE;
    }

}
