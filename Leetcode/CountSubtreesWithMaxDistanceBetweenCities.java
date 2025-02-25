import java.util.*;

public class CountSubtreesWithMaxDistanceBetweenCities {
    
    // https://leetcode.com/problems/count-subtrees-with-max-distance-between-cities/

    ArrayList<Integer>[] g;
    int d, count;

    @SuppressWarnings("unchecked")
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        // O(n 2^n)
        // try 2^n possibilities. then create the tree and iterate through

        int[] ans = new int[n-1];
        g = new ArrayList[n];
        for (int j = 1; j < (1 << n); j++) {
            int root = 0;
            d = 0;
            count = 0;
            for (int k = 0; k < n; k++) {
                if (((j >> k) & 1) == 1) {
                    root = k;
                    count++;
                }
                g[k] = new ArrayList<>();
            }
            for (int k = 0; k < n-1; k++) {
                if ((j >> (edges[k][0]-1) & 1) == 1 && 
                    (j >> (edges[k][1]-1) & 1) == 1) {
                    
                    g[edges[k][0] - 1].add(edges[k][1] - 1);
                    g[edges[k][1] - 1].add(edges[k][0] - 1);
                }
            }

            dfs(root, -1);
            if (d != 0 && count == 0) {
                ans[d-1]++;
            }
        }
        return ans;
    }

    public int dfs(int cur, int parent) {
        count--;
        int maxD = 0;
        for (int to : g[cur]) {
            if (to != parent) {
                int ret = dfs(to, cur); // doesn't include the current node
                d = Math.max(d, maxD + ret);
                maxD = Math.max(maxD, ret);
            }
        }
        return maxD + 1;
    }

}
