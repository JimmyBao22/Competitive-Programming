
import java.util.*;
import java.io.*;

public class fuel {

	// http://usaco.org/index.php?page=viewproblem2&cpid=283
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("fuel.in"));
		PrintWriter out = new PrintWriter(new FileWriter("fuel.out"));
				
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int g = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());

		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
		}
				
		Arrays.parallelSort(arr);
		TreeMap<Integer, Integer> regions = new TreeMap<>();	// regions left
		regions.put(b, d);
				
		long cost=0;
		for (int i=0; i<n; i++) {
			//arr[i].print();
			Integer up = regions.ceilingKey(arr[i].x);
			if (up != null) {
				int to = regions.get(up);
				int to2 = Math.min(to, arr[i].x+g);
				// arr[i].x + g >= up
				// arr[i].x + g - up >= 0
				if (arr[i].x + g - up >= 0) {
					cost += (long)(to2 - Math.max(arr[i].x, up)) * arr[i].cost;
					regions.remove(up);
					if (to2 != to) {
						regions.put(to2, to);
					}
				}
			}
			
			Integer down = regions.floorKey(arr[i].x);
			if (down != null) {
				int to = regions.get(down);
				int to2 = Math.min(to, arr[i].x + g);
				// arr[i].x <= to2
				// to2 - arr[i].x >= 0
				if (to2 - arr[i].x >= 0) {
					cost += (long)(to2 - arr[i].x) * arr[i].cost;
					regions.remove(down);
					if (down != arr[i].x) {
						regions.put(down, arr[i].x);
					}
					if (to2 != to) {
						regions.put(to2, to);
					}
				}
			}
		}
		
		if (regions.size() > 0 ){		
			cost = -1;
		}
		System.out.println(cost);
		out.println(cost);
		out.close();

	}

	static class A implements Comparable<A> {
		int x;
		long cost;
		A (int a, long b) {
			x = a; cost = b;
		}
		public int compareTo(A o) {
			return Long.compare(cost, o.cost);
		}
		void print() {
			System.out.println(x + " " + cost);
		}
	}
}