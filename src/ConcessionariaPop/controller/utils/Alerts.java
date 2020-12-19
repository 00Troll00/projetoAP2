package ConcessionariaPop.controller.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {
    public static Optional<ButtonType> showConfirmation (String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.OK, ButtonType.CANCEL);
        
        alert.setTitle(title);
        alert.setHeaderText(null);
        
        return alert.showAndWait();
    }
}
