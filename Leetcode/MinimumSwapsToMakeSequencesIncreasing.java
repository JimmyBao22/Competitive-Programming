
import java.util.*;

public class MinimumSwapsToMakeSequencesIncreasing {

	// https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/
	
	public static void main(String[] args) {

	}

	public int minSwap(int[] A, int[] B) {
		int[][] dp = new int[A.length][2];
    	// which number you are on, if last one was a swap or not
		for (int i=0; i<A.length; i++) {
        	Arrays.fill(dp[i], (int)(1e9));
        }
		dp[0][0] = 0;
		dp[0][1] = 1;
		for (int i=1; i<A.length; i++) {
			int lastA = 0;
			int lastB = 0;
			
			// last pos swapped
			lastA = B[i-1];
			lastB = A[i-1];
			if (A[i] > lastA && B[i] > lastB) {
				dp[i][0] = Math.min(dp[i][0], dp[i-1][1]);
			}
			if (A[i] > lastB && B[i] > lastA) {
				dp[i][1] = Math.min(dp[i][1], dp[i-1][1] + 1);
			}
			
			// last pos not swapped
			lastA = A[i-1];
			lastB = B[i-1];
			if (A[i] > lastA && B[i] > lastB) {
				dp[i][0] = Math.min(dp[i][0], dp[i-1][0]);
			}
			if (A[i] > lastB && B[i] > lastA) {
				dp[i][1] = Math.min(dp[i][1], dp[i-1][0] + 1);
			}
		}
		
		return Math.min(dp[A.length-1][0], dp[A.length-1][1]);
    }
	
	public int minSwapRec(int[] A, int[] B) {
        int[][] memo = new int[A.length][2];
        	// which number you are on, if last one was a swap or not
        for (int i=0; i<A.length; i++) {
        	Arrays.fill(memo[i], -1);
        }
        return Math.min(dp(memo, 1, 0, A, B), dp(memo, 1, 1, A, B)+1);
    }
	
	public int dp (int[][] memo, int pos, int swap, int[] A, int[] B) {
		if (pos >= A.length) return 0;
		if (memo[pos][swap] != -1) return memo[pos][swap];
		int lastA = 0;
		int lastB = 0;
		if (swap==1) {
			lastA = B[pos-1];
			lastB = A[pos-1];
		}
		else {
			lastA = A[pos-1];
			lastB = B[pos-1];
		}
		int val = (int)(1e9);
		if (A[pos] > lastA && B[pos] > lastB) {
			// can not swap
			val = Math.min(val, dp(memo, pos+1, 0, A, B));
		}
		if (A[pos] > lastB && B[pos] > lastA) {
			// can swap
			val = Math.min(val, dp(memo, pos+1, 1, A, B) + 1);
		}
		return memo[pos][swap] = val;
	}
}