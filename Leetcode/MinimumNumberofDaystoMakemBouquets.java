
import java.util.*;
import java.io.*;

public class MinimumNumberofDaystoMakemBouquets {

	// https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/
	
	public static void main(String[] args) {
		int[] arr = new int[] {30,49,11,66,54,22,2,57,35};
		System.out.println(minDays(arr, 3, 3));
	}
		
	public static int minDays(int[] bloomDay, int m, int k) {
		int n = bloomDay.length;
		if (m * k > n) return -1;
		int minday = Integer.MAX_VALUE;
        int maxday = Integer.MIN_VALUE;
		for (int i=0; i<n; i++) {
			minday = Math.min(minday, bloomDay[i]);
			maxday = Math.max(maxday, bloomDay[i]);
		}
		while (minday < maxday) {
			int mid = minday + (maxday - minday)/2;
			int bouq = 0;
			int curflowers = 0;
			// check, if the day was mid, how many bouquets can be made
			for (int i=0; i<n; i++) {
				if (bloomDay[i] > mid) {
					curflowers= 0;
				}
				else {
					curflowers++;
					if (curflowers == k) {
						bouq++;
						curflowers=0;
					}
				}
			}
			if (bouq >= m) {
				// enough bouquets
				maxday = mid;
			}
			else {
				minday = mid+1;
			}
		}
		return minday;
    }
}