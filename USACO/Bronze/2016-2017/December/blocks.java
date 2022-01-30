import java.util.*;
import java.io.*;

public class blocks {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=664

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("blocks.in"));
		
		int n = in.nextInt();
		String[] t = new String[n];
		in.nextLine(); //first line empty space?
		for (int i = 0; i < n; i++) {
			t[i] = in.nextLine();
		}
		in.close();		
		
				
		int[] f = new int[26]; //this is the final one adding up all the #'s
		
		for (int i = 0; i < 26; i++) {
			char letter = (char)(i + 'a');
			for (int j = 0; j < n; j++) {
				f[i] += check1(t[j], letter);
			}
		}
		
		PrintWriter out = new PrintWriter(new File("blocks.out"));
		
		for (int j = 0; j < 26; j++) {
			System.out.println(f[j]);
			out.println(f[j]);
		}
		out.close();
		
	}
	
	public static int check1(String a, char b) {
		int temp1 = 0;
		int temp2 = 0;
		int count1 = 0;
		int count2 = 0;
		String f = a.substring(0, a.indexOf(" ")); //the first word on one side
		String l = a.substring(a.indexOf(" ")+1);  // the second one
		while(f.indexOf(b, temp1) >= 0) {
			count1++;
			temp1 = f.indexOf(b, temp1) + 1;
		}
		while(l.indexOf(b, temp2) >= 0) {
			count2++;
			temp2 = l.indexOf(b, temp2) + 1;
		}
		return Math.max(count1, count2);
	}
}