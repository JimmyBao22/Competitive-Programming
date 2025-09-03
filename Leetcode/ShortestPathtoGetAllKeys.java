import java.util.Arrays;
import java.util.Queue;
import java.util.ArrayDeque;

class ShortestPathtoGetAllKeys {

    private static final int INF = (int)(1e9);
    private int n, m, k;
    private int[] dirx = {-1, 1, 0, 0};
    private int[] diry = {0, 0, -1, 1};

    public int shortestPathAllKeys(String[] grid) {
        n = grid.length;
        m = grid[0].length();
        k = 0;
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = grid[i].charAt(j);
                if (isLowerCase(c)) {
                    // letter
                    k++;
                }
                if (c == '@') {
                    startX = i;
                    startY = j;
                }
            }
        }

        int[][][] visited = new int[n][m][1 << k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(visited[i][j], INF);
            }
        }

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{startX, startY, 0, 0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int i = cur[0];
            int j = cur[1];
            int keys = cur[2];
            int steps = cur[3];
            if (visited[i][j][keys] <= steps) continue;
            visited[i][j][keys] = steps;

            // found all keys
            if (keys == (1 << k) - 1) return steps;

            for (int g = 0; g < 4; g++) {
                int newI = i + dirx[g];
                int newJ = j + diry[g];
                int newKeys = keys;
                int newSteps = steps + 1;
                if (outOfBounds(newI, newJ)) continue;
                char c = grid[newI].charAt(newJ);
                if (c == '#') continue;
                
                if (isLowerCase(c)) {
                    int v = c - 'a';
                    newKeys |= (1 << v);
                }
                if (isUpperCase(c)) {
                    int v = c - 'A';
                    if (((newKeys >> v) & 1) == 0) {
                        // haven't found key for this lock yet
                        continue;
                    }
                }

                q.add(new int[]{newI, newJ, newKeys, newSteps});
            }
        }

        // never found all keys
        return -1;
    }

    private boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private boolean outOfBounds(int i, int j) {
        return i < 0 || j < 0 || i >= n || j >= m;
    }
}