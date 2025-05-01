import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayDeque;

class MostProfitablePathinaTree {
    
    private int n;
    private List<Integer>[] g;

    @SuppressWarnings("unchecked")
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        n = amount.length;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < n-1; i++) {
            g[edges[i][0]].add(edges[i][1]);
            g[edges[i][1]].add(edges[i][0]);
        }
        List<Integer> path = new ArrayList<>();
        dfs(bob, -1, path);

        Queue<int[]> q = new ArrayDeque<>();
        Queue<int[]> nextQ = new ArrayDeque<>();
        q.add(new int[]{0, -1, 0});
        int time = 0;
        int maxScore = Integer.MIN_VALUE;
        while (true) {
            // use /2 to account for if bob and alice reach same node at same time
            if (time < path.size()) amount[path.get(time)] /= 2;
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int node = cur[0], parent = cur[1], score = cur[2];
                score += amount[node];
                if (node != 0 && g[node].size() == 1) {
                    // this is a leaf node
                    maxScore = Math.max(maxScore, score);
                } else {
                    // continue searching
                    for (Integer to : g[node]) {
                        if (to != parent) {
                            nextQ.add(new int[]{to, node, score});
                        }
                    }
                }
            }
            if (nextQ.isEmpty()) break;
            q = nextQ;
            nextQ = new ArrayDeque<>();
            if (time < path.size()) amount[path.get(time)] = 0;
            time++;
        }

        return maxScore;
    }

    private boolean dfs(int node, int p, List<Integer> path) {
        path.add(node);
        if (node == 0) return true;
        for (Integer to : g[node]) {
            if (to != p) {
                if (dfs(to, node, path)) return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }
}