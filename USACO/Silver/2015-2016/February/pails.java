
import java.util.*;
import java.io.*;

public class pails {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=620
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("pails.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int x = Integer.parseInt(st.nextToken());  
		int y = Integer.parseInt(st.nextToken());  
		int k = Integer.parseInt(st.nextToken());  
		int m = Integer.parseInt(st.nextToken());  

	    boolean[][] works = new boolean[x+1][y+1];
	    works[0][0] = true;
	    
	    for (int i = 0; i < k; i++) {
	    	boolean[][] copy = new boolean[x+1][y+1];
	    	copy[0][0] = true;
			copy[x][0] = true;
			copy[0][y] = true;
	    	for (int j = 0; j < works.length; j++) {
	    		for (int a = 0; a < works[0].length; a++) {
	    			
	    			if (!works[j][a]) continue;
	    			
	    			copy[j][0] = true;
	    			copy[0][a] = true;
	    			
	    			copy[j][y] = true;
	    			copy[x][a] = true;
	    			
	    			// pour from a to j
	    			if (x - a < j) {
	    				copy[x][j - (x - a)] = true;
	    			}
	    			else {
	    				copy[a + j][0] = true;
	    			}
	    			
	    			// pour from j to a
	    			if (y - j < a) {
	    				copy[a - (y - j)][y] = true;
	    			}
	    			else {
	    				copy[0][a+j] = true;
	    			}
	    		}
	    	}
	    	works = copy;
	    }
		
		in.close();

		int result = Integer.MAX_VALUE;

		for (int i = 0; i < works.length; i++) {
			for (int j = 0; j < works[0].length; j++) {
				if (works[i][j]) result = Math.min(result, Math.abs(i + j - m));
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("pails.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}