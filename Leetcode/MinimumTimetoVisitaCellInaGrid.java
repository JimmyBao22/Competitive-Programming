import java.util.Arrays;
import java.util.PriorityQueue;

class MinimumTimetoVisitaCellInaGrid {

    // https://leetcode.com/problems/minimum-time-to-visit-a-cell-in-a-grid/
    
    public int minimumTime(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        int[] dirx = {1, -1, 0, 0};
        int[] diry = {0, 0, 1, -1};

        // if the original cell (0,0) can't move anywhere, return -1
        if (grid[0][1] > 1 && grid[1][0] > 1) return -1;

        // stores the minimum time needed to reach this cell
        int[][] minTimeNeeded = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(minTimeNeeded[i], Integer.MAX_VALUE);
        }

        PriorityQueue<Cell> queue = new PriorityQueue<>();
        // Queue<Cell> queue = new ArrayDeque<>();
        queue.add(new Cell(0, 0, 0));

        while (!queue.isEmpty()) {
            Cell curCell = queue.poll();
            int x = curCell.x;
            int y = curCell.y;
            int t = curCell.time;
            if (x == n-1 && y == m-1) return t;
            if (minTimeNeeded[x][y] <= t) continue;
            minTimeNeeded[x][y] = t;

            for (int i = 0; i < 4; i++) {
                int newx = x + dirx[i];
                int newy = y + diry[i];
                if (!outOfBounds(newx, newy, n, m)) {
                    // add to queue
                    // you can reach this cell at time t+1, t+3, t+5, ... 
                    // this is because in the previous move, you could've delayed it by 2 sec by moving back and forth one time
                    int newTime = t+1;
                    if (t+1 < grid[newx][newy]) {
                        if ((t+1)%2 != grid[newx][newy]%2) {
                            newTime = grid[newx][newy]+1;
                        } else {
                            newTime = grid[newx][newy];
                        }
                    }
                    queue.add(new Cell(newx, newy, newTime));
                }
            }
        }

        return -1;
    }

    private boolean outOfBounds(int x, int y, int n, int m) {
        return x < 0 || y < 0 || x >= n || y >= m;
    }

    private class Cell implements Comparable<Cell> {
        int x, y, time;

        Cell(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }   

        public int compareTo(Cell c) {
            return time - c.time;
        }
    }
}