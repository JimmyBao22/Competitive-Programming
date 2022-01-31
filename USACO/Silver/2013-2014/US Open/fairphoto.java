
import java.util.*;
import java.io.*;

public class fairphoto {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=431
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fairphoto.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fairphoto.out"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		for (int i=0; i<n; i++ ) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()), st.nextToken().charAt(0));
		}
		Arrays.parallelSort(arr);
		
		int[] psum = new int[n];
		psum[0] = (arr[0].type == 'G' ? 1 : -1);
		for (int i=1; i<n; i++) {
			psum[i] = (arr[i].type == 'G' ? 1 : -1) + psum[i-1];
		}
		
		int ans=0;
		TreeMap<Integer, Integer> map = new TreeMap<>();
			// psum, index
		for (int i=0; i<n; i++) {
			if (psum[i] == 0) {
				ans = Math.max(ans, arr[i].pos - arr[0].pos);
			}
			else if (!map.containsKey(psum[i])) {
				map.put(psum[i], i);
			}
			else {
				ans = Math.max(ans, arr[i].pos - arr[map.get(psum[i])+1].pos);
			}
		}
		
		// all G
		int last = n-1;
		for (int i=0; i<n; i++) {
			if (arr[i].type == 'G') {
				if (last == n-1) last = i;
				ans = Math.max(ans, arr[i].pos - arr[last].pos);
			}
			else last = n-1;
		}
		
		// all H
		last = n-1;
		for (int i=0; i<n; i++) {
			if (arr[i].type == 'H') {
				if (last == n-1) last = i;
				ans = Math.max(ans, arr[i].pos - arr[last].pos);
			}
			else last = n-1;
		}

		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	static class A implements Comparable<A> {
		int pos; char type;
		A (int a, char b) {
			pos = a; type = b;
		}
		public int compareTo(A o) {
			return pos - o.pos;
		}
	}
}