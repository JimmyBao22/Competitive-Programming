
import java.util.*;

public class ConstrainedSubsetSum {

	// https://leetcode.com/problems/constrained-subsequence-sum/
	
	public static void main(String[] args) {
		int[] a = new int[] {-1,-2,-3};
		System.out.println(constrainedSubsetSum(a,2));
	}

	public static int constrainedSubsetSum(int[] nums, int k) {
        int[] dp = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
        	dp[i] = nums[i];
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
			public int compare(int[] x, int[] y) {
				return y[0] - x[0];	// sort by largest first
			}
        });
        
        int[] currentSubset = new int[] {dp[0], 0};
        pq.add(currentSubset);
        
        for (int i = 1; i < nums.length; i++) {
    		while (pq.size() > 0) {
    			if (pq.peek()[1] < i-k) pq.poll();
    			else break;
    		} 
    		dp[i] = Math.max(dp[i], nums[i] + pq.peek()[0]);
    		currentSubset = new int[] {dp[i], i};
    		pq.add(currentSubset);
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < dp.length; i++) {
        	max = Math.max(max, dp[i]);
        	
        }
        return max; 
    }
	
}

/*
	TLE
	
	int[] dp = new int[nums.length];
	for (int i = 0; i < nums.length; i++) dp[i] = nums[i];
	for (int i = 1; i <= k; i++) {
		for (int j = 0; j < i; j++) {
			dp[i] = Math.max(dp[i], dp[j]+nums[i]);
		}
	}
	for (int i = k+1; i < nums.length; i++) {
		for (int j = i-k; j < i; j++) {
			dp[i] = Math.max(dp[i], dp[j]+nums[i]);
		}
		
	}
	int max = Integer.MIN_VALUE;
	for (int i = 0; i < dp.length; i++) {
		max = Math.max(max, dp[i]);
		
	}
	return max; 
*/
