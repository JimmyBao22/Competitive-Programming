
import java.util.*;
import java.io.*;

public class skilevel {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=384
	
	static int n,m,t;
	static int[][] arr, needed;
	static int special = 1000;
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("skilevel.in"));
		PrintWriter out = new PrintWriter(new FileWriter("skilevel.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		arr = new int[n][m];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<m; j++ ) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		needed = new int[n][m];
		int pointer=0;
		int[][] temp = new int[(n-1)*m + (m-1)*n][5];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<m; j++ ) {
				needed[i][j] = Integer.parseInt(st.nextToken());
				if (i!=n-1) {
					int[] cur = new int[5];
					cur[0] = Math.abs(arr[i][j] - arr[i+1][j]);
					cur[1] = i;
					cur[2] = j;
					cur[3] = i+1;
					cur[4] = j;
					temp[pointer] = cur;
					pointer++;
				}
				if (j!=m-1) {
					int[] cur = new int[5];
					cur[0] = Math.abs(arr[i][j] - arr[i][j+1]);
					cur[1] = i;
					cur[2] = j;
					cur[3] = i;
					cur[4] = j+1;
					temp[pointer] = cur;
					pointer++;
				}
			}
		}
		
		DSU s = new DSU(n,m);

		Arrays.parallelSort(temp, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		
		long ans=0;
		
		for (int i=0; i<temp.length; i++) {
			int[] cur = temp[i];
			long curlength = cur[0];
			int x1=cur[1]; int y1 = cur[2]; int x2 = cur[3]; int y2 = cur[4];
			//System.out.println(x1 + " " + y1 + " " + x2 + " " + y2 + ": " + curlength);
			int ret = s.Union(x1,y1,x2,y2);
			if (ret != -1) {		
				if (s.size[ret/special][ret%special]>=t) {
					ans += curlength * s.nodes_inside[ret/special][ret%special];
					s.nodes_inside[ret/special][ret%special] = 0;
				}
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static class DSU {
		int n, m;
		int[][] parent;
		int[][] size;
		long[][] nodes_inside;
		
		DSU (int n, int m) {
			this.n = n;
			this.m = m;
			parent = new int[n][m];
			size = new int[n][m];
			nodes_inside = new long[n][m];
			for (int i=0; i<n; i++) {
				for (int j=0; j<m; j++) {
					parent[i][j] = i*special+j;
					size[i][j] = 1;
					if (needed[i][j] == 1) nodes_inside[i][j] = 1;
				}
			}
		}
		
		public int FindSet(int x, int y) {
			if (x*special+y == parent[x][y]) return x*special+y;
			// parent[a] = FindSet(parent[a]);
			return parent[x][y] = FindSet(parent[x][y]/special, parent[x][y]%special);
		}
		
		public int Union(int x1, int y1, int x2, int y2) {
			int first = FindSet(x1, y1);
			int second = FindSet(x2, y2);
			if (first == second) { 	// cycle found
				return -1;
			}
			
			x1 = first/special;
			y1 = first%special;
			x2 = second/special;
			y2 = second%special;
			
			if (size[x1][y1] < size[x2][y2]) {
				parent[x1][y1] = x2*special+y2;
				size[x2][y2] += size[x1][y1];
				nodes_inside[x2][y2] += nodes_inside[x1][y1];
				return second;
			}
			else {
				parent[x2][y2] = x1*special+y1;
				size[x1][y1] += size[x2][y2];
				nodes_inside[x1][y1] += nodes_inside[x2][y2];
				return first;
			}
		}
	}
}


/*
	ski course rating
	store all as nodes and assign edge weights
	loop in order of edge weights and keep combining edges together
	combine edges repeatedly with dsu and find size(parent(things))
		the only size that changes is the one that just got combined.
		Every tree keep track of number of ones in that tree. can add
			in end do max edge weight * number of ones
				then number of ones = 0 so no overcount
*/