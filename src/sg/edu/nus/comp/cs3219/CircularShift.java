package sg.edu.nus.comp.cs3219;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kfwong on 1/27/17.
 */
public class CircularShift implements Filter<List<String>> {

    public static String[] STOP_WORDS = {
            "is", "the", "of", "and", "as", "a", "after"
    };

    @Override
    public List<String> execute(List<String> data) {
        List<String> result = new ArrayList<>();

        data.forEach(d -> {
            String[] words = extractWords(d);
            for (int i = 0; i < words.length; i++) {
                String firstWord = words[0];
                if (!isStopWord(firstWord)) {
                    String shiftedSentence = String.join(" ", words);
                    result.add(shiftedSentence);
                }

                String[] shiftedWords = leftShift(words);
                System.arraycopy(shiftedWords, 0, words, 0, shiftedWords.length);
            }
        });

        return result;
    }

    private String[] leftShift(String[] s) {

        String[] shiftedWords = new String[s.length];

        if (s.length > 1) {
            System.arraycopy(s, 1, shiftedWords, 0, s.length-1);
            System.arraycopy(s, 0, shiftedWords, shiftedWords.length-1, 1);
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
