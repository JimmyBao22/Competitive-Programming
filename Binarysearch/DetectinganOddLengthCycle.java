
import java.util.*;
import java.io.*;

public class DetectinganOddLengthCycle {

	public static void main(String[] args) {

	}

	// https://binarysearch.com/problems/Detecting-an-Odd-Length-Cycle
	
	int[] visited;
    ArrayList<Integer>[] g;
    boolean works;

    public boolean solve(int[][] graph) {
        int n = graph.length;
        int m = graph[0].length;
        g = new ArrayList[n];
        visited =new int[n];
        Arrays.fill(visited, -1);
        works=false;
        for (int i=0; i<n; i++) g[i] = new ArrayList<>();
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                g[i].add(graph[i][j]);
            }
        }

        for (int i=0; i<n; i++) {
            if (visited[i] == -1) dfscheck(i, 0);
        }

        return works;

    }

	public void dfscheck(int node, int dist) { 
        if (works) return;
		if (visited[node] != -1) {
            if ((dist - visited[node])%2 == 1) works = true;
            return;
        }
		visited[node] = dist;
		for (Integer i : g[node]) {
			dfscheck(i, dist+1);
            if (works) return;
		}
	}
}