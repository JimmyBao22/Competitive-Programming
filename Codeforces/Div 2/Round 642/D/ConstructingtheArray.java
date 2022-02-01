
import java.util.*;
import java.io.*;

public class ConstructingtheArray {

	// https://codeforces.com/problemset/problem/1353/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ConstructingtheArray"));

		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			int n = Integer.parseInt(in.readLine());
			PriorityQueue<int[]> a = new PriorityQueue<>(
		    		new Comparator<int[]>() {
		    			public int compare(int[] x, int[] y) {
		    				if (y[0] != x[0]) return y[0]-x[0]; // sort by largest first
		    				else return x[1] - y[1]; // if same large, sort by leftmost
		    			}
		    });
			
			int[] ans = new int[n];
			a.add(new int[] {n, 0, n-1});
				// length, l, r
			int curnum=1;
			while (a.size() > 0) {
				int[] cur = a.poll();
				if (cur[0] <= 0 || cur[1] < 0 || cur[2] < 0) continue;
				ans[(cur[1] + cur[2])/2] = curnum;
				curnum++;
				if (cur[1] == cur[2]) continue;
				a.add(new int[] {(cur[1] + cur[2])/2 - cur[1], cur[1], (cur[1] + cur[2])/2 -1});
				a.add(new int[] {cur[2] - (cur[2] + cur[1])/2, (cur[2] + cur[1])/2 + 1, cur[2]});
			}
			for (int i=0; i<n; i++) {
				System.out.print(ans[i] + " ");
			}
			System.out.println();
		}
	}
}