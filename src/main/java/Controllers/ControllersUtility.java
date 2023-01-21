package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.HashMap;

public class ControllersUtility {
    private static ControllersUtility controllersUtility;

    private final HashMap<String, String> resources = new HashMap<>();

    private Stage primaryStage;

    private ControllersUtility(){}

    public static ControllersUtility getInstance() {
        if (controllersUtility == null) return controllersUtility = new ControllersUtility();
        return controllersUtility;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public FXMLLoader getResource(String key){
        return new FXMLLoader(getClass().getClassLoader().getResource(resources.get(key)));
    }

    public void putResourceName(String key, String value){
        resources.put(key, value);
    }

    public void showAlert(String message){
        Platform.runLater(() -> showAlertBlocking(message));
    }

    public void showAlertBlocking(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showInfo(String message){
        Platform.runLater(() -> showInfoBlocking(message));
    }

    public void showInfoBlocking(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Предупреждение");
        alert.setHeaderText("Предупреждение");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
