package Exceptions;

import javafx.scene.control.Alert;

public class NoMatchesException extends Exception {
    public NoMatchesException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Не найдено совпадений");
        alert.setHeaderText(null);
        alert.setContentText("Пожалуйста, выберите другой стартовый ключ или убедитесь, что текст корректен");
        alert.showAndWait();
    }
}
