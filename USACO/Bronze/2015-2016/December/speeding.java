
import java.util.*;
import java.io.*;

public class speeding {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=568

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("speeding.in"));
		//make 2 100 size arrays each storing each thing, then compare the two.
		
		int[] limit = new int[100];
		int[] speed = new int[100];
		
		int n = in.nextInt(); 
		int m = in.nextInt();
		
		int q = 0;
		int temp = 0;
		int speed1 = 0;
		
		for (int i = 0; i < n; i++) {
			temp = in.nextInt();
		    speed1 = in.nextInt();
			for (int j = q; j < q + temp; j++) {
				limit[j] = speed1;
			}
			q += temp;
			
		}
		
		q = 0;
		for (int i = 0; i < m; i++) {
			temp = in.nextInt();
		    speed1 = in.nextInt();
			for (int j = q; j < q + temp; j++) {
				speed[j] = speed1;
			}
			q += temp;
			
		}
		
		in.close();

		//now we have 2 arrays so now we must compare
		
		int max = 0;
		
		for (int i = 0; i < 100; i++) {
			if (speed[i] - limit[i] > max) max = speed[i] - limit[i];
		}
		
		PrintWriter out = new PrintWriter(new File("speeding.out"));
		System.out.println(max);
		out.println(max);
		out.close();
	}
}