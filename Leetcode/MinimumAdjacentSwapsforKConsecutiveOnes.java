
import java.util.*;
import java.io.*;

public class MinimumAdjacentSwapsforKConsecutiveOnes {

	// https://leetcode.com/problems/minimum-adjacent-swaps-for-k-consecutive-ones/
	// my explanation - https://leetcode.com/problems/minimum-adjacent-swaps-for-k-consecutive-ones/discuss/987317/javac-two-pointers-with-prefix-sums
	
	public static void main(String[] args) {
		
	}
	
	public static int minMoves(int[] nums, int k) {
        	ArrayList<Integer> ones = new ArrayList<>();
		for (int i=0; i<nums.length; i++) {
			if (nums[i] == 1) ones.add(i);
		}
		int[] zeroespref = new int[nums.length];
		for (int i=0; i<nums.length; i++) {
			if (i == 0) {
        		zeroespref[0] = (nums[0] == 0 ? 1 : 0);
			}
			else {
				zeroespref[i] = zeroespref[i-1] + (nums[i] == 0 ? 1 : 0);
			}
		}

		int n = ones.size();
		int right=k-1;
		int left=0;
		int mindist = 0;

			// centered at 0
		for (int i=0; i<k; i++) {
			mindist += zeroespref[ones.get(i)] - zeroespref[ones.get(0)];
		}

		int curdist = mindist;

			// centered at i
		for (int i=1; i<n; i++) {
			int zeroeschange = zeroespref[ones.get(i)] - zeroespref[ones.get(i-1)];
			curdist += (i - left)*zeroeschange;
			curdist -= (right - i + 1)*zeroeschange;

			while (right+1<n) {
				int zeroesleft = zeroespref[ones.get(i)] - zeroespref[ones.get(left)];
				int zeroesright = zeroespref[ones.get(right+1)] - zeroespref[ones.get(i)];
				if (zeroesright < zeroesleft) {
					curdist -= zeroesleft;
					curdist += zeroesright;
				right++;
				left++;
				}
				else break;
			}
			mindist = Math.min(mindist, curdist);

			if (right == i && right+1 < n) {
				int zeroesleft = zeroespref[ones.get(i)] - zeroespref[ones.get(left)];
				int zeroesright = zeroespref[ones.get(right+1)] - zeroespref[ones.get(i)];
				curdist -= zeroesleft;
				curdist += zeroesright;
				right++;
				left++;
			}
		}
		return mindist;
    	}
}
