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

class fileUtils{
    public static void comp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}

	public static void decomp(String sourceFile, String resultFile) {
		// TODO: implement this method
	}
	
	public static void size(String sourceFile) {
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static boolean equal(String firstFile, String secondFile) {
		try {
			FileInputStream f1 = new FileInputStream(firstFile);
			FileInputStream f2 = new FileInputStream(secondFile);
			int k1, k2;
			byte[] buf1 = new byte[1000];
			byte[] buf2 = new byte[1000];
			do {
				k1 = f1.read(buf1);
				k2 = f2.read(buf2);
				if (k1 != k2) {
					f1.close();
					f2.close();
					return false;
				}
				for (int i=0; i<k1; i++) {
					if (buf1[i] != buf2[i]) {
						f1.close();
						f2.close();
						return false;
					}
						
				}
			} while (k1 == 0 && k2 == 0);
			f1.close();
			f2.close();
			return true;
		}
		catch (IOException ex) {
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
		String choiseStr;
		String sourceFile, resultFile, firstFile, secondFile;
		
		loop: while (true) {
			
			choiseStr = scanner.next();
								
			switch (choiseStr) {
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
				firstFile = scanner.next();
				System.out.print("second file name: ");
				secondFile = scanner.next();
				System.out.println(fileUtils.equal(firstFile, secondFile));
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
		
		
	}

	

}
