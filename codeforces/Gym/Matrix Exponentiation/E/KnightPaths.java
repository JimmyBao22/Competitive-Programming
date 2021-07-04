
import java.util.*;
import java.io.*;

public class KnightPaths {

	// https://codeforces.com/gym/102644/problem/E
	
	static long mod;
	static long secmod;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("KnightPaths"));

		secmod = pow(2, 16);
		mod = secmod * secmod;
		
		long k = Long.parseLong(in.readLine());
		
		long[][] board = new long[65][65];
		
		for (int row=0; row<8; row++) {			// what is possible to do
			for (int col=0; col<8; col++) {
				for (int rowchange : new int[] {-2, -1, 1, 2}) {
					for (int colchange : new int[] {-2, -1, 1, 2}) {
						if (Math.abs(rowchange)!=Math.abs(colchange)) {
							int newrow = row + rowchange;
							int newcol = col + colchange;
							if (newrow >=0 && newrow <8 && newcol>=0 && newcol < 8) {
								board[8*row + col][8*newrow + newcol] = 1;
									// possible to get from (row,col) to (newrow, newcol)
							}
						}
					}
				}
			}
		}
		
		for (int i=0; i<=64; i++) board[i][64] = 1;
		
		long[][] prod = new long[65][65];
		for (int i=0; i<65; i++) prod[i][i] = 1;
		
		k++;
		while (k > 0) {
			if (k %2 == 1) {
				prod = mult(prod, board);
			}
			board = mult(board, board);
			k >>= 1;
		}

		System.out.println(prod[0][64]);
	}
	
	public static long[][] mult(long[][] a, long[][] b) {
		long[][] product = new long[a.length][b.length];
		for (int i=0; i<a.length; i++) {
			for (int j=0; j<a.length; j++) {
				for (int k=0; k<a.length; k++) {
					product[i][k] += (a[i][j]%secmod * (b[j][k]%secmod)) + ((long)(a[i][j]/secmod)) * (b[j][k]%secmod)* secmod + ((long)(b[j][k]/secmod)) * (a[i][j]%secmod) * secmod;
						// ^ so that there is no overflow. Instead of doing mod 2^32, put it into form (a1*2^(16) + r1)(a2*2^(16) + r2)
						// so no overflow
					product[i][k] %= mod;
				}
			}
		}
		return product;
	}
	
	static long pow(long a, long b) {
        // a^b
    	long ans=1;
    	while (b >0) {
    		if (b%2 == 1) {
    			ans *= a;
    		}
    		a *= a;
    		b >>=1;
    	}
    	return ans;
    }
}
