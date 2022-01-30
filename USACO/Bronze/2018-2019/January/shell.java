import java.util.*;
import java.io.*;

public class shell {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=891

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("shell.in"));
		
		int n = in.nextInt();
		
		int[] shells = {1, 2, 3};
		
		int[] total = new int[3];
		
		for (int i = 0; i < n; i++) {
			int k = in.nextInt()-1;
			int l = in.nextInt()-1;
			int temp = shells[k];
			shells[k] = shells[l];
			shells[l] = temp;
			
			//this switches the terms.
			total[shells[in.nextInt()-1]-1]++;

		}
		
		in.close();
		
		int max = 0;
		for (int i = 0; i < 3; i++) {
			if (total[i] > max) max = total[i];
		}
		
		PrintWriter out = new PrintWriter(new File("shell.out"));
		System.out.println(max);
		out.println(max);
		out.close();
	}
}

//my idea
/*
	 Start making an array, since there are 3 numbers u have [1,2,3]
	 each representing a diff shell
	 
	 for every time u swap. Then u look at the guessed spot and add it to the total array
	 total array is an array with [1,2,3] as well, which will count how many times u counted that part
	 
	 ex. if u have [1,2,3] and swap first 2 and guess first, you have [1,2,3] --> [2,1,3] and guess first spot u have 2 position
	 so then the total array turns [0,0,0] --> [0,1,0]
	 
	 Then find the highest and return it
 */