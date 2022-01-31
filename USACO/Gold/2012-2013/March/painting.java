
import java.util.*;
import java.io.*;

public class painting {

	// http://usaco.org/index.php?page=viewproblem2&cpid=263
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("painting.in"));
		PrintWriter out = new PrintWriter(new FileWriter("painting.out"));

		int n = Integer.parseInt(in.readLine());
		
		Point[] arr = new Point[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			arr[i] = new Point(a, b, c,d);
		}
		
		Arrays.parallelSort(arr);
		
		HashSet<Point> curmin = new HashSet<>();
		ArrayList<Point> all = new ArrayList<>();
		curmin.add(arr[0]);
		all.add(arr[0]);
		int miny = arr[0].b;
		for (int i=1; i<n; i++) {
			if (arr[i].b < miny) {
				miny = arr[i].b;
				curmin.add(arr[i]);
				all.add(arr[i]);
				continue;
			}
			boolean contained=false;
			HashSet<Point> rem = new HashSet<>();
			for (Point a : curmin) {
				if (a.c < arr[i].a) {
					rem.add(a);
				}
				else {
					if (contains(a, arr[i])) {
						contained=true;
					}
				}
			}
			for (Point a : rem) curmin.remove(a);
			if (!contained) {
				curmin.add(arr[i]);
				all.add(arr[i]);
			}
		}
		
		System.out.println(all.size());
		out.println(all.size());
		out.close();
	}
	
	static class Point implements Comparable<Point> {
		int a; int b; int c; int d;
		Point (int a, int b, int c, int d) {
			this.a = a; this.b = b; this.c = c; this.d = d;
		}
		
		public int compareTo(Point o) {
			return a - o.a;
		}
	}
	
	public static boolean contains(Point a, Point b) {
		// a cont b
		if (a.a <= b.a && a.b <= b.b && a.c >= b.c && a.d >= b.d) return true;
		return false;
	}
}