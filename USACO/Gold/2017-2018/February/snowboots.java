
import java.util.*;
import java.io.*;

public class snowboots {

	// http://usaco.org/index.php?page=viewproblem2&cpid=813
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("snowboots.in"));
		PrintWriter out = new PrintWriter(new FileWriter("snowboots.out"));
	
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int[] f = new int[n];
		A[] fsort = new A[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			f[i] = Integer.parseInt(st.nextToken());
			fsort[i] = new A(f[i], i);
		}
		Arrays.parallelSort(fsort);
		
		Boot[] boots = new Boot[b];
		for (int i=0; i<b; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			boots[i] = new Boot(one,two,i);
		}
		Arrays.parallelSort(boots);
		
		int[] next = new int[n];
		int[] prev = new int[n];
		int maxgap=1;
		for (int i=0; i<n; i++) {
			next[i] = i+1;
			prev[i] = i-1;
		}

		int[] ans = new int[b];
		int fpointer=0;
		for (int i=0; i<b; i++) {
			while (fpointer<n && fsort[fpointer].val > boots[i].s) {
				int index = fsort[fpointer].i;
				next[prev[index]] = next[index];
				prev[next[index]] = prev[index];
				maxgap = Math.max(maxgap, next[index] - prev[index]);
				fpointer++;
			}
			if (boots[i].d >= maxgap) {
				ans[boots[i].i] = 1;
			}
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<b; i++) {
			if (i!=b-1) s.append(ans[i] + "\n");
			else s.append(ans[i]);
		}
		System.out.println(s);
		out.println(s);
		out.close();

	}
	
	static class Boot implements Comparable<Boot> {
		int s;
		int d;
		int i;
		Boot (int a, int b, int c ){
			s = a; d = b; i = c;
		}
		public int compareTo( Boot o) {
			return o.s - s;
		}
	}
	
	static class A implements Comparable<A> {
		int val; int i;
		A (int a, int b) {
			val = a; i = b;
		}
		public int compareTo(A o) {
			return o.val - val;
		}
	}
}

/*

	sort the queries in a nice way. sort s_i in decreasing order (largest s_i first).
	Store closest index you can walk to (next array). As you move through boots, 
		delete spaces with less s_i than cur.
	store the max(gap) rn, if boot >= gap, then good
		max(gap) = max(next - index)
	
	How to find which cells we want to delete — create second array which, in decreasing 
		order, stores (value, index)
		keep track of which a pointer of this
	How to delete — we have a next array and also a previous array. previous array will 
		let us know what we can find previously to point to next element
	
	next[previous[i]] = next[i]
	previous[next[i]] = previous[i]
	
	next[i] = next good element that has not been deleted
	prev[i] = closest previous good element that has not been deleted

*/