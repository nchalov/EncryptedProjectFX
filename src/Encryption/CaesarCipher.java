package Encryption;

import Exceptions.IncorrectAlphabetException;
import Directories.Directories;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaesarCipher {

    public static boolean isChoiceEncryption;

    public static int key;

    public static Object[] alphabet;

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

    private String toStandardCase(String text) throws IOException {

        if (text == null) {
            throw new NullPointerException();
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getText(Directories.originFile).length(); i++) {
            char originCh = getText(Directories.originFile).charAt(i);
            char textCh = text.charAt(i);
            if (Character.isUpperCase(originCh)) {
                sb.append(Character.toUpperCase(textCh));
            } else {
                sb.append(textCh);
            }
        }
        return sb.toString();
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

    private char[] getLetterArray(String text) {
        StringBuilder resBuilder = new StringBuilder();
        Pattern pattern = Pattern.compile("\\W+\\s?");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            resBuilder.append(matcher.group());
        }
        return resBuilder.toString().toCharArray();
    }

    public String getEncryption(int key, Object[] alphabet)
            throws IncorrectAlphabetException, IOException {

        if (!alphabetCheck(alphabet) && key == 0) {
            throw new IncorrectAlphabetException("Выбран неверный алфавит");
        }

        if (key == 0) {
            return getText(Directories.originFile);
        }

        if (!alphabetCheck(alphabet)) {
            throw new IncorrectAlphabetException("Выбран неверный алфавит");
        }

        String newText = getText(Directories.originFile).toLowerCase().trim();
        char[] letterArray = getLetterArray(newText);
        for (int i = 0; i < letterArray.length; i++) {
            char ch = letterArray[i];
            for (int j = 0; j < alphabet.length; j++) {
                if (Character.toString(ch).equals(alphabet[j].toString())) {
                    int index = (j + key) >= alphabet.length ?
                            Math.abs(alphabet.length - (j + key)) : (j + key);
                    letterArray[i] = alphabet[index].toString().charAt(0);
                }
            }
        }

        return toStandardCase(new String(letterArray));
    }
}

