import Controllers.ControllersUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    protected final ControllersUtility controllersUtility = ControllersUtility.getInstance();

    // MAVEN: Use package to build

    @Override
    public void start(Stage primaryStage) throws IOException {
        controllersUtility.setPrimaryStage(primaryStage);

        FXMLLoader fxmlLoaderMain = new FXMLLoader(getClass().getClassLoader().getResource("View_Main.fxml"));
        controllersUtility.putResourceName("FilesView", "View_FilesView.fxml");

        Scene scene = new Scene(fxmlLoaderMain.load());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Java Bytecode Analyzer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}
