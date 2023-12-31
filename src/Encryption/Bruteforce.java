package Encryption;
import Exceptions.IncorrectAlphabetException;
import Exceptions.NoMatchesException;
import Directories.Directories;
import Vocabulary.Vocabulary;

import java.io.*;

public class Bruteforce {
    public static boolean isChoiceBruteforce;

    private boolean wordMatches(String word, String vocabulary) throws IOException {
        FileReader fileReader = new FileReader(vocabulary);
        BufferedReader reader = new BufferedReader(fileReader);
        while (reader.ready()) {
            if (reader.readLine().equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    private String getText(File file) throws IOException {
        String text = "";
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        int count = 0;
        while (reader.ready()) {
            if (count > 0) {
                text += "\n";
            }
            text += reader.readLine();
            count++;
        }
        if (text.endsWith("\n")) {
            text = text.replaceAll("$\\n", "").trim();
        }
        return text;
    }


    private boolean alphabetCheck(Object[] alphabet) throws IOException {
        String lang = "";
        for (Object object : alphabet) {
            lang += object;
        }
        boolean langMatches = lang.matches("[A-Za-z]+");
        boolean textMatches = getText(Directories.originFile).matches("[A-Za-z]+");
        return langMatches == textMatches;
    }

    private String getVocabulary(Object[] alphabet) throws IOException {
        if (!alphabetCheck(alphabet)) {
            return Vocabulary.ENGLISH;
        }
        return Vocabulary.RUSSIAN;
    }

    public String bruteforce(int key, Object[] alphabet, String vocabulary)
            throws IOException, NoMatchesException, IncorrectAlphabetException {

        if (key > alphabet.length - 1) {
            throw new NoMatchesException("Не найдено совпадений");
        }

        if (!alphabetCheck(alphabet)) {
            throw new IncorrectAlphabetException("Выбран неверный алфавит");
        }

        Decryption decryption = new Decryption();
        String caesarDecryption = decryption.getCaesarDecryption(key, alphabet);
        String[] split = caesarDecryption.replaceAll("[^A-Za-zА-Яа-яё\\s]+", "").split("\\s+");

        int countMatches = 0;

        for (String s : split) {
            boolean isExist = wordMatches(s, getVocabulary(alphabet));
            if (isExist) {
                countMatches++;
            }
        }

        if (countMatches >= split.length * 0.8) {
            return caesarDecryption;
        }

        return bruteforce(key + 1, alphabet, vocabulary);
    }
}
