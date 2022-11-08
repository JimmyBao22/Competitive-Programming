
import java.util.*;
import java.io.*;

public class Grid2 {

	// https://atcoder.jp/contests/dp/tasks/dp_y
	
	static long mod = (long)(1e9+7);
	static long[] fact, inv_fact;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Grid"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int h = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken())-1;
			arr[i][1] = Integer.parseInt(st.nextToken())-1;
		}
		
		fact = new long[h+w+1]; 
		inv_fact = new long[h + w + 1];
		fact[0] = inv_fact[0] = 1;
		for (int i=1; i<fact.length; i++) {
			fact[i] = fact[i-1] * i;
			fact[i] %= mod;
			inv_fact[i] = pow(fact[i], mod-2, mod);
		}
				
		Arrays.parallelSort(arr, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return (a[0] + a[1]) - (b[0] + b[1]);
			}
		});
		long[] dp = new long[n];
			// number of ways such that arr[i] is first forbidden you pass through
		
		for (int i=0; i<n; i++) {
			dp[i] = choose(arr[i][0] + arr[i][1], arr[i][0]);
			for (int j=0; j<i; j++) {
				if (arr[j][0] <= arr[i][0] && arr[j][1] <= arr[i][1]) {
					// go to arr[j], to go to arr[i]
					int changex = arr[i][0] - arr[j][0];
					int changey = arr[i][1] - arr[j][1];
					dp[i] -= (dp[j] * choose(changex + changey, changex));
					dp[i] %= mod;
				}
			}
		}
		
		long total = choose((h-1) + (w-1), h-1);
			
			// subtract out all bad paths
		for (int i=0; i<n; i++) {
			// from arr[i] to end
			int changex = h - 1 - arr[i][0];
			int changey = w - 1 - arr[i][1];
			total -= (dp[i] * choose(changex + changey, changex));;
			total %= mod;
		}
		System.out.println((total%mod + mod)%mod);
	}
	
	public static long choose(int top, int bottom) {
		// top! / bottom! (top - bottom)!
		return fact[top] * inv_fact[bottom] %mod * inv_fact[top - bottom] %mod;
	}
	
    static long pow(long a, long b, long m) {
        // a^b
    	long ans=1;
    	while (b >0) {
    		if (b%2 == 1) {
    			ans *= a%m;
    			ans %= m;
    		}
    		a *= a %m;
    		a%=m;
    		b >>=1;
    	}
    	return ans;
    }
}