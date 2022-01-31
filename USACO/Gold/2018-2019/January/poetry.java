
import java.util.*;
import java.io.*;

public class poetry {

	// http://usaco.org/index.php?page=viewproblem2&cpid=897
	
	static long mod = (long)(1e9+7);
	static int n,m,k;
	static A[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("poetry.in"));
		PrintWriter out = new PrintWriter(new FileWriter("poetry.out"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		arr = new A[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int s = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken())-1;
			arr[i] = new A(s,c);
		}
		Arrays.parallelSort(arr);
		
		int[] dp_all = new int[k+1];
		int[][] dp_type = new int[k+1][n];
		for (int i=0; i<n; i++) dp_type[0][i] = 1;
		dp_all[0] = 1;
		
		for (int i=1; i<=k; i++) {
			for (int j=0; j<n; j++) {
				if (i - arr[j].val < 0) break;
				dp_all[i] += dp_all[i-arr[j].val];
				dp_all[i] %= mod;
				dp_type[i][arr[j].type] += dp_all[i-arr[j].val];
				dp_type[i][arr[j].type] %= mod;
			}
		}
		
		HashMap<Character, Integer> map = new HashMap<>();
		for (int i=0; i<m; i++) {
			char c = in.readLine().charAt(0);
			map.put(c, map.getOrDefault(c, 0)+1);
		}
		
		long prod=1;
		for (Character a : map.keySet()) {
			long cur=0;
			for (int i=0; i<n; i++) {
				cur += pow(dp_type[k][i], map.get(a), mod);
				cur %= mod;
			}
			prod *= cur;
			prod %= mod;
		}
		
		System.out.println(prod%mod);
		out.println(prod%mod);
		out.close();

	}
	
	static class A implements Comparable<A> {
		int val; int type;
		A (int a, int b) {
			val = a; type = b;
		}
		public int compareTo(A o) {
			return val - o.val;
		}
	}
	
	static long pow(long a, long b, long m) {
        // a^b mod m
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