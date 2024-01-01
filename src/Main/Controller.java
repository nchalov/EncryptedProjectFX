package Main;

import Alphabet.Alphabet;
import Directories.Directories;
import Encryption.Bruteforce;
import Encryption.Encryption;
import Encryption.Decryption;
import Exceptions.IncorrectAlphabetException;
import Exceptions.IncorrectFileException;
import Exceptions.NoMatchesException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Controller {

    @FXML
    private ComboBox<String> alphabetCombobox;

    @FXML
    private Button brutforceButton;

    @FXML
    private Button cipherButton;

    @FXML
    private Button encryptButton;

    @FXML
    private ComboBox<Integer> keyCombobox;

    @FXML
    private Button readButton;

    @FXML
    private TextField textReadField;

    @FXML
    private TextField textWriteField;

    @FXML
    private Button startButton;

    @FXML
    private Button writeButton;

    private final Encryption encryption = new Encryption();

    private final Decryption decryption = new Decryption();

    private final Bruteforce bruteforce = new Bruteforce();

    private void startEncryption() throws IncorrectFileException, IncorrectAlphabetException {
        try {
            Directories directories = new Directories(textReadField.getText(), textWriteField.getText());
            encryption.setDirectories(directories);
            String text = encryption.getEncryption(encryption.getKey(), encryption.getAlphabet());
            if (directories.getDestiny().length() == 0) {
                Files.writeString(Paths.get(directories.getDestiny().toURI()), text,
                        StandardOpenOption.APPEND);
            } else {
                Files.writeString(Paths.get(directories.getDestiny().toURI()), "\n" + text,
                        StandardOpenOption.APPEND);
            }
            if (directories.getDestiny().equals(directories.getOrigin())) {
                throw new IncorrectFileException("Выбран неверный файл");
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Один или несколько файлов не были выбраны");
            alert.showAndWait();
        }
    }

    private void startDecryption() throws IncorrectFileException, IncorrectAlphabetException {
        try {
            Directories directories = new Directories(textReadField.getText(), textWriteField.getText());
            decryption.setDirectories(directories);
            String text = decryption.getCaesarDecryption(decryption.getKey(), decryption.getAlphabet());
            if (directories.getDestiny().length() == 0) {
                Files.writeString(Paths.get(directories.getDestiny().toURI()), text,
                        StandardOpenOption.APPEND);
            } else {
                Files.writeString(Paths.get(directories.getDestiny().toURI()), "\n" + text,
                        StandardOpenOption.APPEND);
            }
            if (directories.getDestiny().equals(directories.getOrigin())) {
                throw new IncorrectFileException("Выбран неверный файл");
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Один или несколько файлов не были выбраны");
            alert.showAndWait();
        }
    }

    private void startBruteforce() throws NoMatchesException, IncorrectAlphabetException, IncorrectFileException {
        try {
            Directories directories = new Directories(textReadField.getText(), textWriteField.getText());
            bruteforce.setDirectories(directories);
            String text = bruteforce.bruteforce(1, bruteforce.getAlphabet());
            if (directories.getDestiny().length() == 0) {
                Files.writeString(Paths.get(directories.getDestiny().toURI()), text,
                        StandardOpenOption.APPEND);
            } else {
                Files.writeString(Paths.get(directories.getDestiny().toURI()), "\n" + text,
                        StandardOpenOption.APPEND);
            }
            if (directories.getDestiny().equals(directories.getOrigin())) {
                throw new IncorrectFileException("Выбран неверный файл");
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Один или несколько файлов не были выбраны");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {

        readButton.setOnAction(event -> {
            readButton.setStyle("Green");
            Stage stage = new Stage();
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            try {
                textReadField.setText(chooser.showOpenDialog(stage).toString());
            } catch (NullPointerException e) {
                e.getMessage();
            }
        });

        writeButton.setOnAction(event -> {
            writeButton.setStyle("Green");
            Stage stage = new Stage();
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            try {
                textWriteField.setText(chooser.showOpenDialog(stage).toString());
            } catch (NullPointerException e) {
                e.getMessage();
            }
        });

        startButton.setOnAction(event -> {
            if (encryption.isChoiceEncryption()) {
                try {
                    startEncryption();
                    if (!textReadField.getText().isEmpty() && !textWriteField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Успешное выполнение программы");
                        alert.setHeaderText(null);
                        alert.setContentText("Файл успешно зашифрован");
                        alert.showAndWait();
                    }
                } catch (IncorrectFileException | IncorrectAlphabetException e) {
                    throw new RuntimeException(e);
                }
            } else if (decryption.isChoiceDecryption()) {
                try {
                    startDecryption();
                    if (!textReadField.getText().isEmpty() && !textWriteField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Успешное выполнение программы");
                        alert.setHeaderText(null);
                        alert.setContentText("Файл успешно дешифрован");
                        alert.showAndWait();
                    }
                } catch (IncorrectAlphabetException | IncorrectFileException e) {
                    throw new RuntimeException(e);
                }
            } else if (bruteforce.isChoiceBruteforce()) {
                try {
                    startBruteforce();
                    if (!textReadField.getText().isEmpty() && !textWriteField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Успешное выполнение программы");
                        alert.setHeaderText(null);
                        alert.setContentText("Файл успешно взломан");
                        alert.showAndWait();
                    }
                } catch (NoMatchesException | IncorrectFileException | IncorrectAlphabetException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!encryption.isChoiceEncryption() && (!decryption.isChoiceDecryption()) && (!bruteforce.isChoiceBruteforce())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Опция не выбрана");
                alert.showAndWait();
            }
        });

        cipherButton.setOnAction(actionEvent -> {
            encryption.setChoiceEncryption(true);
            decryption.setChoiceEncryption(false);
            bruteforce.setChoiceBruteforce(false);
            cipherButton.setStyle("Green");
            keyCombobox.setDisable(false);

        });

        encryptButton.setOnAction(actionEvent -> {
            encryption.setChoiceEncryption(false);
            decryption.setChoiceEncryption(true);
            bruteforce.setChoiceBruteforce(false);
            encryptButton.setStyle("Green");
            keyCombobox.setDisable(false);
        });

        brutforceButton.setOnAction(actionEvent -> {
            encryption.setChoiceEncryption(false);
            decryption.setChoiceEncryption(false);
            bruteforce.setChoiceBruteforce(true);
            brutforceButton.setStyle("Green");
            keyCombobox.setDisable(true);
        });

        alphabetCombobox.setItems(FXCollections.observableArrayList("Кириллица", "Латиница"));
        if (alphabetCombobox.getSelectionModel().getSelectedItem() == null) {
            encryption.setAlphabet(Alphabet.RUSSIAN);
            decryption.setAlphabet(Alphabet.RUSSIAN);
            bruteforce.setAlphabet(Alphabet.RUSSIAN);
            encryption.setKey(0);
            decryption.setKey(0);
            keyCombobox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                    13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));
        }
        alphabetCombobox.setOnAction(actionEvent -> {
            keyCombobox.setValue(0);
            if (alphabetCombobox.getSelectionModel().getSelectedItem().equals("Кириллица")) {
                encryption.setAlphabet(Alphabet.RUSSIAN);
                decryption.setAlphabet(Alphabet.RUSSIAN);
                bruteforce.setAlphabet(Alphabet.RUSSIAN);
                keyCombobox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));
            } else if (alphabetCombobox.getSelectionModel().getSelectedItem().equals("Латиница")) {
                encryption.setAlphabet(Alphabet.LATIN);
                decryption.setAlphabet(Alphabet.LATIN);
                bruteforce.setAlphabet(Alphabet.LATIN);
                keyCombobox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25));
            }
        });
        keyCombobox.setOnAction(actionEvent -> {
            if (keyCombobox.getSelectionModel().getSelectedItem() != null) {
                encryption.setKey(keyCombobox.getSelectionModel().getSelectedItem());
                decryption.setKey(keyCombobox.getSelectionModel().getSelectedItem());
            }
        });
    }
}






