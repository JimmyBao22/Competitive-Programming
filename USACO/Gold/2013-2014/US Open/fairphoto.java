
import java.util.*;
import java.io.*;

public class fairphoto {
	
	// http://usaco.org/index.php?page=viewproblem2&cpid=433
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fairphoto.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fairphoto.out"));
		
		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			char c = st.nextToken().charAt(0);
			arr[i] = new A(a,c);
		}
		Arrays.parallelSort(arr);
		
		int[] psum = new int[n];
		psum[0] = arr[0].t == 'W' ? 1 : -1;
		for (int i=1; i<n; i++) {
			psum[i] = psum[i-1];
			if (arr[i].t == 'W') psum[i]++;
			else psum[i]--;
		}
		
		TreeMap<Integer, Integer> even = new TreeMap<>();
		TreeMap<Integer, Integer> odd = new TreeMap<>();
		for (int i=0; i<n; i++) {
			if (i%2==0) {
				even.put(psum[i], i);
			}
			else {
				odd.put(psum[i], i);
			}
		}
		
		int max = 0;
		if (even.size()>0) max = even.get(even.lastKey());
		for (Integer a : even.descendingKeySet()) {
			
			// you want a higher psum, because that means more W cows.
			
			// if, for a higher value has index value more to the right, that means it
				// encompasses more cows than the lower value, so you might as well
					// set it to the higher value, because you can always take that
					// higher value instead.
			
			max = Math.max(max, even.get(a)); 			
			even.put(a, max);
		}
		if (odd.size()>0) max = odd.get(odd.lastKey());
		for (Integer a : odd.descendingKeySet()) {
			max = Math.max(max, odd.get(a));
			odd.put(a, max);
		}
		
		int ans=0;
				// rememeber: need even number of cows in total
		
		for (int i=0; i<n; i++) {		// find max position starting from arr[i]
			if (i%2==0) {
				// search odd
				// psum[x] - psum[i-1] ≥ 0 			for some x
				// psum[x] ≥ psum[i-1]
				int needed = 0;
				if (i-1 >= 0) needed = psum[i-1];
				Integer key = odd.ceilingKey(needed);
				if (key == null) continue;
				int maxindex = odd.get(key);
				ans = Math.max(ans, arr[maxindex].pos - arr[i].pos);
			}
			else {
				// search even
				int needed = 0;
				if (i-1 >= 0) needed = psum[i-1];
				Integer key = even.ceilingKey(needed);
				if (key == null) continue;
				int maxindex = even.get(key);
				ans = Math.max(ans, arr[maxindex].pos - arr[i].pos);
			}
		}

		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	static class A implements Comparable<A> {
		int pos; char t;
		A (int a, char b) {
			pos = a; t = b;
		}
		public int compareTo(A o) {
			return pos-o.pos;
		}
	}
}