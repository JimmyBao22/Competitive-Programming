import java.util.*;

public class MaximumSubarray {

	// https://leetcode.com/problems/maximum-subarray/
	
	public static void main(String[] args) {
		System.out.print(maxSubArray(new int[] {-2, -1}));
	}

	public static int maxSubArray(int[] nums) {
        int max = nums[0];
        int cursum = nums[0];
        for (int i = 1; i < nums.length; i++) {
        	if (cursum < 0) cursum = 0;
        	cursum += nums[i];
        	max = Math.max(cursum, max);
        }
        return max;
    }
	
}
