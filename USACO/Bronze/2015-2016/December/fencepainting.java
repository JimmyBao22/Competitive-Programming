import java.util.*;
import java.io.*;

public class fencepainting {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=567
	
	public static void main(String[] args) throws FileNotFoundException {
	
		Scanner in = new Scanner(new File("paint.in"));

		int a = in.nextInt();
		int b = in.nextInt();
		int c = in.nextInt();
		int d = in.nextInt();
		
		boolean[] p = new boolean[100];
		 
		for (int i = a; i < b; i++) {
			p[i] = true; 
		}
		 
		for (int j = c; j < d; j++) {
			 p[j] = true;
		}
		
		int result = 0;
		for (int k = Math.min(a, c); k < Math.max(b,d); k++) {
			 if (p[k]) result++;
		}
		
		in.close();
		
		PrintWriter out = new PrintWriter(new File("paint.out"));
		out.println(result);
		System.out.println(result);
		out.close();
	}
}