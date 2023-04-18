import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
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
                FileInputStream sourceFile = new FileInputStream(sourceFileName);
                String result = "size: " + Integer.toString(sourceFile.available());
                sourceFile.close();
                return result;
            } catch (IOException ex) {
                return ex.getMessage();
            }

        }

        public static boolean equal(Scanner scanner) {
            System.out.print("first file name: ");
            String firstFileName = scanner.next();
            System.out.print("second file name: ");
            String secondFileName = scanner.next();
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
	
		public void create(List lists) {
      this.tree.add(new Node(false,-1,lists.characterlist.get(0)));
      int j=0;
			for(int i=1; i<lists.characterlist.size();i++){
        if(this.tree.get(j).sym.quant<lists.characterlist.get(i).quant){
          
          this.tree.set(j,new Node(false,-1,this.tree.get(j).sym));
          
          this.tree.add(new Node(true, -1, lists.characterlist.get(i)));
          j=j+1;
        }else{
          
          this.tree.set(j, new Node(true,-1,this.tree.get(j).sym));
          
          this.tree.add(new Node(false, -1, lists.characterlist.get(i)));
          j=j+1;
        }
        
        this.tree.add(new Node(false, -1, new Symbol('\0' ,this.tree.get(j).sym.quant+this.tree.get(j-1).sym.quant)));
        
        this.tree.set(j, new Node(this.tree.get(j).position, j+1, this.tree.get(j).sym));
        
        this.tree.set(j-1, new Node(this.tree.get(j-1).position, j+1, this.tree.get(j-1).sym));
        j=j+1;
      }

	}

  public HashMap<Character, String> toMap(){
    HashMap<Character, String> map = new HashMap<Character, String>();
    for (int i = 0; i<this.tree.size(); i++){
      String code="";
      int j=i;
      if(this.tree.get(i).sym.character!='\0'){
        do{
            if(this.tree.get(j).position){
                code=code+"1";
            }else{
                code=code+"0";
            }
            j=this.tree.get(j).parent;
        }while(this.tree.get(j).parent!=-1);

        String codeFin="";
        for(int k=code.length()-1; k>=0;k--){
            codeFin=codeFin+code.charAt(k);
        }
        map.put(this.tree.get(i).sym.character, codeFin);
      }
      
    }

    return map;
  }
	
}

class Node {
    boolean position; //false-left true-right
    int parent; //no parent, then -1
Symbol sym;

    public Node(boolean pos, int p, Symbol s){
        this.position=pos;
  this.parent=p;
  this.sym=s;
    }
public void print(){
  System.out.println(this.position+" "+this.parent+" "+this.sym.character+" "+this.sym.quant+" aaa ");
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Instantiating the group class
        Group root = new Group();
        //Instantiating the Scene class
        Scene scene = new Scene(root, 595, 300, Color.web("#416573"));
        //Setting the scene to the Stage
        primaryStage.setScene(scene);
        //Setting Title to the stage
        primaryStage.setTitle("Talmor compressor");
        //Displaying the contents of the stage
        primaryStage.show();
    }
}