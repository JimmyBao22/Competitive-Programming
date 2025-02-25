import java.util.*;

public class DivideNodesIntotheMaximumNumberofGroups {
    
    // https://leetcode.com/problems/divide-nodes-into-the-maximum-number-of-groups/

    ArrayList<Integer>[] g;
    DSU dsu;

    @SuppressWarnings("unchecked")
    public int magnificentSets(int n, int[][] edges) {
        g = new ArrayList[n];
        for (int i = 0; i < n ; i++) {
            g[i] = new ArrayList<>();   
        }

        dsu = new DSU(n);

        for (int i = 0; i < edges.length; i++) {
            g[edges[i][0] - 1].add(edges[i][1] - 1);
            g[edges[i][1] - 1].add(edges[i][0] - 1);
            dsu.Union(edges[i][0] - 1, edges[i][1] - 1);
        }

        Map<Integer, Integer> maxVals = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int j = dsu.FindSet(i);
            if (!maxVals.containsKey(j)) maxVals.put(j, -1);
            maxVals.put(j, Math.max(maxVals.get(j), bfs(n, i)));
        }

        // might have disjoint components --> need to store in each component the max that can be achieved. Add all these up at end 

        int ans = 0;
        for (Integer x : maxVals.keySet()) {
            if (maxVals.get(x) == -1) return -1;
            ans += maxVals.get(x);
        }

        return ans;
    }

    public int bfs(int n, int start) {
        ArrayDeque<int[]> d = new ArrayDeque<>();
                        // node, curval, value of the parent node
        d.add(new int[]{start, 1, 0});
        int[] vals = new int[n];
        Arrays.fill(vals, -1);
        int numGroups = 0;
        while (!d.isEmpty()) {
            int[] cur = d.poll();
            if (vals[cur[0]] != -1) {
                if (Math.abs(vals[cur[0]] - cur[2]) != 1) return -1;
                else continue;
            }
            vals[cur[0]] = cur[1];
            numGroups = Math.max(numGroups, cur[1]);
            for (Integer to : g[cur[0]]) {
                // group everything next to this node as the current value + 1 (if not visited yet)
                d.add(new int[]{to, cur[1] + 1, cur[1]});
            }
        }

        return numGroups;
    }

    class DSU {
		int n;
		int[] parent;
		int[] size;
		
		DSU (int n) {
			this.n = n;
			parent = new int[n];
			size = new int[n];
			for (int i=0; i<n; i++) {parent[i] = i; size[i] = 1;}
		}

		public int FindSet(int a) {
			if (a == parent[a]) return a;
			return parent[a] = FindSet(parent[a]);
		}
		
		public void Union(int a, int b) {
			a = FindSet(a);
			b = FindSet(b);
			if (a == b) return;
			
			if (size[a] < size[b]) {
				parent[a] = b;
				size[b] += size[a];
			}
			else {
				parent[b] = a;
				size[a] += size[b];
			}
		}
	}
}
