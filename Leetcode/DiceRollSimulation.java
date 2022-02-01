
import java.util.*;
import java.io.*;

public class DiceRollSimulation {

	// https://leetcode.com/problems/dice-roll-simulation/
		
	public static void main(String[] args) {
		//int[] arr = {8,5,10,8,7,2}; n = 20
				// ans = 822005673
		
		int[] arr = {1,1,1,2,2,3};
		System.out.println(dieSimulator(3, arr));
	}
	
	public static int dieSimulator(int n, int[] rollMax) {
		int maxtimes=1;
		for (int i=0; i<6; i++) maxtimes = Math.max(maxtimes, rollMax[i]);
		long[][][] dp = new long[n+1][6][maxtimes+1];
			// dice roll, number it currently is on, number of times it currently rolled
		int mod = (int)(1e9+7);
		
		for (int i=0; i<6; i++) {
		    dp[1][i][1] = 1;
        }
		
		for (int i=2; i<=n; i++) {
			for (int j=0; j<6; j++) {
				for (int k=1; k<rollMax[j]+1; k++) {
					dp[i][j][k] += dp[i-1][j][k-1]%mod;
				}
				// dp[i][j][1]
				for (int z=0; z<6; z++) {
					if (z == j) continue;
					for (int k=1; k<rollMax[z]+1; k++) {
						dp[i][j][1] += dp[i-1][z][k]%mod;
					}
				}
			}
		}
		
		long t=0;
		for (int i=0; i<6; i++) {
			for (int j=1; j<rollMax[i]+1; j++) {
				t += dp[n][i][j]%mod;
			}
		}
		return (int)(t%mod);
	}

	
	public static int dieSimulatorRec(int n, int[] rollMax) {
		int maxtimes=1;
		for (int i=0; i<6; i++) maxtimes = Math.max(maxtimes, rollMax[i]);
		long[][][] memo = new long[n+1][6][maxtimes+1];
			// dice roll, number it currently is on, number of times it currently rolled
		int mod = (int)(1e9+7);
		for (int i=0; i<n+1; i++) {
			for (int j=0; j<6; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		return (int)(dp(memo, 0, 0, 0, mod, rollMax)%mod);
    }
	
	public static long dp(long[][][] memo, int n, int num, int times, int mod, int[] rollMax) {
		if (n == memo.length-1) return 1;
		if (memo[n][num][times] != -1) return memo[n][num][times];
		long t=0;
		for (int i=0; i<6; i++) {
			if (i == num) continue;
			t += dp(memo, n+1, i, 1, mod, rollMax)%mod;
		}
		// i == num
		if (times == rollMax[num]) {
			
		}
		else {
			t += dp(memo, n+1, num, times+1, mod, rollMax)%mod;
		}
		return memo[n][num][times] = t;
	}
}