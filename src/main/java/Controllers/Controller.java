package Controllers;

import javafx.stage.Stage;

public abstract class Controller {
    protected final ControllersUtility controllersUtility = ControllersUtility.getInstance();
    protected Stage stage;

    public void setStage (Stage stage){
        this.stage = stage;
    }
}
