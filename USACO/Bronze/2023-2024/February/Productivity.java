
import java.util.*;
import java.io.*;

public class Productivity {
	
	// https://usaco.org/index.php?page=viewproblem2&cpid=1397

	public static void main(String[] args) throws IOException, FileNotFoundException {
		Scanner in = new Scanner(System.in);
		
		// input
		int n = in.nextInt();
		int q = in.nextInt();
		int[] c = new int[n];
		for (int i = 0; i < n; i++) {
			c[i] = in.nextInt();
		}
		int[] t = new int[n];
		for (int i = 0; i < n; i++) {
			t[i] = in.nextInt();
		}
		int[] v = new int[q];
		int[] s = new int[q];
		for (int i = 0; i < q; i++) {
			v[i] = in.nextInt();
			s[i] = in.nextInt();
		}
		
		int[] difference = new int[n];
		for (int i = 0; i < n; i++) {
			difference[i] = c[i] - t[i] - 1;
		}
		Arrays.sort(difference);
		
		// either binary search or notice that in order for V farms to work,
		// value at index n - V >= s[i]
		
		for (int i = 0; i < q; i++) {
			if (difference[n - v[i]] >= s[i]) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
		
	}

}
