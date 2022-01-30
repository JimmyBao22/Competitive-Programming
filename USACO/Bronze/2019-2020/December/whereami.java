import java.util.*;
import java.io.*;

public class whereami {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=964

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("whereami.in"));
		
		int n = in.nextInt();
		String letters = in.next();
		in.close();

		boolean same = false;
		int result = 0;
		outerloop:
		for (int i = 2; i <= n; i++) {
			// i will be the length of the sequence
			String[] b = new String[n-i+1];
			// we will store in an array for each i
			/*
			 	ex. ABCDABC
			 	for i = 2
			 	AB
			 	BC
			 	CD
			 	DA
			 	AB
			 	BC
			 	the compare. if any are different then boolean is false and break inner loop. else break outerloop
			 */
			same = false;
			for (int j = 0; j < n-i+1; j++) {
				b[j] = letters.substring(j, j+i);
			}
			outerloop2:
			for (int j = 0; j < b.length; j++) {
				for (int k = j+1; k < b.length; k++) {
					// check each one
					if (b[j].equals(b[k])) {
						same = true;
						// this means same thing
						break outerloop2;
						// go to the next one. i ++
					}
				}
			}
			if (!same) {
				// no same number found
				result = i;
				break outerloop;
			}
		}
		
		PrintWriter out = new PrintWriter(new File("whereami.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}