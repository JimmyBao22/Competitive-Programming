import java.util.*;

public class NumberofDiceRollsWithTargetSum {

	// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
	
	public static void main(String[] args) {
		System.out.println(numRollsToTarget(30,30,500));
	}
	
	public static int numRollsToTarget(int d, int f, int target) {
        int[][] dp = new int[d+1][target+1];
        	// number of dice it is on, value
        int mod = (int)1e9 + 7;
        for (int i=1; i<f+1 && i < target+1; i++) {
        	dp[1][i] = 1;
        }
        for (int i=2; i<d+1; i++) {
        	for (int k=0; k < target+1; k++) {
        		for (int j=1; j<f+1; j++) {
            		if (k - j < 0) continue;
            		dp[i][k] += dp[i-1][k-j];
            		dp[i][k] %= mod;
            	}
        	}
        }
        return dp[d][target];
    }
	
	public static int numRollsToTargetRec(int d, int f, int target) {
        long[][] memo = new long[d+1][target+1];
        	// number of dice it is on, value
        int mod = (int)1e9 + 7;
        for (int i=0; i<d+1; i++) {
        	Arrays.fill(memo[i], -1);
        }
        return (int)(dp(0, 0, memo, f, mod)%mod);
	}
	
	public static long dp(int dice, int val, long[][] memo, int f, int mod) {
		if (dice >= memo.length) return 0;
		if (val >= memo[0].length) return 0;
		if (dice == memo.length-1 && val == memo[0].length-1) return 1; // visit the val
		if (memo[dice][val] != -1) return memo[dice][val];
		long sum=0;
		for (int i=1; i<=f; i++) {
			if (val + i >= memo[0].length) break;
			sum += dp(dice+1, val+i, memo, f, mod) %mod;
		}
		return memo[dice][val] = sum%mod;
	}
}
