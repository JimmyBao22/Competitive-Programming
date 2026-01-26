import java.io.*;
import java.util.*;

public class ModernArt {
    
    // https://usaco.org/index.php?page=viewproblem2&cpid=737

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("art.in"));
        
        // input
        int n = in.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            long row = in.nextLong();
            for (int j = n-1; j >= 0; j--) {
                grid[i][j] = (int)(row % 10);
                row /= 10;
            }
        }

        boolean[] possibleFirst = new boolean[11];
        Arrays.fill(possibleFirst, true);
        
        // for each color, figure out its bounding box and determine if there are any rectangles on top
        for (int k = 1; k < 11; k++) {
            // determine bounding box
            int minRow = Integer.MAX_VALUE;
            int minCol = Integer.MAX_VALUE;
            int maxRow = Integer.MIN_VALUE;
            int maxCol = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == k) {
                        minRow = Math.min(minRow, i);
                        minCol = Math.min(minCol, j);
                        maxRow = Math.max(maxRow, i);
                        maxCol = Math.max(maxCol, j);
                    }
                }
            }

            if (minRow == Integer.MAX_VALUE) {
                // this color doesn't appear
                possibleFirst[k] = false;
                continue;
            }

            // loop over bounding box and see if any colors overlap 
            for (int i = minRow; i <= maxRow; i++) {
                for (int j = minCol; j <= maxCol; j++) {
                    if (grid[i][j] != k) {
                        possibleFirst[grid[i][j]] = false;
                    }
                }
            }
        }

        int countPossibleFirst = 0;
        for (int i = 1; i < 11; i++) {
            if (possibleFirst[i]) countPossibleFirst++;
        }

        PrintWriter out = new PrintWriter(new File("art.out"));
        System.out.println(countPossibleFirst);
        out.println(countPossibleFirst);
        out.close();
    }
}