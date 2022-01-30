
import java.util.*;
import java.io.*;

public class cowtip {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=689
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("cowtip.in"));
		
		int n = in.nextInt();
		int[][] board = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			int k = in.nextInt();
			for (int j = n-1; j >=0 ; j--) {
				board[i][j] = k%10;
				k /= 10;
			}
		}

		in.close();
		
		int count = 0;
		while (!sorted(board)) {
			outerloop:
			for (int i = board.length-1; i>=0; i--) {
				for (int j = board.length-1; j>=0; j--) {
					if (board[i][j] == 1) {
						board = swap(board, i, j);
						count++;
						break outerloop;
					}
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new File("cowtip.out"));
		System.out.println(count);
		out.println(count);
		out.close();
	}
	
	public static boolean sorted(int[][] board) {
		// is it all 0?
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != 0) return false;
			}
 		}
		return true;
	}
	
	public static int[][] swap(int[][] board, int stopx, int stopy) {
		for (int i = 0; i <= stopx; i++) {
			for (int j = 0; j <= stopy; j++) {
				if (board[i][j] == 1) board[i][j] = 0;
				else if (board[i][j] == 0) board[i][j] = 1;
			}
		}
		return board;
	}
}

/*
	start from bottom row and work up
	ex. 
	010		100		011		111		000
	101 --> 011 --> 100 --> 000 --> 000
	110		000		000		000		000
*/