
import java.util.*;
import java.io.*;

public class trapped {

	// http://usaco.org/index.php?page=viewproblem2&cpid=550
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("trapped.in"));
		PrintWriter out = new PrintWriter(new FileWriter("trapped.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(st.nextToken());
		Bale[] arr = new Bale[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new Bale(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		Arrays.parallelSort(arr);
		
		int left=-1;
		int right=-1;
		for (int i=0; i<n; i++) {
			if (arr[i].pos > start && left == -1) left = i-1;
			if (i!=n-1 && arr[i+1].pos >= start && arr[i].pos < start && right == -1) right = i+1;
		}
		if (right==-1) right=n-1;
		if (left == -1) left=0;
		
		long minans=Integer.MAX_VALUE;
		
		// left side invincible
		int rightpointer=right;
		for (int i=left; i>=0; i--) {
			while (rightpointer < n) {
				int curdist = arr[rightpointer].pos - arr[i].pos;
				if (arr[rightpointer].size < curdist) {
					rightpointer++;
				}
				else break;
			}
			if (rightpointer >= n) break;
			minans = Math.min(minans, Math.max(arr[rightpointer].pos - arr[i].pos - arr[i].size, 0));
		}
		
		// right side invincible
		int leftpointer = left;
		for (int i=right; i<n; i++) {
			while (leftpointer >= 0) {
				int curdist = arr[i].pos - arr[leftpointer].pos;
				if (arr[leftpointer].size < curdist) {
					leftpointer--;
				}
				else break;
			}
			if (leftpointer < 0) break;
			minans = Math.min(minans, Math.max(arr[i].pos - arr[leftpointer].pos - arr[i].size, 0));
		}
		
		if (minans == Integer.MAX_VALUE) {
			System.out.println(-1);
			out.println(-1);
		}
		else {
			System.out.println(minans);
			out.println(minans);
		}
		out.close();

	}
	
	static class Bale implements Comparable<Bale> {
		int size;
		int pos;
		Bale (int a, int b) {
			size = a; pos = b;
		}
		
		public int compareTo(Bale other) {
			return pos - other.pos;
		}		
	}
}