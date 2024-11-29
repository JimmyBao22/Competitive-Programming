import java.util.Arrays;
import java.util.PriorityQueue;

class MinimumObstacleRemovaltoReachCorner {

    // https://leetcode.com/problems/minimum-obstacle-removal-to-reach-corner/description/

    public int minimumObstacles(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        int[] dirx = {1, -1, 0, 0};
        int[] diry = {0, 0, 1, -1};

        // stores the minimum obstacles that are required to be removed to reach this cell
        int[][] minObstaclesRemoved = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(minObstaclesRemoved[i], Integer.MAX_VALUE);
        }

        PriorityQueue<Cell> pq = new PriorityQueue<>();
        pq.add(new Cell(0, 0, 0));

        while (!pq.isEmpty()) {
            Cell curCell = pq.poll();
            int x = curCell.x;
            int y = curCell.y;
            int obs = curCell.obstacles;
            if (x == n-1 && y == m-1) return obs;
            if (minObstaclesRemoved[x][y] <= obs) continue;
            minObstaclesRemoved[x][y] = obs;

            for (int i = 0; i < 4; i++) {
                int newx = x + dirx[i];
                int newy = y + diry[i];
                if (!outOfBounds(newx, newy, n, m)) {
                    // add to pq
                    if (grid[newx][newy] == 1) {
                        pq.add(new Cell(newx, newy, obs + 1));
                    } else {
                        pq.add(new Cell(newx, newy, obs));
                    }
                }
            }
        }

        return -1;
    }

    private boolean outOfBounds(int x, int y, int n, int m) {
        return x < 0 || y < 0 || x >= n || y >= m;
    }

    private class Cell implements Comparable<Cell> {
        int x, y, obstacles;

        Cell(int x, int y, int obstacles) {
            this.x = x;
            this.y = y;
            this.obstacles = obstacles;
        }   

        public int compareTo(Cell c) {
            return obstacles - c.obstacles;
        }
    }
}