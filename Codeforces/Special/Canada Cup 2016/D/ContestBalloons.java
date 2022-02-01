
import java.util.*;
import java.io.*;

public class ContestBalloons {

	// https://codeforces.com/contest/725/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ContestBalloons"));

		int n = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine());
		A main = new A(Long.parseLong(st.nextToken()),Long.parseLong(st.nextToken()));
		A[] arr = new A[n];
		arr[0] = main;
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(in.readLine());
			long one = Long.parseLong(st.nextToken());
			long two = Long.parseLong(st.nextToken());
			arr[i+1] = new A(one, two);
		}
		
		// largest t to smallest
		Arrays.parallelSort(arr);
		
		PriorityQueue<A> p = new PriorityQueue<>(
	    		new Comparator<A>() {
	    			public int compare(A x, A y) {
	    				return Long.compare(x.w-x.t+1, y.w - y.t+1);
	    			}
	    });

		int index=0;
		for (int i=0; i<arr.length; i++) {
			if (arr[i].equals(main)) {
				index = i;
				break;
			}
			p.add(arr[i]);
		}
		
		int ans = p.size()+1;
		while (index<arr.length && p.size()>0) {
			A cur = p.poll();
			long needed = cur.w - cur.t+1;
			if (needed > main.t) break;
			main.t -= needed;
			while (index+1 < n && arr[index+1].t > main.t) {
				p.add(arr[index+1]);
				index++;
			}
			ans = Math.min(ans, p.size()+1);
		}
		System.out.println(ans);
	}
	
	static class A implements Comparable<A> {
		long w;
		long t;
		A(long a, long b) {
			t = a; w = b;
		}
		public int compareTo(A o) {
			return Long.compare(o.t, t);
		}
	}
}