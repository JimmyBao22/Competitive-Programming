import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MaximumNumberofPointsFromGridQueries {
    
    // https://leetcode.com/problems/maximum-number-of-points-from-grid-queries/description/?envType=daily-question&envId=2025-03-28

    private int[] dirx = new int[]{1,-1,0,0}, diry = new int[]{0,0,-1,1};

    public int[] maxPoints(int[][] grid, int[] queries) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] a, int[] b) {
                return grid[a[0]][a[1]] - grid[b[0]][b[1]];
            }
        });
        pq.add(new int[]{0,0});

        List<Integer> orderOfGrid = new ArrayList<>();
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int i = cur[0], j = cur[1];
            if (visited[i][j]) continue;
            visited[i][j] = true;
            orderOfGrid.add(grid[i][j]);
            
            for (int k = 0; k < 4; k++) {
                int newI = i + dirx[k];
                int newJ = j + diry[k];
                if (!outOfBounds(newI, newJ, n, m)) {
                    pq.add(new int[]{newI, newJ});
                }
            }
        }

        // sort queries
        int q = queries.length;
        int[] ans = new int[q];
        TreeMap<Integer, List<Integer>> mapQueryToIndices = new TreeMap<>();
        for (int i = 0; i < q; i++) {
            if (!mapQueryToIndices.containsKey(queries[i])) mapQueryToIndices.put(queries[i], new ArrayList<>());
            mapQueryToIndices.get(queries[i]).add(i);
        }

        int gridOrderIndex = 0;
        for (Integer key : mapQueryToIndices.keySet()) {
            while (gridOrderIndex < orderOfGrid.size() && orderOfGrid.get(gridOrderIndex) < key) {
                gridOrderIndex++;
            }
            for (Integer index : mapQueryToIndices.get(key)) {
                ans[index] = gridOrderIndex;
            }
        }

        return ans;
    }

    private boolean outOfBounds(int i, int j, int n, int m) {
        return i < 0 || j < 0 || i >= n || j >= m;
    }
}
