import java.util.ArrayList;

class MinimumScoreAfterRemovalsonaTree {

    // https://leetcode.com/problems/minimum-score-after-removals-on-a-tree/description/

    private int n;
    private ArrayList<Integer>[] g;
    private int[] subtreeXOR;
    private int[][] parent;
    private int[] depth;
    private static final int LOG = 32;

    public int minimumScore(int[] nums, int[][] edges) {
        n = nums.length;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < n-1; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            g[u].add(v);
            g[v].add(u);
        }

        subtreeXOR = new int[n];
        parent = new int[n][LOG];
        depth = new int[n];
        dfs(nums, 0, -1, 0);

        buildParents();

        int minDiff = Integer.MAX_VALUE;
        // now, try every pair of edges
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n-1; j++) {
                int[] edges1 = edges[i];
                int[] edges2 = edges[j];

                // make the first element the one with smaller depth
                if (depth[edges1[0]] > depth[edges1[1]]) {
                    swap(edges1);
                }
                if (depth[edges2[0]] > depth[edges2[1]]) {
                    swap(edges2);
                }

                // compare LCA
                int lca = LCA(edges1[0], edges2[0]);
                int xor1 = subtreeXOR[0];
                int xor2 = subtreeXOR[edges1[1]];
                int xor3 = subtreeXOR[edges2[1]];

                boolean samePath = false;
                if (lca == edges1[0] || lca == edges2[0]) {
                    // Check if edges1 and edges2 are on the same path
                    int lca2 = LCA(edges1[1], edges2[1]);
                    samePath = (lca2 == edges1[1] || lca2 == edges2[1]);
                }

                if (samePath) {
                    // xor2 and xor3 are overlapping
                    // remove the higher up (lower depth) subtree from xor1
                    // then, remove the lower (higher depth) one from the other
                    if (depth[edges1[0]] < depth[edges2[0]]) {
                        xor1 ^= xor2;
                        xor2 ^= xor3;
                    } else {
                        xor1 ^= xor3;
                        xor3 ^= xor2;
                    }
                } else {
                    // xor2 and xor3 are non-overlapping
                    // remove those subtrees from xor1
                    xor1 ^= xor2;
                    xor1 ^= xor3;
                }

                int maxXOR = Math.max(xor1, Math.max(xor2, xor3));
                int minXOR = Math.min(xor1, Math.min(xor2, xor3));
                minDiff = Math.min(minDiff, maxXOR - minXOR);
            }
        }

        return minDiff;
    }

    private int LCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // now, depth[u] > depth[v]
        // make them the same depth
        int depthDiff = depth[u] - depth[v];
        for (int i = LOG-1; i >= 0; i--) {
            if (((depthDiff >> i) & 1) == 1) {
                u = parent[u][i];
            }
        }

        if (u == v) return u;

        // now that they're the same depth, we can find xor
        for (int i = LOG-1; i >= 0; i--) {
            if (parent[u][i] != parent[v][i]) {
                u = parent[u][i];
                v = parent[v][i];
            }
        }

        return parent[u][0];
    }

    // returns subtree xor
    private int dfs(int[] nums, int node, int p, int d) {
        parent[node][0] = p;
        depth[node] = d;
        int xor = nums[node];
        for (Integer to : g[node]) {
            if (to != p) {
                xor ^= dfs(nums, to, node, d+1);
            }
        }
        return subtreeXOR[node] = xor;
    }

    private void buildParents() {
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                if (parent[i][j-1] != -1) {
                    parent[i][j] = parent[parent[i][j-1]][j-1];
                } else {
                    parent[i][j] = -1;
                }
            }
        }
    }

    private void swap(int[] arr) {
        int temp = arr[0];
        arr[0] = arr[1];
        arr[1] = temp;
    }
}