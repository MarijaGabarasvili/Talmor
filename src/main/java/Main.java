import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    class fileUtils {

        static String read(String fileName) {
            try {
                FileInputStream file = new FileInputStream(fileName);
                byte[] buffer = new byte[file.available()];
                file.read(buffer, 0, file.available());
                file.close();
                return new String(buffer);
            } catch (Exception e) {
                System.out.println(e);
                return e.getMessage();
            }
        }

        //
        static void write(String fileName, String file) {
            try {
                FileWriter fw = new FileWriter(fileName);
                fw.write(file + "\n");
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public static void comp(Scanner scanner) {
            System.out.print("source file name: ");
            String sourceFileName = scanner.next();
            System.out.print("archive name: ");
            String resultFileName = scanner.next();

            comp(sourceFileName, resultFileName);
        }

        public static String comp(String sourceFileName, String resultFileName) {
            StringBuilder sb = new StringBuilder();

            ByteBuffer sb1 = ByteBuffer.allocate(sb.length());
            for (int i = 0; i < sb.length(); i += 8) {
                if (i + 8 > sb.length()) {
                    String strii = sb.substring(i, sb.length());
                    sb1.put((byte) Integer.parseInt(strii, 2));
                } else {
                    String strii = sb.substring(i, i + 8);
                    sb1.put((byte) Integer.parseInt(strii, 2));
                }

            }
            sb1.rewind();
            Charset cs = Charset.forName("UTF-8");
            CharBuffer cb = cs.decode(sb1);
            write(resultFileName, cb.toString());
            // it needs to return Compressed successfully or Failed to compress
            return "unimplemented";
        }

        public static void decomp(Scanner scanner) {
            System.out.print("archive name: ");
            String sourceFileName = scanner.next();
            System.out.print("file name: ");
            String resultFileName = scanner.next();
            decomp(sourceFileName, resultFileName);
        }

        public static String decomp(String sourceFileName, String resultFileName) {
            String text = read(sourceFileName);
            ASCIIToBin(text);

            return "unimplemented";
        }

        public static String ASCIIToBin(String ASCII) {
            byte[] bytes = ASCII.toString().getBytes();
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes) {
                int val = b;
                for (int i = 0; i < 8; i++) {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
            }
            return binary.toString();
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
            } catch (IOException exeption) {
                System.out.println(exeption.getMessage());
                return false;
            }
        }

        public static String about() {
            return "Marija Gabarašvili 17. grupa 221RDB236\nAnastasija Bakalova 17.grupa 221RDB324\nPāvels Pozdejevs 15. grupa 221RDB438\nDmitrijs Astrošaps 10.grupa 221RDB193\nLukas Pahomovs 14. grupa 221RDB047";
        }
    }

    class List {
        LinkedList<Symbol> characterList = new LinkedList<Symbol>();

        public void add(Symbol symbol) {
            characterList.add(symbol);
        }

        public void sort() {
            int position = 0;
            for (int index = 0; index < Math.floorDiv(characterList.size(), 2); index++) {
                position = makingsort(characterList, index, characterList.size());
                if (position != index) {
                    index = position;
                } else {
                    index = position;
                }
            }
        }

        public Integer makingsort(LinkedList<Symbol> characterList, Integer index, Integer size) {
            Symbol character;
            Symbol othercharacter;
            int position = index;
            int leftpos = 2 * index + 1;
            int rightpos = 2 * index + 2;
            if ((leftpos < size) && (characterList.get(leftpos).quant < characterList.get(position).quant)) {
                position = leftpos;
            }
            if ((rightpos < size) && (characterList.get(rightpos).quant < characterList.get(position).quant)) {
                position = rightpos;
            }
            if (index != position) {
                character = characterList.get(position);
                othercharacter = characterList.get(index);
                characterList.remove(position);
                characterList.remove(characterList.get(index));
                characterList.add(index, character);
                characterList.add(position, othercharacter);
                return position;
            } else {
                return index;
            }
        }
    }

    static class Symbol {
        char character;
        int quant;

        public Symbol(char c, int q) {
            this.character = c;
            this.quant = q;
        }

        public Integer count(String file) {
            for (int index = 0; index < file.length(); index++) {
                if (file.charAt(index) == this.character) {
                    this.quant++;
                }
            }
            return this.quant;
        }
    }

    class Tree {
        LinkedList<Node> tree = new LinkedList<Node>();

        public void create(List lists) {
            this.tree.add(new Node(false, -1, lists.characterList.get(0)));
            int j = 0;
            for (int i = 1; i < lists.characterList.size(); i++) {
                if (this.tree.get(j).sym.quant < lists.characterList.get(i).quant) {

                    this.tree.set(j, new Node(false, -1, this.tree.get(j).sym));

                    this.tree.add(new Node(true, -1, lists.characterList.get(i)));
                    j = j + 1;
                } else {

                    this.tree.set(j, new Node(true, -1, this.tree.get(j).sym));

                    this.tree.add(new Node(false, -1, lists.characterList.get(i)));
                    j = j + 1;
                }

                this.tree.add(new Node(false, -1,
                        new Symbol('\0', this.tree.get(j).sym.quant + this.tree.get(j - 1).sym.quant)));

                this.tree.set(j, new Node(this.tree.get(j).position, j + 1, this.tree.get(j).sym));

                this.tree.set(j - 1, new Node(this.tree.get(j - 1).position, j + 1, this.tree.get(j - 1).sym));
                j = j + 1;
            }

        }

        public HashMap<Character, String> toMap() {
            HashMap<Character, String> map = new HashMap<Character, String>();
            for (int i = 0; i < this.tree.size(); i++) {
                String code = "";
                int j = i;
                if (this.tree.get(i).sym.character != '\0') {
                    do {
                        if (this.tree.get(j).position) {
                            code = code + "1";
                        } else {
                            code = code + "0";
                        }
                        j = this.tree.get(j).parent;
                    } while (this.tree.get(j).parent != -1);

                    String codeFin = "";
                    for (int k = code.length() - 1; k >= 0; k--) {
                        codeFin = codeFin + code.charAt(k);
                    }
                    map.put(this.tree.get(i).sym.character, codeFin);
                }

            }
            return map;
        }

        public HashMap<String, Character> toMapReverse() {
            HashMap<String, Character> map = new HashMap<String, Character>();
            for (int i = 0; i < this.tree.size(); i++) {
                String code = "";
                int j = i;
                if (this.tree.get(i).sym.character != '\0') {
                    do {
                        if (this.tree.get(j).position) {
                            code = code + "1";
                        } else {
                            code = code + "0";
                        }
                        j = this.tree.get(j).parent;
                    } while (this.tree.get(j).parent != -1);

                    String codeFin = "";
                    for (int k = code.length() - 1; k >= 0; k--) {
                        codeFin = codeFin + code.charAt(k);
                    }
                    map.put(codeFin, this.tree.get(i).sym.character);
                }

            }
            return map;
        }

    }

    class Node {
        boolean position; // false-left true-right
        int parent; // no parent, then -1
        Symbol sym;

        public Node(boolean pos, int p, Symbol s) {
            this.position = pos;
            this.parent = p;
            this.sym = s;
        }

        public void print() {
            System.out.println(
                    this.position + " " + this.parent + " " + this.sym.character + " " + this.sym.quant + " aaa ");
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

        // gui version
        launch(args);

        // terminal version
        // try {
        // System.out.println(loop(""));
        // } catch (Exception exeption) {
        // System.out.println(exeption.getMessage());
        // }

    }

    class File {
        public String size;
        public String name;
        public String path;
        public String extension;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        File activeFile = new File();
        File secondaryFile = new File();

        String statisticsPlaceholder = "Name: N/A\nExtension: N/A\nSize: N/A bytes\nPath: N/A";

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label activeFileLabel = new Label("Active file");
        activeFileLabel.setTextFill(Color.web("#c2ebff"));
        activeFileLabel.setFont(Font.font(20));
        GridPane.setConstraints(activeFileLabel, 0, 0);
        grid.getChildren().add(activeFileLabel);

        Text activeFileStatistics = new Text(statisticsPlaceholder);
        activeFileStatistics.setFill(Color.web("#c2ebff"));
        GridPane.setConstraints(activeFileStatistics, 0, 1);
        grid.getChildren().add(activeFileStatistics);

        TextField activeFileField = new TextField();
        activeFileField.setPromptText("Active file path");
        GridPane.setConstraints(activeFileField, 0, 2);
        grid.getChildren().add(activeFileField);

        Label secondaryFileLabel = new Label("Secondary file");
        secondaryFileLabel.setTextFill(Color.web("#c2ebff"));
        secondaryFileLabel.setFont(Font.font(20));
        GridPane.setConstraints(secondaryFileLabel, 1, 0);
        grid.getChildren().add(secondaryFileLabel);

        Text secondaryFileStatistics = new Text(statisticsPlaceholder);
        secondaryFileStatistics.setFill(Color.web("#c2ebff"));
        GridPane.setConstraints(secondaryFileStatistics, 1, 1);
        grid.getChildren().add(secondaryFileStatistics);

        TextField secondaryFileField = new TextField();
        secondaryFileField.setPromptText("Secondary file path");
        GridPane.setConstraints(secondaryFileField, 1, 2);
        grid.getChildren().add(secondaryFileField);

        Text equality = new Text("Files are not equal");
        equality.setFill(Color.web("#c2ebff"));
        GridPane.setConstraints(equality, 2, 1);
        grid.getChildren().add(equality);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setVgap(5);
        buttonGrid.setHgap(5);

        Button compileButton = new Button("Compile file");
        compileButton.setOnMouseClicked(actionEvent -> {
            compileButton(activeFileField, secondaryFileField);
        });
        compileButton.setOnAction(actionEvent -> {
            compileButton(activeFileField, secondaryFileField);
        });
        GridPane.setConstraints(compileButton, 0, 0);
        buttonGrid.getChildren().add(compileButton);

        Button decompileButton = new Button("Decompile file");

        decompileButton.setOnAction(actionEvent -> {
            decompileButton(activeFileField, secondaryFileField);
        });
        GridPane.setConstraints(decompileButton, 1, 0);
        buttonGrid.getChildren().add(decompileButton);

        Button statisticsButton = new Button("Refresh statistics");
        statisticsButton.setOnAction(actionEvent -> {
            statisticsButton(primaryStage, activeFile, secondaryFile, statisticsPlaceholder, activeFileStatistics,
                    activeFileField, secondaryFileStatistics, secondaryFileField, equality);

        });
        GridPane.setConstraints(statisticsButton, 2, 0);
        buttonGrid.getChildren().add(statisticsButton);

        Button aboutButton = new Button("Credits");
        aboutButton.setOnMouseClicked(actionEvent -> {
            aboutButton();
        });
        aboutButton.setOnAction(actionEvent -> {
            aboutButton();
        });
        GridPane.setConstraints(aboutButton, 3, 0);
        buttonGrid.getChildren().add(aboutButton);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(actionEvent -> {
            System.exit(0);
        });
        GridPane.setConstraints(exitButton, 4, 0);
        buttonGrid.getChildren().add(exitButton);

        Group root = new Group(grid, buttonGrid);
        Scene scene = new Scene(root, Color.web("#416573"));
        primaryStage.setScene(scene);
        // Setting Title to the stage
        primaryStage.setTitle("Talmor compressor");
        // Displaying the contents of the stage

        activeFileField.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                refresh(activeFile, activeFileStatistics, statisticsPlaceholder, activeFileField.getText());
                primaryStage.sizeToScene();
            }
        });

        secondaryFileField.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode() == KeyCode.ENTER) {
                refresh(secondaryFile, secondaryFileStatistics, statisticsPlaceholder, secondaryFileField.getText());
                primaryStage.sizeToScene();
            }
        });

        primaryStage.show();

    }

    private void aboutButton() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(fileUtils.about());
        alert.setTitle("Credits");
        alert.setHeaderText(null);
        alert.show();
    }

    private void statisticsButton(Stage primaryStage, File activeFile, File secondaryFile, String statisticsPlaceholder,
            Text activeFileStatistics, TextField activeFileField, Text secondaryFileStatistics,
            TextField secondaryFileField, Text equality) {
        refresh(activeFile, activeFileStatistics, statisticsPlaceholder, activeFileField.getText());
        refresh(secondaryFile, secondaryFileStatistics, statisticsPlaceholder, secondaryFileField.getText());
        try {
            equality.setText(
                    (!activeFileField.getText().equals("") && !secondaryFileField.getText().equals(""))
                            ? fileUtils.equal(activeFile.path, secondaryFile.path) ? "Files are equal"
                                    : "Files are not equal"
                            : "Files are not equal");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
        primaryStage.sizeToScene();
    }

    private void decompileButton(TextField activeFileField, TextField secondaryFileField) {
        if (!activeFileField.getText().equals("")) {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        fileUtils.decomp(activeFileField.getText(),
                                secondaryFileField.getText().equals("")
                                        ? activeFileField.getText().contains(".talmor")
                                                ? activeFileField.getText().substring(0,
                                                        activeFileField.getText().length() - 7)
                                                : activeFileField.getText()
                                        : secondaryFileField.getText()));
                alert.setHeaderText(null);
                alert.showAndWait();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void compileButton(TextField activeFileField, TextField secondaryFileField) {
        if (!activeFileField.getText().equals("")) {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        fileUtils.comp(activeFileField.getText(),
                                secondaryFileField.getText().equals("") ? activeFileField.getText() + ".talmor"
                                        : secondaryFileField.getText()));
                alert.setHeaderText(null);
                alert.showAndWait();
            } catch (Exception exeption) {
                exeption.printStackTrace();
            }
        }
    }

    static void refresh(File file, Text fileStatistics, String statisticsPlaceholder, String path) {
        try {
            if (path.equals("")) {
                fileStatistics.setText(statisticsPlaceholder);
                return;
            }
            file.path = path;
            file.size = fileUtils.size(path);
            if (!path.contains("."))
                file.extension = "N/A";
            else
                file.extension = path.substring(path.lastIndexOf(".") + 1);
            try {
                file.name = path.substring(path.lastIndexOf("\\") + 1);
                file.name = file.name.substring(0, file.name.lastIndexOf("."));
            } catch (Exception exception) {
                file.name = path;
            }
            fileStatistics.setText("Name: " + file.name + "\nExtension: " + file.extension
                    + "\nSize: " + file.size + " bytes" + "\nPath: " + file.path);

            return;

        } catch (Exception exeption) {
            new Alert(AlertType.ERROR, exeption.getMessage()).showAndWait();
            return;
        }

    }
}
