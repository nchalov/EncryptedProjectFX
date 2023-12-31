package Main;

import Alphabet.Alphabet;
import Directories.Directories;
import Encryption.Bruteforce;
import Encryption.CaesarCipher;
import Encryption.Decryption;
import Exceptions.IncorrectAlphabetException;
import Exceptions.IncorrectFileException;
import Exceptions.NoMatchesException;
import Vocabulary.Vocabulary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        stage.setTitle("Encrypted");
        stage.setScene(new Scene(root, 611, 441));
        stage.setResizable(false);
        stage.show();
    }

    public static void encryptionStart() throws IOException, IncorrectAlphabetException,
            IncorrectFileException {
        CaesarCipher cipher = new CaesarCipher();
        String text = cipher.getEncryption(CaesarCipher.key, CaesarCipher.alphabet);
        if (Directories.destinyFile.length() == 0) {
            Files.writeString(Paths.get(Directories.destinyFile.toURI()), text,
                    StandardOpenOption.APPEND);
        } else {
            Files.writeString(Paths.get(Directories.destinyFile.toURI()), "\n" + text,
                    StandardOpenOption.APPEND);
        }
        if (Directories.destinyFile.equals(Directories.originFile)) {
            throw new IncorrectFileException("Выбран неверный файл");
        }
    }

    public static void decryptionStart() throws IncorrectAlphabetException, IOException, IncorrectFileException {
        Decryption decryption = new Decryption();
        String text = decryption.getCaesarDecryption(CaesarCipher.key, CaesarCipher.alphabet);
        if (Directories.destinyFile.length() == 0) {
            Files.writeString(Paths.get(Directories.destinyFile.toURI()), text,
                    StandardOpenOption.APPEND);
        } else {
            Files.writeString(Paths.get(Directories.destinyFile.toURI()), "\n" + text,
                    StandardOpenOption.APPEND);
        }
        if (Directories.destinyFile.equals(Directories.originFile)) {
            throw new IncorrectFileException("Выбран неверный файл");
        }
    }

    public static void bruteforceStart() throws
            NoMatchesException, IncorrectAlphabetException, IOException, IncorrectFileException {
        Bruteforce bruteforce = new Bruteforce();
        String text = bruteforce.bruteforce(CaesarCipher.key, CaesarCipher.alphabet, Vocabulary.RUSSIAN);
        if (Directories.destinyFile.length() == 0) {
            Files.writeString(Paths.get(Directories.destinyFile.toURI()), text,
                    StandardOpenOption.APPEND);
        } else {
            Files.writeString(Paths.get(Directories.destinyFile.toURI()), "\n" + text,
                    StandardOpenOption.APPEND);
        }
        if (Directories.destinyFile.equals(Directories.originFile)) {
            throw new IncorrectFileException("Выбран неверный файл");
        }
    }
}


