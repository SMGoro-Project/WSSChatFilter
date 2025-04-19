package ltd.rymc.wss.chat.core.filter4j;

public class Tokenizer {
    private final String[] vocab;

    public Tokenizer(String[] vocab) {
        this.vocab = vocab;
    }

    public double[] tokenize(String text) {
        double[] values = new double[vocab.length];
        for (int i = 0; i < values.length; i++) {
            if (text.contains(vocab[i])) {
                values[i] = 1;
            }
        }
        return values;
    }
}
