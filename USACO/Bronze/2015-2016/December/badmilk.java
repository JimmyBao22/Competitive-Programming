import java.util.*;
import java.io.*;

public class badmilk {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=569

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("badmilk.in"));
		
		int N = in.nextInt(); 	//num people	
		int M = in.nextInt();	//num milk
		int D = in.nextInt();
		int S = in.nextInt();
		
		int[] milk = new int[M+1];
		
		int[][] r = new int[D][3];
		// gives when drink and drink what
		for (int i = 0; i < D; i++) {
			for (int j = 0 ; j < 3; j++) {
				r[i][j] = in.nextInt();
			}
		}
		
		int[][] s = new int[S][2];
		// gives who got sick when
		for (int i = 0; i < S; i++) {
			for (int j = 0 ; j < 2; j++) {
				s[i][j] = in.nextInt();
			}
		} 
		
		in.close();

		/*
			boolean[] checkp = new boolean[N+1];
			//this will check if this person has already been checked or not
			boolean[] checkm = new boolean[M+1];
			//this will check if this person has already drank this milk has already been checked or not
		*/
		
		boolean[][] check = new boolean[N+1][M+1];
		
		for (int i = 0; i < r.length; i++) {
			// look for their time in S loop to see if they have same
			for (int k = 0; k < s.length; k++) {
				if (r[i][0] == s[k][0]) {
					//same person found!
					//now check to see if time is before 
					if (!check[r[i][0]][r[i][1]]) {
						check[r[i][0]][r[i][1]] = true;
						if (r[i][2] < s[k][1]) {
							//this means time is before they got sick! good!
							//now we can add to the total array
							
							milk[r[i][1]]++;			
						}
					}	
				}
			}
		}
		
		int max = 0;
		

		for (int i = 0; i < milk.length; i++ ) {
			if (milk[i] > max) {
				max = milk[i];
			}
		}
		
		int result = 0;

		int max2 = 0;
		//now we have to loop through 
		boolean[] check2 = new boolean[N+1];
		//this will check if this person has already been checked or not
		for (int j = 0; j < milk.length; j++) {
			if (milk[j] == max) {
				result = 0;
				for (int i = 0; i < D; i++) {	
					
					if (r[i][1] == j) {
						//this is the same milk
						if (!check2[r[i][0]]) {
							//this person hasn't been checked yet
							result++;
							check2[r[i][0]] = true;
						}
					}
					
				}
				if (result > max2) {
					max2 = result;
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new File("badmilk.out"));
		System.out.println(max2);
		out.println(max2);
		out.close();
	}
}