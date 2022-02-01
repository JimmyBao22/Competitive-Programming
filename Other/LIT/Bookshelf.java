
import java.util.*;
import java.io.*;

public class Bookshelf {

	// https://lit.lhsmathcs.org/bookshelf
	
	static long mod = (long)(1e9+7);
	static int r,b,s;
	static int[] rbooks, bbooks;
	static int mx;
	static long INF = (long)(1e18);
	static long[][] dp;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Bookshelf"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		r = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
		mx = Math.max(r, b);
		st = new StringTokenizer(in.readLine());
		rbooks = new int[r];
		bbooks = new int[b];
		for (int i=0; i<r; i++) {
			rbooks[i] = -1*Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<b; i++) {
			bbooks[i] = -1*Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(rbooks);
		Arrays.parallelSort(bbooks);
		for (int i=0; i<r; i++) rbooks[i] *= -1;
		for (int i=0; i<b; i++) bbooks[i] *= -1;
				
		int rpoint = 0;
		int bpoint = 0;
		
		dp = new long[s+1][2];
			// 0 = red, 1 = blue
		dp[0][0]=1;
		dp[0][1]=1;
		
		while (rpoint < r && bpoint < b) {
			if (rbooks[rpoint] == bbooks[bpoint]) {
				for (int i=s; i-rbooks[rpoint]>=0; i--) {
					dp[i][0] += dp[i-rbooks[rpoint]][1];
					dp[i][0]%=mod;
					dp[i][1] += dp[i-rbooks[rpoint]][0];
					dp[i][1]%=mod;
					
					// take either first
					if (i-2*rbooks[rpoint] >= 0) {
						dp[i][0] += dp[i-2*rbooks[rpoint]][0];
						dp[i][1] += dp[i-2*rbooks[rpoint]][1];
						dp[i][0]%=mod;
						dp[i][1]%=mod;
					}
				}
				rpoint++;
				bpoint++;
			}
			else if (rbooks[rpoint] > bbooks[bpoint]) {
				addred(rbooks[rpoint]);
				rpoint++;
			}
			else {
				addblue(bbooks[bpoint]);
				bpoint++;
			}
		}
		
		while (rpoint < r) {
			addred(rbooks[rpoint]);
			rpoint++;
		}
		
		while (bpoint < b) {
			addblue(bbooks[bpoint]);
			bpoint++;
		}
		
		System.out.println((dp[s][0] + dp[s][1])%mod);
	}
	
	public static void addred (int redval) {
		for (int i=s; i-redval>=0; i--) {
			dp[i][0] += dp[i-redval][1];
			dp[i][0]%=mod;
		}
	}
	
	public static void addblue (int blueval) {
		for (int i=s; i-blueval>=0; i--) {
			dp[i][1] += dp[i-blueval][0];
			dp[i][1]%=mod;
		}
	}
}