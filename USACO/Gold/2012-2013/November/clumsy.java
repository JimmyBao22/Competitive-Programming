
import java.util.*;
import java.io.*;

public class clumsy {

	// http://usaco.org/index.php?page=viewproblem2&cpid=190
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("clumsy.in"));
		PrintWriter out = new PrintWriter(new FileWriter("clumsy.out"));

		char[] arr = in.readLine().toCharArray();
		int op=0;
		int cl=0;
		int change=0;
		for (int i=0; i<arr.length; i++) {
			if (arr[i] == '(') {
				op++;
			}
			else {
				cl++;
			}
			
			if (op < cl) {
				change++;
				op++;
				cl--;
				arr[i] = '(';
			}
		}
		
		int pointer=arr.length-1;
		while (op != cl) {
			if (arr[pointer] == '(') {
				op--;
				cl++;
				arr[pointer] = ')';
				change++;
			}
			pointer--;
		}

		System.out.println(change);
		out.println(change);
		out.close();
	}
}