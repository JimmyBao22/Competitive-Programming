import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ShortestPathinBinaryMatrix {

    // https://leetcode.com/problems/shortest-path-in-binary-matrix/

    final int INF = (int)(1e9);
    private int[] dirx = {-1,-1,-1,0,0,1,1,1};
    private int[] diry = {-1,0,1,-1,1,-1,0,1};

    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;
        
        // Bidirectional BFS
        Queue<Cell> qBeg = new ArrayDeque<>();
        Queue<Cell> qEnd = new ArrayDeque<>();
        qBeg.add(new Cell(0, 0, 1));
        qEnd.add(new Cell(n-1, n-1, 1));

        int[][] visitedBeg = new int[n][n];
        int[][] visitedEnd = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(visitedBeg[i], INF);
            Arrays.fill(visitedEnd[i], INF);
        }
        
        while (!qBeg.isEmpty() || !qEnd.isEmpty()) {
            int ret = 0;
            int iter = qBeg.size();
            for (int i = 0; i < iter; i++) {
                ret = updateQueue(qBeg, n, grid, visitedBeg, visitedEnd);
                if (ret != INF) return ret;
            }
            iter = qEnd.size();
            for (int i = 0; i < iter; i++) {
                ret = updateQueue(qEnd, n, grid, visitedEnd, visitedBeg);
                if (ret != INF) return ret;
            }
        }

        return -1;
    }

    private int updateQueue(Queue<Cell> q, int n, int[][] grid, int[][] myVisited, int[][] otherVisited) {
        Cell cell = q.poll();
        int x = cell.x, y = cell.y, c = cell.c;
        
        if (otherVisited[x][y] != INF) {
            return c + otherVisited[x][y] - 1;
        }
        if (myVisited[x][y] != INF) {
            return INF;
        }
        myVisited[x][y] = c;

        for (int i = 0; i < 8; i++) {
            int newX = x + dirx[i];
            int newY = y + diry[i];
            if (!outOfBounds(n, newX, newY) && grid[newX][newY] == 0) {
                q.add(new Cell(newX, newY, c+1));
            }
        }

        return INF;
    }

    private boolean outOfBounds(int n, int x, int y) {
        return x < 0 || y < 0 || x >= n || y >= n;
    }

    private class Cell {
        int x, y, c;
        Cell(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }
}

/*
    Normal BFS Solution:

    class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;
        
        boolean[][] visited = new boolean[n][n];
        Queue<Cell> q = new ArrayDeque<>();
        q.add(new Cell(0, 0, 1));
        visited[0][0] = true;
        
        int[] dirx = {-1,-1,-1,0,0,1,1,1};
        int[] diry = {-1,0,1,-1,1,-1,0,1};
        while (!q.isEmpty()) {
            Cell cell = q.poll();
            int x = cell.x, y = cell.y, c = cell.c;
            
            if (x == n-1 && y == n-1) return c;

            for (int i = 0; i < 8; i++) {
                int newX = x + dirx[i];
                int newY = y + diry[i];
                if (!outOfBounds(n, newX, newY) && grid[newX][newY] == 0 && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    q.add(new Cell(newX, newY, c+1));
                }
            }
        }

        return -1;
    }

    private boolean outOfBounds(int n, int x, int y) {
        return x < 0 || y < 0 || x >= n || y >= n;
    }

    private class Cell {
        int x, y, c;
        Cell(int x, int y, int c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }
}


 */
