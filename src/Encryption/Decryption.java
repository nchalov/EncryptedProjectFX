package Encryption;
import Directories.Directories;
import Exceptions.IncorrectAlphabetException;
import Exceptions.IncorrectFileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Decryption {

    public String encryptedText;
    public static boolean isChoiceDecryption;

    public Decryption() throws IOException {
        encryptedText = getText(Directories.originFile);
    }

    private String toStandardCase(String text) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            char originCh = encryptedText.charAt(i);
            char textCh = text.charAt(i);
            if (Character.isUpperCase(originCh)) {
                sb.append(Character.toUpperCase(textCh));
            } else {
                sb.append(textCh);
            }
        }
        return sb.toString();
    }

    private boolean alphabetCheck(Object[] alphabet) {
        String lang = "";
        for (Object object : alphabet) {
            lang += object;
        }
        boolean langMatches = lang.matches("[A-Za-z]+");
        boolean textMatches = encryptedText.matches("[A-Za-z]+");
        return langMatches == textMatches;
    }

    private char[] getLetterArray(String text) {
        String res = "";
        Pattern pattern = Pattern.compile("\\W+\\s?");
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

    public String getCaesarDecryption(int key, Object[] alphabet)
            throws IncorrectAlphabetException {

        if (!alphabetCheck(alphabet) && key == 0) {
            throw new IncorrectAlphabetException("Выбран неверный алфавит");
        }

        if (key == 0) {
            return encryptedText;
        }

        if (!alphabetCheck(alphabet)) {
            throw new IncorrectAlphabetException("Выбран неверный алфавит");
        }

        String newText = encryptedText.toLowerCase().trim();
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


