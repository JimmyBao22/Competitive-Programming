
import java.util.*;
import java.io.*;

public class PartitionArrayIntoThreePartsWithEqualSum {

	// https://leetcode.com/problems/partition-array-into-three-parts-with-equal-sum/
	
	public static void main(String[] args) {
		int[] arr = new int[] {10,-10,10,-10,10,-10,10,-10};
		canThreePartsEqualSum(arr);
	}
	
	public static boolean canThreePartsEqualSum(int[] A) {
        int n = A.length;
        int[] prefix = new int[n];
        prefix[0] = A[0];
        for (int i=1; i<n; i++) {
            prefix[i] = A[i]+ prefix[i-1];
        }
        if (prefix[n-1] %3 != 0) return false;
        boolean first=false;
        boolean second=false;
        for (int i=0; i<n; i++) {
            if (!first && prefix[i] == prefix[n-1]/3) first=true;
            else if (i!=n-1 && first && prefix[i] == prefix[n-1]*2/3) second=true;
        }
        return first&&second;
    }

}
