import java.util.*;
import java.io.*;
import java.util.Arrays;

public class diamond {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=639
	
	public static void main(String[] args) throws FileNotFoundException {
	    Scanner in = new Scanner(new File("diamond.in"));
	    
	    int n = in.nextInt();
	    int k = in.nextInt();
	    int[] o = new int[n];
	    for(int i = 0; i<n; i++) {
	        o[i] = in.nextInt();
	    }
	    in.close();
	
	    int count = 0;
		int max = 0;
		Arrays.sort(o);
		
		for (int j = 0; j < n; j++) {
			count = 1;
			if (j != 0 && o[j] == o[j-1]) {
				continue;
			}
			for (int h = j+1; h < n; h++) {
				if (Math.abs(o[h] - o[j]) <= k) {
					count++;
				}
			}
			if (count > max) {
				max = count;
			}
		}
		
	    PrintWriter out = new PrintWriter(new File("diamond.out"));
	    out.println(max);
	    System.out.println(max);
	    out.close();
  	}
}