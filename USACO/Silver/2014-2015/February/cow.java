
import java.util.*;
import java.io.*;

public class cow {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=527
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cow.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cow.out"));

		int n = Integer.parseInt(in.readLine());
		String s = in.readLine();

		long c=0;
		long oval=0;
		long ans=0;
		for (int i=0; i<n; i++) {
			if (s.charAt(i) == 'C') c++;
			else if (s.charAt(i) == 'O') oval += c;
			else ans += oval;
		}

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}

/*
	Note: something like this is wrong:
	
		long c=0;
		long o=0;
		long ans=0;
		for (int i=0; i<n; i++) {
			if (s.charAt(i) == 'C') c++;
			else if (s.charAt(i) == 'O') o++;
			else ans += c*o;
		}
		
	Because for example 
		5
		COCOW
			answer is 3, but code above gives 4

*/