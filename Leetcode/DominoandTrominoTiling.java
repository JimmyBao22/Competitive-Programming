
import java.util.*;
import java.io.*;

public class DominoandTrominoTiling {

	// https://leetcode.com/problems/domino-and-tromino-tiling/
	
	public static void main(String[] args) {
		System.out.println(numTilings(4));
		System.out.println(numTilings(5));
		System.out.println(numTilings(30));
		System.out.println(numTilings(40));
		System.out.println(numTilings(60));

	}
	
	public static int numTilings(int N) {
        int mod = (int)1e9+7;
		long[] dp = new long[N+1];
        dp[0] = 1;
        if (N == 0) return 0;
        dp[1] = 1;
        if (N==1) return 1;
        dp[2] = 2;
        if (N==2) return 2;
        dp[3] = 5;
        if (N==3) return 5;
        for (int i=4; i<N+1; i++) {
        	dp[i] += dp[i-1];
        	dp[i] %= mod;
        	dp[i] += dp[i-2];
        	dp[i] %= mod;
        	dp[i] += 2*dp[i-3];
        	dp[i] %= mod;
        	for (int j=4; i-j >= 0; j++) {
        		dp[i] += 2*dp[i-j];
            	dp[i] %= mod;
        	}
        }
    	dp[N] %= mod;
        return (int)dp[N];
    }
}

/*
	Number of ways to tile 
	X
	X
	
	XX
	YY
	
	XXY
	XYY
	
	XYY
	XXY
	
	
	V can go infinitely long starting from 4, x2 for direction of L
	
	XXZZ
	XYYZ
	
	XYYZ
	XXZZ

	XYYHH
	XXZZH
		
	XYYHH      AA
	XXZZJJ ...  A
*/
