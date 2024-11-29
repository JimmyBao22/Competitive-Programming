import java.util.ArrayDeque;

class MinimumCosttoMakeatLeastOneValidPathinaGrid {
    
    // https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/

    public int minCost(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        // 0-1 BFS
        ArrayDeque<Cell> deque = new ArrayDeque<>();
        deque.add(new Cell(0, 0, 0));

        boolean[][] visited = new boolean[n][m];

        int[] dirx = {0, 0, 1, -1};
        int[] diry = {1, -1, 0, 0};
        int[] sign = {1, 2, 3, 4};  // correspond to the signs in the problem statement
        while (!deque.isEmpty()) {
            Cell cell = deque.poll();
            int x = cell.x, y = cell.y, cost = cell.cost;

            if (x == n-1 && y == m-1) return cost;

            if (visited[x][y]) continue;
            visited[x][y] = true;

            // visit new stuff
            for (int i = 0; i < 4; i++) {
                int newX = x + dirx[i];
                int newY = y + diry[i];
                if (outOfBounds(n, m, newX, newY)) continue;
                
                if (grid[x][y] == sign[i]) {
                    // no cost
                    deque.push(new Cell(newX, newY, cost));
                } else {
                    // cost of 1
                    deque.add(new Cell(newX, newY, cost + 1));
                }
            }
        }

        return -1;
    }

    private boolean outOfBounds(int n, int m, int x, int y) {
        return x < 0 || y < 0 || x >= n || y >= m;
    }

    private class Cell {
        int x, y, cost;
        Cell (int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}