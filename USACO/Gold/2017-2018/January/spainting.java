
import java.util.*;
import java.io.*;

public class spainting {

	// http://usaco.org/index.php?page=viewproblem2&cpid=791
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("spainting.in"));
		PrintWriter out = new PrintWriter(new FileWriter("spainting.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		long mod = (long)(1e9+7);
		
		long[] dp = new long[n+1];
			// dp[i] = number of ways for the first i to NOT have a valid sequence
		dp[1] = m;
		
		long sum=m;
		for (int i=2; i<k; i++) {
				// either add any of m-1 colors to some previous dp (m-1*sum), 
					//or create a new sequence with just length i of the same color (+m) 
			dp[i] = (m-1)*(sum) + m;
			dp[i] %= mod;
			sum += dp[i];
			sum %= mod;
		}
		
		for (int i=k; i<=n; i++) {
			dp[i] = (m-1)*(sum);
			dp[i] %= mod;
			sum -= dp[i-k+1];
			sum += dp[i];
			sum = (sum%mod + mod)%mod;
		}
		
		// System.out.println(Arrays.toString(dp));
		
		long total = pow(m, n, mod);
		total -= dp[n];
		
		total = (total%mod + mod)%mod;
		
		System.out.println(total);
		out.println(total);
		out.close();
	}

	public static long pow(long a, long b, long m) {
    	long ans = 1;
    	while (b > 0) {
    		if (b%2 == 1) {
    			ans *= a;
    			ans %= m;
    		}
    		a *= a;
    		a %= m;
    		b >>= 1;
    	}
    	return ans;
    }	
}


/*
	in order for it to work, at least one thing of k length. This is because the last move
	is k length long of the same color
	
	dp[i] = number of ways for the first i to NOT have a valid sequence
	
	dp[i] = (M-1) * (sum from i=i-k+1 to i-1 of dp[i])
	
	M-1 because you need to choose the M-1 new character
	
	i-k+1 to i-1 because you are adding a sequence from that index to i-1 of this current new color
	keep track of sum from i=n-k+1 to n-1 of dp[i] in another variable
	this way, can just remove or add one value to it, so then time
	complexity stays at O(n)

	finally, do m^n (total number of ways) - dp[n] 
*/