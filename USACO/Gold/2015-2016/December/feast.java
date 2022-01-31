
import java.util.*;
import java.io.*;

public class feast {

	// http://usaco.org/index.php?page=viewproblem2&cpid=574
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("feast.in"));
		PrintWriter out = new PrintWriter(new FileWriter("feast.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int t = Integer.parseInt(st.nextToken());
		int a = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());

		boolean[] dp1 = new boolean[t+1];
		boolean[] dp2 = new boolean[t+1];
		int max=0;
		dp1[0] = true;
		dp2[0] = true;
		for (int i=1; i<t+1; i++) {
			if (i-a>=0) {
				dp1[i] = dp1[i-a];
			}
			if (i-b>=0 && !dp1[i]) {
				dp1[i] = dp1[i-b];
			}
			if (!dp2[i/2]) dp2[i/2] = dp1[i];
			if (dp1[i]) max = Math.max(max, i);
		}
		
		for (int i=1; i<t+1; i++) {
			if (i-a>=0) {
				dp2[i] = dp2[i-a];
			}
			if (i-b>=0 && !dp2[i]) {
				dp2[i] = dp2[i-b];
			}
			if (dp2[i]) max = Math.max(max, i);
		}
		
		System.out.println(max);
		out.println(max);
		out.close();
	}
}

/*

	works as well

		long[] dp1 = new long[t+1];
		long[] dp2 = new long[t+1];
		long mod = (long)(1e9+7);
		int max=0;
		dp1[0] = 1;
		dp2[0] = 1;
		for (int i=1; i<t+1; i++) {
			if (i-a>=0) {
				dp1[i] += dp1[i-a];
				if (dp1[i] == mod) dp1[i]=1;
				else if (dp1[i] > mod) dp1[i] %= mod;
			}
			if (i-b>=0) {
				dp1[i] += dp1[i-b];
				if (dp1[i] == mod) dp1[i]=1;
				else if (dp1[i] > mod) dp1[i] %= mod;
			}
			dp2[i/2] += dp1[i];
			if (dp1[i] > 0) max = Math.max(max, i);
		}
		
		for (int i=1; i<t+1; i++) {
			if (i-a>=0) {
				dp2[i] += dp2[i-a];
				if (dp2[i] == mod) dp2[i]=1;
				else if (dp2[i] > mod) dp2[i] %= mod;
			}
			if (i-b>=0) {
				dp2[i] += dp2[i-b];
				if (dp2[i] == mod) dp2[i]=1;
				else if (dp2[i] > mod) dp2[i] %= mod;
			}
			if (dp2[i] > 0) max = Math.max(max, i);
		}

*/