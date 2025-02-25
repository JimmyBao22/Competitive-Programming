import java.util.HashSet;
import java.util.Set;

class MakeALargeIsland {

    // https://leetcode.com/problems/making-a-large-island/?envType=daily-question&envId=2025-01-31

    private int n;
    private int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
    public int largestIsland(int[][] grid) {
        n = grid.length;
        boolean[][] visited = new boolean[n][n];
        DSU dsu = new DSU(n * n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dfs(i, j, i, j, visited, grid, dsu);
            }
        }

        int largest = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                if (grid[i][j] == 1) {
                    sum = dsu.size[dsu.findSet(i * n + j)];
                } else {
                    Set<Integer> parents = new HashSet<>();
                    for (int k = 0; k < 4; k++) {
                        int newI = i + dx[k];
                        int newJ = j + dy[k];
                        if (!outOfBounds(newI, newJ) && grid[newI][newJ] == 1) {
                            parents.add(dsu.findSet(newI * n + newJ));
                        }
                    }
                    for (Integer p : parents) {
                        sum += dsu.size[p];
                    }
                    sum++;  // for changing the 0 to a 1
                }
                largest = Math.max(largest, sum);
            }
        }

        return largest;
    }

    private void dfs(int i, int j, int pi, int pj, boolean[][] visited, int[][] grid, DSU dsu) {
        if (visited[i][j] || grid[i][j] == 0) return;
        visited[i][j] = true;
        dsu.union(i * n + j, pi * n + pj);
        for (int k = 0; k < 4; k++) {
            int newI = i + dx[k];
            int newJ = j + dy[k];
            if (!outOfBounds(newI, newJ)) {
                dfs(newI, newJ, i, j, visited, grid, dsu);
            }
        }
    }

    private boolean outOfBounds(int i, int j) {
        return i < 0 || j < 0 || i >= n || j >= n;
    }

    private class DSU {
        int[] parent, size;
        
        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int findSet(int i) {
            if (parent[i] == i) return i;
            return parent[i] = findSet(parent[i]);
        }

        void union(int i, int j) {
            int pi = findSet(i);
            int pj = findSet(j);
            
            if (pi != pj) {
                if (size[pi] < size[pj]) {
                    parent[pi] = pj;
                    size[pj] += size[pi];
                } else {
                    parent[pj] = pi;
                    size[pi] += size[pj];
                }
            }
        }
    }
}