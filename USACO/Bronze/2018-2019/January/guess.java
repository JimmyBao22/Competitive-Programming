import java.util.*;
import java.io.*;

public class guess {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=893

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("guess.in"));
		
		int n = in.nextInt();
		String[][] x = new String[n][100];
		//the first one can be ignored. each row will be a diff cow
		for (int i = 0; i < n; i++) {
			in.next(); // throw this away
			int a = in.nextInt();
			for (int j = 0; j < a; j++) {
				x[i][j] = in.next();
			}
		}
		in.close();
		
		int count = 0;
		int max = 0;
		
		int countfirst = 0;
		int countsecond = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				// this will loop through each one separately
				count = 0;
				countfirst = 0;
				for (int k = 0; k < 100; k++) {
					if (x[i][k] == null) continue;
					countfirst++; // this counts how many in first are not null
					countsecond = 0;
					for (int m = 0; m < 100; m++) {
						if (x[j][m] == null) continue;
						countsecond++; // this counts how many in second are not null
						if (x[i][k].equals(x[j][m])) count++;
					}
				
				}
				
				if (count != countfirst || count != countsecond) {
					count++;
					/* 
					   this is because, if we r comparing 2 things
					   such as a, b, c and a, b; then 
					   here there will be three 'yes' as max
					   
					   meanwhile if its a,b and a,b; it will only be 
					   two 'yes' max
					*/
				}
				if (count > max) {
					max = count;
				}
			}
		}		
		
		PrintWriter out = new PrintWriter(new File("guess.out"));
		System.out.println(max);
		out.println(max);
		out.close();
	}
}