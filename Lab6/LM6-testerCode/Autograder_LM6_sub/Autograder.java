import java.io.*;
import java.util.Scanner;

public class Autograder{
	public static void main(String[] args) {
		int marks = 0;
		try {
      		File file = new File("output.txt");
      		File file_res = new File("output_res.txt");
      		Scanner sc_res = new Scanner(file_res);
      		Scanner sc = new Scanner(file);
      		while(sc.hasNextLine() && sc_res.hasNextLine()){
      			String s1 = sc.nextLine();
      			String s2 = sc_res.nextLine();
      			if(s1.compareTo(s2) == 0){
      				marks++;
      			}
      		}
      	}catch(Exception e) {
	    	System.out.println("Exception Occurred:");
	    	e.printStackTrace();
	    }
	    System.out.println("score: "+ marks);
	}
}