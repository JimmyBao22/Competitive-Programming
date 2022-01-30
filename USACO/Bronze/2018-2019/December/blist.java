import java.util.*;
import java.io.*;

public class blist {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=856
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("blist.in"));
		
		int n = in.nextInt();

		//note: max amount of buckets possible is 10n.
		int[] buckets = new int[10*n];
		
		int[][] c = new int[n][3];		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 3; j++) {
				c[i][j] = in.nextInt();
			}
		}
		in.close();

		//sort the cows so that you have min first.
		for(int j = 0; j < c.length; j++) {
            //Find the jth smallest:
            int minIndex = j; 
            for(int i = j + 1; i < c.length ; i++) { 
                if(c[i][0] < c[minIndex][0]) {
                    minIndex = i;
                }
            }
            //swap x[j] with x[minIndex]:
            int[] tmp = c[j];
            c[j] = c[minIndex];
            c[minIndex] = tmp;
        }
		

		for (int i = 0; i < c.length; i++) {
			int j = 0;
			int k = 0;
			while (j < c[i][2]) {
				// j will count number of times the milk got added.
				if (buckets[k] - c[i][0] > 0) {
					k++;
					continue;
				}
				
				buckets[k] = c[i][1];
				
				j++;
				k++;
			}
		}
		
		
		int result = 0;
		
		for (int i = 0; i < buckets.length; i++) {
			if (buckets[i] != 0) result++;
		}
		
		PrintWriter out = new PrintWriter(new File("blist.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}