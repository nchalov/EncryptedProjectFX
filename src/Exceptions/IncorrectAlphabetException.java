package Exceptions;

import javafx.scene.control.Alert;

public class IncorrectAlphabetException extends Exception {
    public IncorrectAlphabetException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неверный алфавит");
        alert.setHeaderText(null);
        alert.setContentText("Пожалуйста, выберите правильный алфавит");
        alert.showAndWait();
    }
}
