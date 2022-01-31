
import java.util.*;
import java.io.*;

public class lightsout {

	// http://usaco.org/index.php?page=viewproblem2&cpid=599
	
	static int n;
	static A[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lightsout.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lightsout.out"));

		n = Integer.parseInt(in.readLine());
		arr = new A[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			arr[i] = new A(i, one, two);
		}
		Edge[] edges = new Edge[n];
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
			// angle = indices of points
		
		for (int i=0; i<n-1; i++) {
			edges[i] = new Edge(i, i+1);
		}
		edges[n-1] = new Edge(n-1, 0);
		
		int[] normal_dist = new int[n];		// when lights are on, min dist for [i]
		int rightdist = 0;
		for (int i=0; i<n; i++) rightdist += edges[i].dist;
		int leftdist = 0;
		normal_dist[0] = 0;
		for (int i=0; i<n-1; i++) {
			leftdist += edges[i].dist;
			rightdist -= edges[i].dist;
			normal_dist[i+1] = Math.min(leftdist, rightdist);
		}
		
		for (int i=0; i<n; i++) {
			// i, i+1, i+2
			int curangle = angle(arr[i], arr[(i+1)%n], arr[(i+2)%n]);
			arr[(i+1)%n].angle = curangle;
			if (!map.containsKey(curangle) ) {
				map.put(curangle, new ArrayList<>());
			}
			map.get(curangle).add((i+1)%n);
		}
		
		int maxdiff = 0;
		for (int i=1; i<n; i++) {
			// currently at this point
			int curdist = 0;
			HashSet<Integer> possible = new HashSet<>();
			for (Integer a : map.get(arr[i].angle)) possible.add(a);
			int pointer=0;
			while (possible.size() > 1 && i+pointer!=n) {
				// go to the right
				pointer++;
				int dist_just_traveled = edges[i+pointer-1].dist;
				curdist += dist_just_traveled;
				
				ArrayList<Integer> remove = new ArrayList<>();
				for (Integer a : possible) {
					if (a + pointer >= n) {		// would have exited by now
						remove.add(a);
					}
					else {
						int dist_a_traveled = edges[a + pointer - 1].dist;
						if (dist_a_traveled != dist_just_traveled) {
							remove.add(a);		// not same dist just traveled
						}
						if (i+pointer<n && arr[a+pointer].angle != arr[i+pointer].angle) {
							remove.add(a);		// not same angle just traveled
						}
					}
				}
				for (Integer a : remove) possible.remove(a);
			}
			// i know where I am, which is i+pointer
			curdist += normal_dist[(i+pointer)%n];
			maxdiff = Math.max(maxdiff, Math.abs(curdist - normal_dist[i]));
		}
		
		System.out.println(maxdiff);
		out.println(maxdiff);
		out.close();
	}
	
	public static int angle(A a, A b, A c) {
		// for point b
		if (a.x == b.x) {
			if (a.y < b.y) {
				// going up
				if (c.x < b.x) {
					// going up then going left
					return 270;
				}
				else {
					return 90;
				}
			}
			else {
				// going down
				if (c.x < b.x) {
					// going down then going left
					return 90;
				}
				else return 270;
			}
		}
		else {
			if (a.x < b.x) {
				// going right
				if (c.y > b.y ) {
					// going right then going up
					return 270;
				}
				else return 90;
			}
			else {
				// going left
				if (c.y > b.y) {
					// going left then going up
					return 90;
				}
				else return 270;
			}
		}
	}
	
	static class Edge {
		int from; int to;
		int dist;
		Edge (int a, int b) {
			from = a; to = b;
			if (arr[from].x == arr[to].x) {
				dist = Math.abs(arr[from].y - arr[to].y);
			}
			else {
				dist = Math.abs(arr[from].x - arr[to].x);
			}
		}
	}
	
	static class A {
		int angle;
		int index;
		int x,y;
		A (int a, int b, int c) {
			index = a; x = b; y = c;
		}
	}
}