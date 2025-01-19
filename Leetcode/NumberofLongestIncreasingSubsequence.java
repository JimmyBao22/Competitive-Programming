
import java.util.*;

public class NumberofLongestIncreasingSubsequence {

	// https://leetcode.com/problems/number-of-longest-increasing-subsequence/
	
	public static void main(String[] args) {
		//int[] arr = new int[] {1,3,5,4,7};
		//int[] arr = new int[] {1,2,4,3,5,4,7,2};
		//int[] arr = new int[] {3,4,6,2,3,4,6,6,2,3};
		int[] arr = new int[] {2,2,2,2,2};
		System.out.println(findNumberOfLIS(arr));
	}

	public static int findNumberOfLIS(int[] nums) {
        int n = nums.length;
		int[] length = new int[n];
			// longest length ending at this index
		Arrays.fill(length, 1);
		for (int i=1; i<n; i++) {
			for (int j=0; j<i; j++) {
				if (nums[i] > nums[j]) {
					length[i] = Math.max(length[i], length[j]+1);
				}
			}
		}
		int maxlength = 1;
		for (int i=0; i<n; i++) {
			maxlength = Math.max(maxlength, length[i]);
		}
		if (maxlength == 1) return n;
		int[] count = new int[n];
		count[0] = 1;
		for (int i=1; i<n; i++) {
			for (int j=0; j<i; j++) {
				if (nums[i] > nums[j] && length[j]+1 == length[i]) {
					count[i] += count[j];
				}
			}
			if (count[i] == 0) count[i] = 1;
		}
		int total =0;
		for (int i=0; i<n; i++) {
			if (length[i] == maxlength) {
				total += count[i];
			}
		}
		return total;
    }
}