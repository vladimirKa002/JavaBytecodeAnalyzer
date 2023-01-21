package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;

public class ControllerFilesView extends Controller{

    @FXML
    private TabPane files_view;

    public void setFiles(List<Tab> tabs){
        files_view.getTabs().addAll(tabs);
    }
}