import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BuildaMatrixWithConditions {
    
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        // build a topological sort using both row conditions and col conditions
        int n = rowConditions.length;
        int m = colConditions.length;
        List<Integer>[] gRow = new ArrayList[k];
        List<Integer>[] gCol = new ArrayList[k];
        init(gRow, rowConditions, k);
        init(gCol, colConditions, k);

        boolean[] open = new boolean[k];
        boolean[] visited = new boolean[k];
        for (int i = 0; i < k; i++) {
            if (hasCycle(i, gRow, open, visited)) return new int[][]{};
        }
        open = new boolean[k];
        visited = new boolean[k];
        for (int i = 0; i < k; i++) {
            if (hasCycle(i, gCol, open, visited)) return new int[][]{};
        }

        // now, do a topological sort
        List<Integer> topoRow = topoSort(gRow, k);
        List<Integer> topoCol = topoSort(gCol, k);

        Map<Integer, Integer> topoRowMap = new HashMap<>();
        for (int i = 0; i < k; i++) {
            topoRowMap.put(topoRow.get(i), i);
        }

        int[][] ans = new int[k][k];
        for (int i = 0; i < k; i++) {
            int col = i;
            int val = topoCol.get(i);
            int row = topoRowMap.get(val);

            // convert back to 1-based value
            ans[row][col] = val+1;
        }

        return ans;
    }

    private List<Integer> topoSort(List<Integer>[] g, int k) {
        List<Integer> topo = new ArrayList<>();
        Queue<Integer> q = new ArrayDeque<>();
        int[] indegree = new int[k];
        for (int i = 0; i < k; i++) {
            for (Integer to : g[i]) {
                indegree[to]++;
            }
        }

        for (int i = 0; i < k; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();
            topo.add(cur);
            for (Integer to : g[cur]) {
                indegree[to]--;
                if (indegree[to] == 0) {
                    q.add(to);
                }
            }
        }

        return topo;
    }

    private boolean hasCycle(int node, List<Integer>[] g, boolean[] open, boolean[] visited) {
        if (open[node]) return true;
        if (visited[node]) return false;
        open[node] = true;
        visited[node] = true;
        
        for (Integer to : g[node]) {
            if (hasCycle(to, g, open, visited)) return true;
        }

        open[node] = false;
        return false;
    }

    private void init(List<Integer>[] g, int[][] conditions, int k) {
        for (int i = 0; i < k; i++) g[i] = new ArrayList<>();

        for (int i = 0; i < conditions.length; i++) {
            g[conditions[i][0]-1].add(conditions[i][1]-1);
        }
    }
}