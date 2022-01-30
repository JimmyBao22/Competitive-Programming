
import java.util.*;
import java.io.*;

public class shuffle {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=760

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("shuffle.in"));
		
		int n = in.nextInt();
		
		int[] order = new int[n];
		int[] id = new int[n];
		
		for (int i = 0; i < n; i++) {
			order[i] = in.nextInt();
		}
		for (int i = 0; i < n; i++) {
			id[i] = in.nextInt();
		}
		
		in.close();

		int[] temp = new int[n];
		
		for (int j = 0; j < 3; j++) {
			//repeat 3 times
			for (int i = 0; i < n; i++) {
				//this will loop over whole thing.
				//look at each term in order. then we will switch it back.
				temp[i] = id[order[i]-1];
				
			}
			//id = temp;
			for (int i = 0; i < n; i++) {
				id[i] = temp[i];
			}
		}
		
		PrintWriter out = new PrintWriter(new File("shuffle.out"));
		
		for (int i = 0; i < n; i++) {
			System.out.println(id[i]);
			out.println(id[i]);
		}
		
		out.close();
	}
}