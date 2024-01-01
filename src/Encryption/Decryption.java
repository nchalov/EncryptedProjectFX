package Encryption;
import Alphabet.Alphabet;
import Directories.Directories;
import Exceptions.IncorrectAlphabetException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decryption {

    private int key;
    private Object[] alphabet;
    private Directories directories;
    private boolean isChoiceDecryption;

    public Object[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Object[] alphabet) {
        this.alphabet = alphabet;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isChoiceDecryption() {
        return isChoiceDecryption;
    }

    public void setChoiceEncryption(boolean choiceDecryption) {
        isChoiceDecryption = choiceDecryption;
    }

    public void setDirectories(Directories directories) {
        this.directories = directories;
    }

    private String toStandardCase(String text) throws IOException {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getText(directories.getOrigin()).length(); i++) {
            char originCh = getText(directories.getOrigin()).charAt(i);
            char textCh = text.charAt(i);
            if (Character.isUpperCase(originCh)) {
                sb.append(Character.toUpperCase(textCh));
            } else {
                sb.append(textCh);
            }
        }
        return sb.toString();
    }

    private char[] getLetterArray(String text) {
        String res = "";
        Pattern pattern = Pattern.compile("[A-Za-zА-Яа-яё_\\W]\\s?");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            res += matcher.group();
        }
        return res.toCharArray();
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

    public String getCaesarDecryption(int key, Object[] alphabet)
            throws IncorrectAlphabetException, IOException {

        if (isRussian() && Arrays.equals(alphabet, Alphabet.LATIN)) {
            throw new IncorrectAlphabetException("Введен неверный алфавит");
        }

        if (!isRussian() && Arrays.equals(alphabet, Alphabet.RUSSIAN)) {
            throw new IncorrectAlphabetException("Введен неверный алфавит");
        }

        if (key == 0) {
            return getText(directories.getOrigin());
        }

        String newText = getText(directories.getOrigin()).toLowerCase().trim();
        char[] letterArray = getLetterArray(newText);
        for (int i = 0; i < letterArray.length; i++) {
            char ch = letterArray[i];
            for (int j = 0; j < alphabet.length; j++) {
                if (Character.toString(ch).equals(alphabet[j].toString())) {
                    int index = (j - key) < 0 ?
                            Math.abs(alphabet.length + (j - key)) : (j - key);
                    letterArray[i] = alphabet[index].toString().charAt(0);
                }
            }
        }
        return toStandardCase(new String(letterArray));
    }
}


