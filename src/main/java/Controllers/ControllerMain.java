package Controllers;

import BytecodeUtils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerMain {
    private final ControllersUtility controllersUtility = ControllersUtility.getInstance();

    private DirectoryChooser directoryChooser;
    private FileChooser fileChooser;

    @FXML
    private ToggleGroup bytecode_generator;

    @FXML
    private void initialize() {
        directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("").getAbsoluteFile());

        fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("JAVA class files (*.class)", "*.class"));
        fileChooser.setInitialDirectory(new File("").getAbsoluteFile());
    }

    @FXML
    void pickFile(ActionEvent event) {
        List<File> chosenFiles = fileChooser
                .showOpenMultipleDialog(controllersUtility.getPrimaryStage());
        generateBytecodes(chosenFiles, getSelectedBytecodeGenerator());
    }

    @FXML
    void pickDirectory(ActionEvent event) {
        File selectedDirectory = directoryChooser
                .showDialog(controllersUtility.getPrimaryStage());
        List<File> chosenFiles = getFilesInDirectory(selectedDirectory.getAbsolutePath());
        generateBytecodes(chosenFiles, getSelectedBytecodeGenerator());
    }

    private void generateBytecodes(List<File> files, BytecodeGenerator bytecodeGenerator) {
        if (files == null || files.isEmpty()) {
            controllersUtility.showInfoBlocking("No files were found.");
            return;
        }

        Map<String, String> bytecodes;
        try{
            bytecodes = bytecodeGenerator.generateBytecodes(files);
        }
        catch (RuntimeException runtimeException) {
            return;
        }

        Scene scene;
        ControllerFilesView controllerFilesView;
        try {
            FXMLLoader loader = controllersUtility.getResource("FilesView");
            scene = new Scene(loader.load());
            controllerFilesView = loader.getController();
        } catch (IOException e) {
            controllersUtility.showAlertBlocking("Error while loading Files View scene.");
            return;
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(bytecodeGenerator.getName());

        controllerFilesView
                .setFiles(
                        bytecodes.entrySet().stream().map(entry -> {
                            Tab tab = new Tab(entry.getKey());
                            ScrollPane scrollPane = new ScrollPane();
                            Label text = new Label(entry.getValue());

                            scrollPane.setContent(text);
                            scrollPane.setPannable(true);
                            scrollPane.setPadding(new Insets(10, 0, 0, 10));
                            tab.setContent(scrollPane);

                            return tab;
                        }).collect(Collectors.toList()));

        stage.show();
    }

    private BytecodeGenerator getSelectedBytecodeGenerator(){
        String selectedGenerator = ((RadioButton) bytecode_generator.getSelectedToggle()).getText();
        switch (selectedGenerator) {
            case "Javap":
                return new BytecodeGeneratorJavap();
            case "ASM":
                return new BytecodeGeneratorASM();
            case "BCEL":
                return new BytecodeGeneratorBCEL();
            default:
                return new BytecodeGeneratorJavassist();
        }
    }

    private List<File> getFilesInDirectory(String path) {
        try {
            try (Stream<Path> stream = Files.walk(Paths.get(path))) {
                return stream
                        .map(Path::toFile)
                        .filter(f -> f.isFile() && f.getName().toLowerCase().endsWith(".class"))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            controllersUtility.showAlertBlocking("Error occurred while reading from directory.");
            return new ArrayList<>(0);
        }
    }
}
