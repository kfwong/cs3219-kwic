package sg.edu.nus.comp.cs3219;

/**
 * Created by kfwong on 1/27/17.
 */
public class CircularShift extends Filter<String, String> {

    public static String[] STOP_WORDS = {
            "is", "the", "of", "and", "as", "a", "after"
    };

    public CircularShift(Pipe<String> in, Pipe<String> out) {
        super(in, out);
    }

    @Override
    public synchronized void filter() {
        if (!inPipe.buffer.isEmpty()) {

            String sentence = inPipe.pull();

            String[] words = extractWords(sentence);
            for (int i = 0; i < words.length; i++) {
                String firstWord = words[0];
                if (!isStopWord(firstWord)) {
                    String shiftedSentence = String.join(" ", words);
                    outPipe.push(shiftedSentence);
                }

                String[] shiftedWords = leftShift(words);
                System.arraycopy(shiftedWords, 0, words, 0, shiftedWords.length);
            }

        }

    }

    private String[] leftShift(String[] s) {

        String[] shiftedWords = new String[s.length];

        if (s.length > 1) {
            System.arraycopy(s, 1, shiftedWords, 0, s.length - 1);
            System.arraycopy(s, 0, shiftedWords, shiftedWords.length - 1, 1);
        }

        return shiftedWords;
    }

    private String[] extractWords(String s) {
        return s.split("\\s+");
    }

    private boolean isStopWord(String s) {
        for (String stopWord : STOP_WORDS) {

            if (s.equalsIgnoreCase(stopWord)) {
                return true;
            }
        }

        return false;
    }
}
