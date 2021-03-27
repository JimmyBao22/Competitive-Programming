
import java.util.*;
import java.io.*;

public class UniqueBinarySearchTrees {

	// https://leetcode.com/problems/unique-binary-search-trees/
	
	public static void main(String[] args) {
		System.out.println(numTrees(8));
	}
	
	static int[][] memo;
	
	public static int numTrees(int n) {
        memo = new int[n+2][n+2];
        for (int i=0; i<n; i++) Arrays.fill(memo[i], -1);
        return rec(0, n+1);
    }

	public static int rec(int left, int right) {
		if (right - left <= 1) return 1;
		if (memo[left][right] != -1) return memo[left][right];
		int cur=0;
		for (int i=left + 1; i < right; i++) {
			// take element i
			cur += (rec(left, i) * rec(i, right));
		}
		
		return memo[left][right] = cur;
	}
}