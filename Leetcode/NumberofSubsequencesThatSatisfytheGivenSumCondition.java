
import java.util.*;

public class NumberofSubsequencesThatSatisfytheGivenSumCondition {

	// https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/
	
	public static void main(String[] args) {
		int[] arr = new int[] {2,3,3,4,6,7};
		System.out.println(numSubseq(arr, 12));
	}
	
	static long mod = (long)1e9+7;
	public static int numSubseq(int[] nums, int target) {
        long ans=0;
        Arrays.parallelSort(nums);
        
        long[] pow = new long[nums.length];
        pow[0] = 1;
        for (int i=1; i<pow.length; i++) {
        	pow[i] = pow[i-1] * 2;
        	pow[i] %= mod;
        }
        
        
        if (nums[0] + nums[0] > target) return 0;
        
        int n = nums.length;
        
        for (int i=0; i<n; i++) {
        	if (nums[i] + nums[i] > target) break;
        	int secindex=secondindex(nums, target, i);
        	//ans += power(secindex - i);
        	ans += pow[secindex-i];
        	ans %= mod;
        }
        ans %= mod;
        if (ans < 0) ans += mod;
        return (int)ans;
	}
	
	public static int secondindex(int[] arr, int target, int curminvalindex) {
		int min = curminvalindex;
		int max = arr.length-1;
		while (min < max) {
			int middle = (min+max+1)/2;
			if (arr[middle] + arr[curminvalindex] <= target) {
				min = middle;
			}
			else max = middle-1;
		}
		return min;
	}
}