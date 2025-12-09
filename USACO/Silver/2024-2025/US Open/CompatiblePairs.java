import java.util.*;
import java.io.*;

public class CompatiblePairs {

    // https://usaco.org/index.php?page=viewproblem2&cpid=1519

    private static List<Integer>[] g;

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        Map<Long, Integer> mapDtoIndex = new HashMap<>();
        long[][] nd = new long[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            nd[i][0] = Integer.parseInt(st.nextToken());
            nd[i][1] = Integer.parseInt(st.nextToken());
            mapDtoIndex.put(nd[i][1], i);
        }

        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            long rest = a - nd[i][1];
            if (mapDtoIndex.containsKey(rest)) {
                int index = mapDtoIndex.get(rest);
                g[i].add(index);
            }
            
            rest = b - nd[i][1];
            if (a != b && mapDtoIndex.containsKey(rest)) {
                int index = mapDtoIndex.get(rest);
                g[i].add(index);
            }
        }
        
        long totalPairs = 0;
        boolean[] visited = new boolean[n];
        
        // dfs from places that have indegree/outdegree of 1
        for (int i = 0; i < n; i++) {
            if (!visited[i] && g[i].size() == 1) {
                totalPairs += dfs(i, visited, nd, -1);
            }
        }

		System.out.println(totalPairs);
	}

    private static long dfs(int node, boolean[] visited, long[][] nd, int prevD) {
        if (visited[node]) return 0;
        visited[node] = true;

        long addedCows = 0;
        if (prevD != -1) {
            addedCows = Math.min(nd[prevD][0], nd[node][0]);
            nd[node][0] -= addedCows;
            nd[prevD][0] -= addedCows;
        }

        boolean self = false;
        for (Integer to : g[node]) {
            if (to == node) {
                self = true;
            } else {
                addedCows += dfs(to, visited, nd, node);
            }
        }

        if (self) {
            // can add pair remaining cows within this id
            addedCows += (nd[node][0] >> 1);
        }

        return addedCows;
    }
}