
import java.util.*;
import java.io.*;

public class CountSquareSubmatriceswithAllOnes {

	// https://leetcode.com/problems/count-square-submatrices-with-all-ones/
	
	public static void main(String[] args) {

	}
	
	public static int countSquares(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
		int[][] dp = new int[n][m];
		
		for (int i=0; i<n; i++) {
			dp[i][0] = matrix[i][0];
		}
		for (int i=0; i<m; i++) {
			dp[0][i] = matrix[0][i];
		}
		for (int i=1; i<n; i++) {
			for (int j=1; j<m; j++) {
				if (matrix[i][j] == 1) dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j-1])) + 1;
			}
		}
		
		int total = 0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) {
				total += dp[i][j];
			}
		}
		return total;
    }
}