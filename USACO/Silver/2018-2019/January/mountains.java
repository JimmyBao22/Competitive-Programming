
import java.util.*;
import java.io.*;

public class mountains {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=896
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mountains.in"));
		PrintWriter out = new PrintWriter(new FileWriter("mountains.out"));

		int n = Integer.parseInt(in.readLine());
		Point[] arr = new Point[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		Arrays.parallelSort(arr);
		
		int count=0;
		for (int i=0; i<n; i++) {
			// check if arr[i] works
			boolean works=true;
			for (int j=0; j<i; j++) {
				// check with all points before it
				if (arr[i].x > arr[j].x) {
					// slope -1, go to right
					long b = arr[j].y + arr[j].x;
					long y = b - arr[i].x;
					if (y >= arr[i].y) {
						works=false;
						break;
					}
				}
				else {
					// slope 1, go to left
					long b = arr[j].y - arr[j].x;
					long y = b + arr[i].x;
					if (y >= arr[i].y) {
						works=false;
						break;
					}
				}
			}
			if (works) count++;
		}
		
		System.out.println(count);
		out.println(count);
		out.close();

	}
	
	static class Point implements Comparable<Point> {
		long x;
		long y;
		Point(long a, long b) {
			x = a; y = b;
		}
		public int compareTo(Point other) {
			return Long.compare(other.y, y);
		}		// sort in decreasing order
		
		public void print() {
			System.out.println(x + " " + y);
		}
	}
}