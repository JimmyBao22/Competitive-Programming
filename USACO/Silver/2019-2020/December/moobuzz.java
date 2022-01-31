
import java.util.*;
import java.io.*;

public class moobuzz {

	// http://usaco.org/index.php?page=viewproblem2&cpid=966
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("moobuzz.in"));

		int n = Integer.parseInt(in.readLine()); 
		
		// notice that this pattern will repeat every 15 terms.

		// every 15 terms, there will be 8 numbers that work.
		// converse is also true. every 8 numbers that work, 15 terms pass.
		
		int result = 0;
		
		// while n is LARGER than 8
			// subtract 8 from n; this causes 15 more terms to have passed
		while (!(n >= 0 && n <= 8)) {
			n -= 8;
			result += 15;
		}
		
		
		if (n == 1) result += 1;
		if (n == 2) result += 2;
		if (n == 3) result += 4;
		if (n == 4) result += 7;
		if (n == 5) result += 8;
		if (n == 6) result += 11;
		if (n == 7) result += 13;
		if (n == 8) result += 14;
		
		// OUTPUT //
		PrintWriter out = new PrintWriter(new FileWriter("moobuzz.out"));
		System.out.println(result);
		out.println(result);
		out.close();
	}
}