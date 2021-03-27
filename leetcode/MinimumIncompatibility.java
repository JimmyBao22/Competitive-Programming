
import java.util.*;
import java.io.*;

public class MinimumIncompatibility {

	// https://leetcode.com/problems/minimum-incompatibility/
	
	public static void main(String[] args) {
		int[] arr = {2,2,2,2};
		System.out.println(minimumIncompatibility(arr, 2));
	}

	static HashMap<Integer, Integer> m;
	public static int minimumIncompatibility(int[] nums, int k) {        
        m = new HashMap<>();
        
        int ans = dp(0, nums, k);
        if (ans >= (int)(1e9)) {
        	return -1;
        }
        else return ans;
    }
	
	public static int dp(int bin, int[] nums, int k) {
		if (m.containsKey(bin)) return m.get(bin);
		int ans=(int)1e9;
		
		// number of values left that need to be taken
		int left = nums.length - Integer.bitCount(bin);
		
		if (left == 0) {
			return 0;
		}
		
		int n = nums.length;
		char[] cur = new char[n];
		
		for (int j=0; j<n; j++) {
			if (((1 << (n - j - 1)) & bin) >= 1) {
				cur[j] = '1';      // position has been taken
			}
			else cur[j] = '0';    // position has not been taken yet
		}
		
		// the binary of i represents the what to take out of the number of positions left.
		// for example, if i = 1010, then if the cur is 101101001, then
			// for this i, it means we will at this turn take the first and third 0 inside cur.
			// the new cur will become 111101101
			
		o: for (int i=1; i<(1 << left); i++) {
			if (Integer.bitCount(i) != nums.length/k) continue;
			
			int seen=0;
			int b = 0;
			ArrayList<Integer> take = new ArrayList<>();
			char[] copy = new char[n];
			for (int j=0; j<n; j++) {
				copy[j] = cur[j];
				if (cur[j] == '0') {
					if (((1 << seen) & i) >= 1) {
						copy[j] = '1';
						if (take.contains(nums[j])) continue o;     // cannot put multiple of same number in same group
						take.add(nums[j]);
					}
					seen++;     // number of 0's seen so far
				}
				
				if (copy[j] == '1') b += (1 << (n - j - 1));
				
			}
			
			Collections.sort(take);
			if (take.get(take.size()-1) - take.get(0) >= ans) continue;       // if the difference between last and first that i am currently taking is already greater than ans, there is no point doing further dp 
			
			ans = Math.min(ans, dp(b, nums, k) + take.get(take.size()-1) - take.get(0));
		}
		
		m.put(bin, ans);
		return ans;
	}
}