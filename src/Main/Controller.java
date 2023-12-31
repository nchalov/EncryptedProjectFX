package Main;

import Alphabet.Alphabet;
import Directories.Directories;
import Encryption.Bruteforce;
import Encryption.CaesarCipher;
import Encryption.Decryption;
import Exceptions.IncorrectAlphabetException;
import Exceptions.IncorrectFileException;
import Exceptions.NoMatchesException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private ComboBox<String> alphabetCombobox;

    @FXML
    private AnchorPane anch2;

    @FXML
    private AnchorPane anchor1;

    @FXML
    private Button brutforceButton;

    @FXML
    private Button cipherButton;

    @FXML
    private Button encryptButton;

    @FXML
    private ImageView image1;

    @FXML
    private ComboBox<Integer> keyCombobox;

    @FXML
    private ButtonBar mainButtonBar;

    @FXML
    private Pane mainPane;

    @FXML
    private Pane pane1;

    @FXML
    private Text pathTxt;

    @FXML
    private Text providedText;

    @FXML
    private Button readBtn;

    @FXML
    private TextField textReadField;

    @FXML
    private TextField textWriteField;

    @FXML
    private Rectangle rect1;

    @FXML
    private Button startButton;

    @FXML
    private Text subtitle;

    @FXML
    private Text title;

    @FXML
    private Button writeBtn;

    public Controller() throws IOException {
    }

    @FXML
    void initialize() {
        readBtn.setOnAction(event -> {
            readBtn.setStyle("Green");
            Stage stage = new Stage();
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Directories.originFile = chooser.showOpenDialog(stage);
            if (!textReadField.getText().isEmpty()) {
                textReadField.deleteText(0, textReadField.getText().length());
            }
            if (Directories.originFile != null) {
                textReadField.appendText(Directories.originFile.toString());
            }
        });
        writeBtn.setOnAction(event -> {
            writeBtn.setStyle("Green");
            Stage stage = new Stage();
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Directories.destinyFile = chooser.showOpenDialog(stage);
            if (!textWriteField.getText().isEmpty()) {
                textWriteField.deleteText(0, textWriteField.getText().length());
            }
            if (Directories.destinyFile != null) {
                textWriteField.appendText(Directories.destinyFile.toString());
            }
        });
        startButton.setOnAction(event -> {
            if (CaesarCipher.isChoiceEncryption) {
                try {
                    Main.encryptionStart();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успешное выполнение программы");
                    alert.setHeaderText(null);
                    alert.setContentText("Файл успешно зашифрован");
                    alert.showAndWait();
                } catch (IOException | IncorrectAlphabetException | IncorrectFileException e) {
                    throw new RuntimeException(e);
                }
            } else if (Decryption.isChoiceDecryption) {
                try {
                    Main.decryptionStart();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успешное выполнение программы");
                    alert.setHeaderText(null);
                    alert.setContentText("Файл успешно дешифрован");
                    alert.showAndWait();
                } catch (IncorrectAlphabetException | IOException | IncorrectFileException e) {
                    throw new RuntimeException(e);
                }
            } else if (Bruteforce.isChoiceBruteforce) {
                try {
                    Main.bruteforceStart();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успешное выполнение программы");
                    alert.setHeaderText(null);
                    alert.setContentText("Файл успешно взломан");
                    alert.showAndWait();
                } catch (NoMatchesException | IncorrectFileException | IOException | IncorrectAlphabetException e) {
                    throw new RuntimeException(e);
                }
            }
            if ((!CaesarCipher.isChoiceEncryption) && (!Decryption.isChoiceDecryption) && (!Bruteforce.isChoiceBruteforce)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Опция не выбрана");
                alert.showAndWait();
            }
            if (Directories.originFile == null || Directories.destinyFile == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Файл не выбран");
                alert.showAndWait();
            }
        });
        cipherButton.setOnAction(actionEvent -> {
            CaesarCipher.isChoiceEncryption = true;
            Decryption.isChoiceDecryption = false;
            Bruteforce.isChoiceBruteforce = false;
            cipherButton.setStyle("Green");
            keyCombobox.setDisable(false);

        });
        encryptButton.setOnAction(actionEvent -> {
            CaesarCipher.isChoiceEncryption = false;
            Decryption.isChoiceDecryption = true;
            Bruteforce.isChoiceBruteforce = false;
            encryptButton.setStyle("Green");
            keyCombobox.setDisable(false);
        });
        brutforceButton.setOnAction(actionEvent -> {
            CaesarCipher.isChoiceEncryption = false;
            Decryption.isChoiceDecryption = false;
            Bruteforce.isChoiceBruteforce = true;
            brutforceButton.setStyle("Green");
            keyCombobox.setDisable(true);
        });
        alphabetCombobox.setItems(FXCollections.observableArrayList("Кириллица", "Латиница"));
        if (alphabetCombobox.getSelectionModel().getSelectedItem() == null) {
            CaesarCipher.alphabet = Alphabet.RUSSIAN;
            CaesarCipher.key = 0;
            keyCombobox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                    13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));
        }
        alphabetCombobox.setOnAction(actionEvent -> {
            keyCombobox.setValue(0);
            if (alphabetCombobox.getSelectionModel().getSelectedItem().equals("Кириллица")) {
                CaesarCipher.alphabet = Alphabet.RUSSIAN;
                keyCombobox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32));
            } else if (alphabetCombobox.getSelectionModel().getSelectedItem().equals("Латиница")) {
                CaesarCipher.alphabet = Alphabet.LATIN;
                keyCombobox.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25));
            }
        });
        keyCombobox.setOnAction(actionEvent -> {
            if (keyCombobox.getSelectionModel().getSelectedItem() != null) {
                CaesarCipher.key = keyCombobox.getSelectionModel().getSelectedItem();
            }
        });
    }
}


