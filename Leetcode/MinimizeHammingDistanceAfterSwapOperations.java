import java.util.*;

public class MinimizeHammingDistanceAfterSwapOperations {

	// https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
	
	public static void main(String[] args) {

	}

	ArrayList<Integer>[] g;
	HashMap<Integer, Integer> counts, countt;
	boolean[] visited;
	@SuppressWarnings("unchecked")
	public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        int m = allowedSwaps.length;
        g = new ArrayList[n];
        for (int i=0; i<n; i++) g[i] = new ArrayList<>();
        for (int i=0; i<m; i++) {
        	g[allowedSwaps[i][0]].add(allowedSwaps[i][1]);
        	g[allowedSwaps[i][1]].add(allowedSwaps[i][0]);
        }
        
        int ans=0;
        visited= new boolean[n];
        for (int i=0; i<n; i++) {
        	if (!visited[i]) {
        		counts = new HashMap<>();
        		countt = new HashMap<>();
        		dfs(i, source, target);				// in dfs, you are able to swap between all
        												// nodes visited
        		for (Integer j : counts.keySet()) {
        			if (countt.containsKey(j)) {
        				counts.put(j, counts.get(j) - countt.get(j));
        			}
        			ans += Math.max(counts.get(j), 0);
        		}
        	}
        }
        
        return ans;
    }
	
	public void dfs(int node, int[] source, int[] target) {
		if (visited[node]) return;
		visited[node] = true;
		counts.put(source[node], counts.getOrDefault(source[node], 0)+1);
		countt.put(target[node], countt.getOrDefault(target[node], 0)+1);
		for (int i : g[node]) {
			dfs(i, source, target);
		}
	}
}