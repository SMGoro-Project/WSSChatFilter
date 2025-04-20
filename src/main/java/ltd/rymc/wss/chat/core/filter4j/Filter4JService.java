package ltd.rymc.wss.chat.core.filter4j;

import ltd.rymc.wss.chat.core.FileLoader;
import ltd.rymc.wss.chat.core.FilterResult;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.core.filter4j.layer.Layer;
import ltd.rymc.wss.chat.utils.ResourceUtil;

import java.util.List;

public class Filter4JService implements FilterService {

    private final List<Layer> compiledScript;
    private final Tokenizer tokenizer;

    public Filter4JService(FileLoader loader) {
        try {
            compiledScript = ResourceUtil.readResource(loader, "model/judge.model", reader -> {
                String[] script = reader.lines().toArray(String[]::new);
                return ScriptCompiler.compile(script);
            });

            tokenizer = ResourceUtil.readResource(loader, "model/tokenize.model", reader -> {
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
