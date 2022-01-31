
import java.util.*;
import java.io.*;

public class cowfind {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=187
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowfind.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowfind.out"));

		char[] arr = in.readLine().toCharArray();
		int n = arr.length;
		if ( n==1) {
			System.out.println(0);
			out.println(0);
			out.close();
			return;
		}
		
		int count =0;
		int ans=0;
		for (int i=1 ; i<n; i++) {
			if (arr[i] == '(' && arr[i-1] == '(') count++;
			else if (arr[i] == ')' && arr[i-1] == ')') ans += count;
		}
			
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}