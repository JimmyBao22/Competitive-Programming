import java.util.*;
import java.io.*;

public class cowsignal {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=665

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("cowsignal.in"));
		
		int M = in.nextInt();
	    int N = in.nextInt();
	    int K = in.nextInt();
	    
	    char[][] c = new char[M][N];
		
	    for (int i = 0; i < M; i++) {
	    	c[i] = in.next().toCharArray();
	    	
	    }
	    
		in.close();
		
		char[][] c2 = new char[K*M][K*N];
		
		for (int i = 0; i < M; i++) {
			for (int j = 0, a = 0; j < K*N; j += K, a++) {
				for (int k = 0; k < K; k++) {
					c2[K*i][j+k] = c[i][a];
				}
			}
			
			for (int l = 1; l < K; l++) {
				c2[K*i+l] = c2[K*i];
			}			
		}
		
		PrintWriter out = new PrintWriter(new File("cowsignal.out"));
		
		for (int i = 0; i < c2.length; i++) {
			for (int j = 0; j < c2[0].length; j++) {
	    	System.out.print(c2[i][j]);
	    	out.print(c2[i][j]);
	    	
	    }
			System.out.println();
			out.println();
	    }
	
		out.close();	
	}
}