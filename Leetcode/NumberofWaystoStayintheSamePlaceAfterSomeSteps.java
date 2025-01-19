
import java.util.*;

public class NumberofWaystoStayintheSamePlaceAfterSomeSteps {

	// https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/
	
	public static void main(String[] args) {
		System.out.println(numWaysRec(4,2));
	}
	
	public static int numWays(int steps, int arrLen) {
        long[][] dp = new long[Math.min(steps/2+1, arrLen)][steps+1];
        	// all pos has to be <= steps/2 because otherwise it is impossible for it to reach 0
        dp[0][0] = 1;
        int mod = (int)(1e9 + 7);
        for (int i=1; i<dp[0].length; i++) {
        	for (int j=0; j<dp.length; j++) {
        		if (j != 0) {
        			dp[j][i] += dp[j-1][i-1]%mod;
        		}
        		if (j != dp.length-1) {
        			dp[j][i] += dp[j+1][i-1]%mod;
        		}
        		dp[j][i] += dp[j][i-1]%mod;
        	}
        }
        return (int)(dp[0][dp[0].length-1]%mod);
	}

	public static int numWaysRec(int steps, int arrLen) {
        long[][] memo = new long[Math.min(steps/2+1, arrLen)][steps];
        	// all pos has to be <= steps/2 because otherwise it is impossible for it to reach 0
        for (int i=0; i<memo.length; i++) {
        	Arrays.fill(memo[i], -1);
        }
        int mod = (int)(1e9 + 7);
        return (int)(dp(memo, 0, 0, mod)%mod);
    }
	
	public static long dp(long[][] memo, int pos, int step, int mod) {
		if (pos < 0 || pos >= memo.length) return 0;
		if (step == memo[0].length && pos == 0) return 1;
		if (step == memo[0].length) return 0;
		if (memo[pos][step] != -1) return memo[pos][step];
		
		long t =0;
		t += dp(memo, pos-1, step+1, mod)%mod;
		t += dp(memo, pos, step+1, mod)%mod;
		t += dp(memo, pos+1, step+1, mod) %mod;
		return memo[pos][step] = t;
	}
}