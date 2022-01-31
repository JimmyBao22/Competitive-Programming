
import java.util.*;
import java.io.*;

public class poker {

	// http://usaco.org/index.php?page=viewproblem2&cpid=262
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("poker.in"));
		PrintWriter out = new PrintWriter(new FileWriter("poker.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n+1];
		for (int i=1; i<=n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		
		long ans=0;
		for (int i=1; i<=n; i++) {
			ans += (long)Math.max(0, arr[i] - arr[i-1]);
		}

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}

	// Draw it as a bunch of historgrams