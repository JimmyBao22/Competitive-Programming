
import java.util.*;
import java.io.*;

public class bcount {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=572
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("bcount.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		
		int[] one = new int[n+1];
		int[] two = new int[n+1];
		int[] three = new int[n+1];
		
		for (int i=0; i<n; ++i) {
			one[i+1] = one[i];
			two[i+1] = two[i];
			three[i+1] = three[i];
			int id = Integer.parseInt(in.readLine());
			if (id == 1) one[i+1]++;
			else if (id == 2) two[i+1]++;
			else three[i+1]++;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("bcount.out"));

		for (int i=0; i<q; i++) {
			String ans="";
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			ans += (one[b+1] - one[a]) + " " + (two[b+1] - two[a]) + " " + (three[b+1] - three[a]);
			System.out.println(ans);
			out.println(ans);
		}
		
		out.close();
	}
}