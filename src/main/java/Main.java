import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class Main extends Application {
    class fileUtils {
        public static void comp(Scanner scanner) {
            System.out.print("source file name: ");
            String sourceFileName = scanner.next();
            System.out.print("archive name: ");
            String resultFileName = scanner.next();
            // TODO: implement this method
        }

        public static void decomp(Scanner scanner) {
            System.out.print("archive name: ");
            String sourceFileName = scanner.next();
            System.out.print("file name: ");
            String resultFileName = scanner.next();
            // TODO: implement this method
        }

        public static String size(Scanner scanner) {
            System.out.print("file name: ");
            String sourceFileName = scanner.next();
            try {
                return size(sourceFileName);
            } catch (IOException exeption) {
                return exeption.getMessage();
            }
        }

        public static String size(String sourceFileName) throws IOException {
            FileInputStream sourceFile = new FileInputStream(sourceFileName);
            String result = Integer.toString(sourceFile.available());
            sourceFile.close();
            return result;

        }

        public static boolean equal(Scanner scanner) {
            System.out.print("first file name: ");
            String firstFileName = scanner.next();
            System.out.print("second file name: ");
            String secondFileName = scanner.next();
            return equal(firstFileName, secondFileName);

        }

        public static boolean equal(String firstFileName, String secondFileName) {
            try {
                FileInputStream firstFile = new FileInputStream(firstFileName);
                FileInputStream secondFile = new FileInputStream(secondFileName);
                int firstKey, secondKey;
                byte[] firstFileBuffer = new byte[1000];
                byte[] secondFileBuffer = new byte[1000];
                do {
                    firstKey = firstFile.read(firstFileBuffer);
                    secondKey = secondFile.read(secondFileBuffer);
                    if (firstKey != secondKey) {
                        firstFile.close();
                        secondFile.close();
                        return false;
                    }
                    for (int index = 0; index < firstKey; index++) {
                        if (firstFileBuffer[index] != secondFileBuffer[index]) {
                            firstFile.close();
                            secondFile.close();
                            return false;
                        }

                    }
                } while (firstKey == 0 && secondKey == 0);
                firstFile.close();
                secondFile.close();
                return true;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }

        public static String about() {
            // TODO insert information about authors
            return "Marija Gabarašvili 17. grupa 221RDB236\nAnastasija Bakalova 17.grupa 221RDB324\nPāvels Pozdejevs 15. grupa 221RDB438\nDmitrijs Astrošaps 10.grupa 221RDB193\nLukas Pahomovs 14. grupa 221RDB047";
        }
    }

    class List {
        LinkedList<Symbol> characterList = new LinkedList<Symbol>();

        public void add(Symbol symbol) {
            // TODO: implement this method
        }
    }

    class Symbol {
        char character;
        int quant;

        public void count() {
            // TODO: implement this method
        }
    }

    class Tree {
        LinkedList<Node> tree = new LinkedList<Node>();

        public void create(List list) {
            // TODO: implement this method
        }

    }

    class Node {
        boolean position;
        int parent;

        public void create() {
            // TODO: implement this method
        }
    }

    static String loop(String choise) {
        Scanner scanner = new Scanner(System.in);
        Boolean test = !(choise.equals(""));

        loop: while (true) {

            if (!test)
                choise = scanner.next();

            switch (choise) {
                case "comp":
                    fileUtils.comp(scanner);
                    break;
                case "decomp":
                    fileUtils.decomp(scanner);
                    break;
                case "size":
                    System.out.println(fileUtils.size(scanner));
                    break;
                case "equal":
                    System.out.println(fileUtils.equal(scanner));
                    break;
                case "about":
                    System.out.print(fileUtils.about());
                    break;
                case "exit":
                    break loop;
            }

            choise = test ? "exit" : "";
        }

        scanner.close();
        return "Run finished successfully";
    }

    public static void main(String[] args) {

        launch(args);

        try {
            System.out.println(loop(""));
        } catch (Exception exeption) {
            System.out.println(exeption.getMessage());
        }

    }

    class file {
        public String size;
        public String name;
        public String path;
        public String extension;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        file activeFile = new file();
        file secondaryFile = new file();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label activeFileLabel = new Label("Active file");
        activeFileLabel.setTextFill(Color.web("#c2ebff"));
        activeFileLabel.setFont(Font.font( 20));
        GridPane.setConstraints(activeFileLabel, 0, 0);
        grid.getChildren().add(activeFileLabel);

        Text activeFileStatistics = new Text(10, 40, "Stats will be here");
        activeFileStatistics.setFill(Color.web("#c2ebff"));

        GridPane.setConstraints(activeFileStatistics, 0, 1);
        grid.getChildren().add(activeFileStatistics);

        TextField activeFileField = new TextField();
        activeFileField.setPromptText("Active file");
        GridPane.setConstraints(activeFileField, 0, 2);
        grid.getChildren().add(activeFileField);

        ToolBar toolBar = new ToolBar();

        Button compileButton = new Button("Compile file");
        toolBar.getItems().add(compileButton);
        Button decompileButton = new Button("Decompile file");
        toolBar.getItems().add(decompileButton);

        toolBar.getItems().add(new Separator());

        Button sizeButton = new Button("Refresh statistics");
        toolBar.getItems().add(sizeButton);

        sizeButton.setOnAction(actionEvent -> {
            // System.out.println(fileUtils.size(activeFile.path));
            try {
                activeFile.path = activeFileField.getText();
                activeFile.size = fileUtils.size(activeFile.path);
                activeFile.extension = activeFile.path.substring(activeFile.path.lastIndexOf(".") + 1);
                activeFile.name = activeFile.path.substring(activeFile.path.lastIndexOf("\\") + 1);
                activeFile.name = activeFile.name.substring(0, activeFile.name.lastIndexOf("."));
                activeFileStatistics.setText("Name: " + activeFile.name + "\nExtension: " + activeFile.extension
                        + "\nSize: " + activeFile.size + " bytes" + "\nPath: " + activeFile.path);
            } catch (Exception exeption) {
                activeFileStatistics.setText("Error: " + exeption.getMessage());
            }
        });

        Button checkButton = new Button("Check files equality");
        toolBar.getItems().add(checkButton);

        toolBar.getItems().add(new Separator());

        Button aboutButton = new Button("Credits");
        toolBar.getItems().add(aboutButton);
        Alert alert = new Alert(AlertType.NONE);

        aboutButton.setOnAction(actionEvent -> {
            // System.out.println(fileUtils.about());
            alert.setAlertType(AlertType.INFORMATION);
            alert.setContentText(fileUtils.about());
            alert.setTitle("Credits");
            alert.setHeaderText("Authors:");
            alert.show();
        });

        toolBar.getItems().add(new Separator());

        Button exitButton = new Button("Exit");
        toolBar.getItems().add(exitButton);
        exitButton.setOnAction(actionEvent -> {
            System.exit(0);
        });

        Group root = new Group(toolBar, grid);
        Scene scene = new Scene(root, 545, 200, Color.web("#416573"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Talmor compressor");
        primaryStage.show();

    }

}