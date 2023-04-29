import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

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

        static void write(String fileName, String file) {

            try {
                File file1 = new File(fileName);
                if (file1.createNewFile()) {
                    System.out.println("File created: " + file1.getName());
                } else {
                    System.out.println("File already exists.");
                }
                FileWriter fw = new FileWriter(fileName);
                fw.write(file.trim());
                fw.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        static void writeComp(String fileName, char[] file) {

            try {
                File file1 = new File(fileName);
                if (file1.createNewFile()) {
                    System.out.println("File created: " + file1.getName());
                } else {
                    System.out.println("File already exists.");
                }
                FileWriter fw = new FileWriter(fileName);
                for (int i = 0; i < file.length; i++) {
                    fw.write(file[i]);
                }
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
            String file = read(sourceFileName);
            List listFile = new List(file);
            listFile.sort();
            Tree treeFile = new Tree(listFile);
            HashMap<Character, String> map = treeFile.toMap();
            String fileByte = "";
            for (int i = 0; i < file.length(); i++) {
                fileByte = fileByte + map.get(file.charAt(i));
            }
            Charset cs = Charset.forName("ISO-8859-1");
            int len = 0;
            if (fileByte.length() % 8 != 0) {
                len = fileByte.length() / 8 + 1;
            } else {
                len = fileByte.length() / 8;
            }
            ByteBuffer fileByte1 = ByteBuffer.allocate(len);
            for (int i = 0; i < fileByte.length(); i += 8) {
                if (i + 8 > fileByte.length()) {
                    String strii = fileByte.substring(i, fileByte.length());
                    for (int j = strii.length(); j < 8; j++) {
                        strii = strii + "0";
                    }
                    System.out.println(strii);
                    fileByte1.put((byte) Integer.parseInt(strii, 2));
                } else {
                    String strii = fileByte.substring(i, i + 8);
                    fileByte1.put((byte) Integer.parseInt(strii, 2));
                }

            }
            fileByte1.rewind();
            CharBuffer cb = cs.decode(fileByte1);

            writeComp(resultFileName, cb.array());
            // it needs to return Compressed successfully or Failed to compress
            HashMap<String, Character> mapToFile = treeFile.toMapReverse();
            String mapToFileString = toString(mapToFile, fileByte.length());
            write(resultFileName + ".map", mapToFileString);

            return "success";
        }

        public static String toString(HashMap<String, Character> map, int len) {
            String result = String.valueOf(len) + "\n";
            for (String key : map.keySet()) {
                result = result + key + ":" + map.get(key) + "\n";
            }
            return result;
        }

        public static void decomp(Scanner scanner) {
            System.out.print("archive name: ");
            String sourceFileName = scanner.next();
            System.out.print("file name: ");
            String resultFileName = scanner.next();
            decomp(sourceFileName, resultFileName);
        }

        public static String decomp(String sourceFileName, String resultFileName) {
            HashMap<String, Character> map = toMap(sourceFileName + ".map");
            int len = MapLen(sourceFileName + ".map");
            String text = read(sourceFileName);
            String Bin = ASCIIToBin(text);
            String Bytefile = "";
            Bin = Bin.substring(0, len);
            for (int i = 0;; i++) {
                String str = "";
                for (int j = 0;; j++) {
                    str = str + Bin.charAt(j);
                    if (map.containsKey(str)) {
                        Bytefile = Bytefile + map.get(str);
                        Bin = Bin.substring(j + 1);
                        break;
                    }
                }
                if (Bin.length() == 0) {
                    break;
                }

            }

            write(resultFileName, Bytefile);

            return "success";
        }

        public static String ASCIIToBin(String ASCII) {
            byte arr[] = ASCII.getBytes(Charset.forName("ISO-8859-1"));
            String s = "";
            for (int i = 0; i < arr.length; i++) {
                s = s + String.format("%8s", Integer.toBinaryString(arr[i] & 0xFF)).replace(' ', '0');
            }
            return s;
        }

        public static HashMap<String, Character> toMap(String sourceFileName) {
            String file = read(sourceFileName);
            HashMap<String, Character> map = new HashMap<String, Character>();
            String[] lines2 = file.split("\n");
            String[] lines = new String[lines2.length - 1];
            for (int i = 1; i < lines2.length; i++) {
                lines[i - 1] = lines2[i];
            }
            for (String line : lines) {
                String[] keyValue = line.split(":");
                map.put(keyValue[0], keyValue[1].charAt(0));
            }
            return map;
        }

        public static Integer MapLen(String sourceFileName) {
            String file = read(sourceFileName);
            // HashMap<String, Character> map = new HashMap<String, Character>();
            String[] lines2 = file.split("\n");
            String line = lines2[0];
            return Integer.parseInt(line);
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

    public static class List {
        LinkedList<Symbol> characterList = new LinkedList<Symbol>();

        public List(String Str) {
            for (int i = 0; i < Str.length(); i++) {
                Symbol symb = new Symbol(Str.charAt(i), 0);
                symb.count(Str);
                boolean tru = true;
                for (int j = 0; j < this.characterList.size(); j++) {
                    if (this.characterList.get(j).character == Str.charAt(i)) {
                        tru = false;
                    }
                }
                if (tru) {
                    this.characterList.add(symb);
                }
            }
        }

        public void sort() {
            for (int i = 1; i < characterList.size(); i++) {
                Symbol character = new Symbol(this.characterList.get(i).character, this.characterList.get(i).quant);
                int a = i;
                while (a != 0) {
                    int position = a - 1;
                    if (character.quant < this.characterList.get(position).quant) {

                        Symbol othercharacter = new Symbol(this.characterList.get(position).character,
                                this.characterList.get(position).quant);
                        this.characterList.remove(a);
                        this.characterList.remove(position);
                        this.characterList.add(a - 1, othercharacter);
                        this.characterList.add(position, character);
                        character = this.characterList.get(position);
                        a = a - 1;
                        // startposition = a;
                    } else {
                        a = a - 1;
                    }
                }
            }
        }
    }

    public static class Symbol {
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

    public static class Tree {
        LinkedList<Node> tree = new LinkedList<Node>();

        public Tree(List lists) {
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
            // for (int i = 0; i < this.tree.size(); i++){
            // this.tree.get(i).print();
            // }
            return map;
        }

    }

    static class Node {
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

        while (true) {

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
                    scanner.close();
                    System.exit(0);
            }

            choise = test ? "exit" : "";
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'gui' to run GUI version or 'terminal' to run terminal version");

        String choise = "gui";
        if (scanner.hasNextLine()) {
            choise = scanner.nextLine();
        }
        if (choise.equals("gui")) {
            scanner.close();
        } else {
            System.out.println(loop(""));
            scanner.close();
            System.exit(0);
        }

        launch(args);
        // terminal version
        // try {
        // System.out.println(loop(""));
        // } catch (Exception exeption) {
        // System.out.println(exeption.getMessage());
        // }

    }

    class FileData {
        public String size;
        public String name;
        public String path;
        public String extension;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileData activeFile = new FileData();
        FileData secondaryFile = new FileData();

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

    private void statisticsButton(Stage primaryStage, FileData activeFile, FileData secondaryFile,
            String statisticsPlaceholder,
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

    static void refresh(FileData file, Text fileStatistics, String statisticsPlaceholder, String path) {
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
