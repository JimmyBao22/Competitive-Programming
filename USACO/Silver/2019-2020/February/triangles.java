
import java.util.*;
import java.io.*;

public class triangles {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1015
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("triangles.in"));
		PrintWriter out = new PrintWriter(new FileWriter("triangles.out"));

		int n = Integer.parseInt(in.readLine());
		Point[] arrx = new Point[n];
		Point[] arry = new Point[n];
		HashMap<Long, ArrayList<Point>> x = new HashMap<>();
		HashMap<Long, ArrayList<Point>> y = new HashMap<>();
			// value, indices
		
		HashMap<Integer, Long> valx = new HashMap<>();
		HashMap<Integer, Long> valy = new HashMap<>();
		
		long mod = (long)(1e9+7);
		
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			arrx[i] = arry[i] = new Point(a,b,i);
		}
		
		Arrays.parallelSort(arrx, new Comparator<Point>() {
			public int compare(Point a, Point b) {
				return Long.compare(a.x, b.x);
			}
		});
		Arrays.parallelSort(arry, new Comparator<Point>() {
			public int compare(Point a, Point b) {
				return Long.compare(a.y, b.y);
			}
		});
		
		for (int i=0; i<n; i++) {
			if (!x.containsKey(arrx[i].x)) x.put(arrx[i].x, new ArrayList<>());
			if (!y.containsKey(arry[i].y)) y.put(arry[i].y, new ArrayList<>());
			
			x.get(arrx[i].x).add(arrx[i]);
			y.get(arry[i].y).add(arry[i]);
		}
		
		for (Long val : y.keySet()) {
			long dist = 0;
			ArrayList<Point> cur = y.get(val);
			for (int i=0; i<cur.size(); i++) {
				dist += cur.get(i).x - cur.get(0).x;
				dist %= mod;
			}
			valx.put(cur.get(0).index, dist);
			
			int right = cur.size()-1;
			int left = 1;
			for (int i=1; i<cur.size(); i++) {
				// move to cur.get(i);
				
				long curdist = cur.get(i).x - cur.get(i-1).x;
				dist -= right*curdist;
				dist %= mod;
				dist += left * curdist;
				dist %= mod;
				if (dist < 0) dist += mod;
				
				valx.put(cur.get(i).index, dist);
				
				right--;
				left++;
			}
		}
		
		for (Long val : x.keySet()) {
			long dist = 0;
			ArrayList<Point> cur = x.get(val);
			for (int i=0; i<cur.size(); i++) {
				dist += cur.get(i).y - cur.get(0).y;
				dist %= mod;
			}
			valy.put(cur.get(0).index, dist);
			
			int right = cur.size()-1;
			int left = 1;
			for (int i=1; i<cur.size(); i++) {
				// move to cur.get(i);
				
				long curdist = cur.get(i).y - cur.get(i-1).y;
				dist -= right*curdist;
				dist %= mod;
				dist += left * curdist;
				dist %= mod;
				if (dist < 0) dist += mod;
				
				valy.put(cur.get(i).index, dist);
				
				right--;
				left++;
			}
		}
		
		long ans=0;
		for (int i=0; i<n; i++) {
			ans += valx.get(i) * valy.get(i);
			ans %= mod;
		}
		ans = (ans%mod+mod)%mod;
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static class Point {
		long x, y; int index;
		Point (int a, int b, int c) {
			x=a; y=b; index=c;
		}
	}
}