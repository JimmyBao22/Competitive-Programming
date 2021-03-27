
import java.util.*;
import java.io.*;

public class KnightProbabilityinChessboard {

	// https://leetcode.com/problems/knight-probability-in-chessboard/
	
	public static void main(String[] args) {
		System.out.println(knightProbability(8,30,6,4));
	}
	
	public static double knightProbability(int N, int K, int r, int c) {
		double[][][] x = new double[K+1][N][N];
        	// each move, then the board
        x[0][r][c] = 1;
        for (int i=1; i<K+1; i++) {
        	for (int row = 0; row <N; row++) {
        		for (int col=0; col<N; col++) {
        			if (row -1 >= 0 && col - 2 >= 0) {
        				x[i][row][col] += x[i-1][row-1][col-2]/8;
        			}
        			if (row -2 >= 0 && col - 1 >= 0) {
        				x[i][row][col] += x[i-1][row-2][col-1]/8;
        			}
        			if (row - 1 >= 0 && col + 2 < N) {
        				x[i][row][col] += x[i-1][row-1][col+2]/8;
        			}
        			if (row -2 >= 0 && col +1 < N) {
        				x[i][row][col] += x[i-1][row-2][col+1]/8;
        			}
        			if (row +1 <N && col - 2 >= 0) {
        				x[i][row][col] += x[i-1][row+1][col-2]/8;
        			}
        			if (row +2 <N && col - 1 >= 0) {
        				x[i][row][col] += x[i-1][row+2][col-1]/8;
        			}
        			if (row +1 <N && col + 2 < N) {
        				x[i][row][col] += x[i-1][row+1][col+2]/8;
        			}
        			if (row +2 <N && col +1 < N) {
        				x[i][row][col] += x[i-1][row+2][col+1]/8;
        			}
        		}
        	}
        }
        double tot = 0.0;
        for (int i=0; i<N; i++) {
        	for (int j=0; j<N; j++) {
        		tot += x[K][i][j];
        	}
        }
        return tot;
    }
}
