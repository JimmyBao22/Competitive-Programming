
import java.util.*;
import java.io.*;

public class SoupServings {

	// https://leetcode.com/problems/soup-servings/
	
	public static void main(String[] args) {
		soupServings(660295675);
	}
	
	public static double soupServings(int N) {
        if (N/25 >= 500) return 1.0;
        	// it goes towards 0 for really large numbers
		double[][] memo = new double[(int)Math.ceil(N/25)+1][(int)Math.ceil(N/25)+1];
	    	// a, b
        	// value = probability
        for (int i=0; i<memo.length; i++) {
        	Arrays.fill(memo[i], -1);
        }
        return sim(N, N, memo);
	}
	
	public static double sim(int A, int B, double[][] memo) {
		if (B <= 0 && A > 0) {
			return 0;
		}
		if (B <= 0 && A <= 0) {
			return 1.0/2;
		}
		if (A <= 0 && B > 0) {
			return 1;
		}
		if (memo[(int)Math.ceil(A/25)][(int)Math.ceil(B/25)] != -1) {
			return memo[(int)Math.ceil(A/25)][(int)Math.ceil(B/25)];
		}
		double total = 0.0;
		total += sim(A - 100, B, memo)/4;
		total += sim(A - 75, B - 25, memo)/4;
		total += sim(A - 50, B - 50, memo)/4;
		total += sim(A - 25, B - 75, memo)/4;
		memo[(int)Math.ceil(A/25)][(int)Math.ceil(B/25)] = total;
		return total;
	}
}
