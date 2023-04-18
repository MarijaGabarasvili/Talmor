/*
Marija Gabarašvili 17. grupa 221RDB236
Anastasija Bakalova 17.grupa 221RDB324
Pāvels Pozdejevs 15. grupa 221RDB438
Dmitrijs Astrošaps 10.grupa 221RDB193
Lukas Pahomovs 14. grupa 221RDB047
 */
package talmor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;
import javafx.controls.*;




public class App {
	public String getGreeting() {
		return "Hello World!";
	}

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
				String result= "size: "+Integer.toString(sourceFile.available());
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
			return "Marija Gabarašvili 17. grupa 221RDB236\nAnastasija Bakalova 17.grupa 221RDB324\nPāvels Pozdejevs 15. grupa 221RDB438\nDmitrijs Astrošaps 10.grupa 221RDB193\nLukas Pahomovs 14. grupa 221RDB047" ;
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

	String loop(String choise) {
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

			choise=test?"exit":"";
		}

		scanner.close();
		return "Run finished successfully";
	}

	public static void main(String[] args) {
		System.out.println(new App().getGreeting());
		try {
			System.out.println(new App().loop(""));
		} catch (Exception exeption) {
			System.out.println(exeption.getMessage());
		}

	}

}