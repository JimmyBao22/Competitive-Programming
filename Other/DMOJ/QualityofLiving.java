
import java.util.*;
import java.io.*;

public class QualityofLiving {

	// https://dmoj.ca/problem/ioi10p3
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
//		long[][] Q= {{5, 11, 12, 16, 25}, 
//				   {17, 18,  2,  7, 10},
//				    {4, 23, 20,  3,  1},
//				   {24, 21, 19, 14,  9},
//				    {6, 22,  8, 13, 15}};
		long[][] Q = {{6, 1,  2, 11,  7,  5},
			    {9,  3,  4, 10, 12,  8}};
		
		System.out.println(rectangle(2,6,1,5,Q));
	}
	
	public static long rectangle(int R, int C, int H, int W, long[][] Q) {
		long max =0;
		long min = Long.MAX_VALUE;
		for (int i=0; i<R; i++) {
			for (int j=0; j<C; j++) {
				max = Math.max(max, Q[i][j]);
				min = Math.min(min, Q[i][j]);
			}
		}
		
		while (min < max) {
			long middle = min + (max - min)/2;
			if (check(R, C, H, W, Q, middle)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		return min;
	}
	
	public static boolean check(int R, int C, int H, int W, long[][] Q, long num) {
		long[][] rep = new long[R][C];
		for (int i=0; i<R; i++) {
			for (int j=0; j<C; j++) 
			{
				if (Q[i][j]>num) rep[i][j] = 0;
				else rep[i][j] = 1;
			}
		}
		
		long[][] sum = new long[R][C];
		sum[0][0] = rep[0][0];
		for (int i=1; i<R; i++) {
			sum[i][0] = rep[i][0] + sum[i-1][0];
		}
		for (int j=1; j<C; j++) {
			sum[0][j] = rep[0][j] + sum[0][j-1];
		}
		for (int i=1; i<R; i++) {
			for (int j=1; j<C; j++) {
				sum[i][j] = rep[i][j] + sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1];
			}
		}
		
		// 1 = less than or equal to
		// we want >= (H*W+1)/2 ones
		for (int i=0; i<=R-H; i++) {
			for (int j=0; j<=C-W; j++) {
				long cursum  =0;
				if (i-1<0 && j-1<0) {
					cursum = sum[i+H-1][j+W-1];
				}
				else if (i-1<0) cursum = sum[i+H-1][j+W-1] - sum[i+H-1][j-1];
				else if (j-1<0) cursum = sum[i+H-1][j+W-1] - sum[i-1][j+W-1];
				else cursum = sum[i+H-1][j+W-1] - sum[i-1][j+W-1] - sum[i+H-1][j-1] + sum[i-1][j-1];
				
				if (cursum>=(H*W+1)/2) return true;
				
			}
		}
		
		return false;
	}
	
	public static void print(long[][] Q) {
		int n = Q.length;
		int m = Q[0].length;
		for (int i=0; i<n; i++) {
			for (int j=0; j<m; j++) System.out.print(Q[i][j] + " ");
			System.out.println();
		}
	}
}