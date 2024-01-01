package Encryption;
import Alphabet.Alphabet;
import Exceptions.IncorrectAlphabetException;
import Exceptions.NoMatchesException;
import Directories.Directories;
import Vocabulary.Vocabulary;
import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bruteforce {
    private boolean isChoiceBruteforce;
    private Object[] alphabet;
    private Directories directories;

    public Object[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Object[] alphabet) {
        this.alphabet = alphabet;
    }

    public boolean isChoiceBruteforce() {
        return isChoiceBruteforce;
    }

    public void setChoiceBruteforce(boolean choiceBruteforce) {
        isChoiceBruteforce = choiceBruteforce;
    }

    public void setDirectories(Directories directories) {
        this.directories = directories;
    }

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

    private boolean isRussian() throws IOException {
        Pattern pattern = Pattern.compile(
                "[" + "а-яА-ЯёЁ" + "\\d" + "\\s" + "\\p{Punct}" + "]" + "*");
        Matcher matcher = pattern.matcher(getText(directories.getOrigin()));
        return matcher.matches();
    }

    public String bruteforce(int key, Object[] alphabet)
            throws IOException, NoMatchesException, IncorrectAlphabetException {

        if (key > alphabet.length - 1) {
            throw new NoMatchesException("Не найдено совпадений");
        }

        if (isRussian() && Arrays.equals(alphabet, Alphabet.LATIN)) {
            throw new IncorrectAlphabetException("Введен неверный алфавит");
        }

        if (!isRussian() && Arrays.equals(alphabet, Alphabet.RUSSIAN)) {
            throw new IncorrectAlphabetException("Введен неверный алфавит");
        }

        Decryption decryption = new Decryption();
        decryption.setDirectories(directories);
        String caesarDecryption = decryption.getCaesarDecryption(key, alphabet);

        String[] split = caesarDecryption.replaceAll("[^A-Za-zА-Яа-яё\\s]+", "").split("\\s+");

        int countMatches = 0;

        for (String s : split) {
            boolean isExist = false;
            if (Arrays.equals(alphabet, Alphabet.LATIN)) {
                isExist = wordMatches(s, Vocabulary.ENGLISH);
            } else {
                isExist = wordMatches(s, Vocabulary.RUSSIAN);
            }
            if (isExist) {
                countMatches++;
            }
        }

        if (countMatches >= split.length * 0.8) {
            return caesarDecryption;
        }

        return bruteforce(key + 1, alphabet);
    }
}
