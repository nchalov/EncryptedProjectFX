package Exceptions;

import javafx.scene.control.Alert;

public class IncorrectFileException extends Exception {
    public IncorrectFileException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неверный файл");
        alert.setHeaderText(null);
        alert.setContentText("Файлы записи и чтения должны быть разными!");
        alert.showAndWait();
    }
}
