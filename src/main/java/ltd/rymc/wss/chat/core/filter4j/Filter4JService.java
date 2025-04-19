package ltd.rymc.wss.chat.core.filter4j;

import ltd.rymc.wss.chat.core.FileLoader;
import ltd.rymc.wss.chat.core.FilterResult;
import ltd.rymc.wss.chat.core.FilterService;
import ltd.rymc.wss.chat.utils.ResourceUtil;

public class Filter4JService implements FilterService {

    private final String[] script;
    private final Tokenizer tokenizer;

    public Filter4JService(FileLoader loader) {
        try {
            script = ResourceUtil.readResource(loader, "model/judge.model", reader -> reader.lines().toArray(String[]::new));

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
        return MinRt.doAi(tokenizer.tokenize(playerMessage), script) == 1 ? FilterResult.UNSAFE : FilterResult.SAFE;
    }

}
