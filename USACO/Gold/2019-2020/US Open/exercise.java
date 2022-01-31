
import java.util.*;
import java.io.*;

public class exercise {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1043
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("exercise.in"));
		PrintWriter out = new PrintWriter(new FileWriter("exercise.out"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long mod = Integer.parseInt(st.nextToken());
		
		max = n+1;
		arr = new int[max];
		primes = new ArrayList<>();
		buildtotient();
		int m = primes.size();
		
		long[][] dp = new long[m][n+1];
		// prime index, sum
		
		ArrayList<ArrayList<Integer>> powers = new ArrayList<>();
			// powers of primes such that it is ≤ n
		for (int i=0; i<m; i++) {
			int primecur = primes.get(i);
			ArrayList<Integer> cur = new ArrayList<>();
			cur.add(1);
			while (primecur <= n) {
				cur.add(primecur);
				dp[i][primecur] = primecur;
				primecur *= primes.get(i);
			}
			powers.add(cur);
		}
		
		for (int i=1; i<m; i++) {
			for (int j=n; j>=0; j--) {
				for (int k=1; k<powers.get(i).size(); ++k) {
					if (j - powers.get(i).get(k) < 0) break;
					dp[i][j] += dp[i-1][j - powers.get(i).get(k)] * powers.get(i).get(k);
					dp[i][j] %= mod;
				}
				dp[i][j] += dp[i-1][j];		// add previous because now can be used to multiply
											// with future elements
				//print(dp);
			}
		}		
		
		long ans=1;			// include the number 1
		for (int i=0; i<=n; i++) {
			ans += dp[m-1][i];
			ans %= mod;
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static void print(long[][] arr) {
		for (int i=0; i<arr.length; i++) {
			for (int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static int max;
	private static int[] arr;
	static ArrayList<Integer> primes;
	
	public static void buildtotient() {
		for (int i=1; i<max; i++) {
			arr[i] = i;
		}
		primes.add(2);
		for (int i=2; i<max; i+=2) {
			arr[i] = arr[i]/2;
		}
		for (int i=3; i<max; i+=2) {
			if (arr[i] == i) {
				primes.add(i);
				for (int j= i; j < max; j+= i) {
					arr[j] /= i; arr[j] *= (i-1);
				}
			}
		}
	}
}

/*

	An integer works if the sum of its prime divisor multiples is ≤ n	
		Ex. a number like 12 works for all numbers ≥ 4+3=7.

*/