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

class fileUtils {
	public static void comp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}

	public static void decomp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}

	public static void size(String sourceFileName) {
		try {
			FileInputStream sourceFile = new FileInputStream(sourceFileName);
			System.out.println("size: " + sourceFile.available());
			sourceFile.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

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

	public static void about() {
		// TODO insert information about authors
		System.out.println("000RDB000 Jānis Programmētājs");
		System.out.println("111RDB111 Ilze Programmētāja");
	}
}

public class App {
	public String getGreeting() {
		return "Hello World!";
	}

	void loop() {
		Scanner scanner = new Scanner(System.in);
		String choise;
		String sourceFile, resultFile, firstFileName, secondFileName;

		loop: while (true) {

			choise = scanner.next();

			switch (choise) {
				case "comp":
					System.out.print("source file name: ");
					sourceFile = scanner.next();
					System.out.print("archive name: ");
					resultFile = scanner.next();
					fileUtils.comp(sourceFile, resultFile);
					break;
				case "decomp":
					System.out.print("archive name: ");
					sourceFile = scanner.next();
					System.out.print("file name: ");
					resultFile = scanner.next();
					fileUtils.decomp(sourceFile, resultFile);
					break;
				case "size":
					System.out.print("file name: ");
					sourceFile = scanner.next();
					fileUtils.size(sourceFile);
					break;
				case "equal":
					System.out.print("first file name: ");
					firstFileName = scanner.next();
					System.out.print("second file name: ");
					secondFileName = scanner.next();
					System.out.println(fileUtils.equal(firstFileName, secondFileName));
					break;
				case "about":
					fileUtils.about();
					break;
				case "exit":
					break loop;
			}
		}

		scanner.close();
	}

	public static void main(String[] args) {

		System.out.println(new App().getGreeting());
		new App().loop();

	}

}
