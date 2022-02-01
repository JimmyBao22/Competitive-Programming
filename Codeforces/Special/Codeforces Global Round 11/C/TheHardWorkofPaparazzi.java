
import java.util.*;
import java.io.*;

public class TheHardWorkofPaparazzi {

	// https://codeforces.com/contest/1427/problem/C
	
	// fake solution?? (actual solution uses dp and is actually smart)
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("TheHardWorkofPaparazzi"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int r = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());

		int[][] arr = new int[n][3];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			arr[i][2] = Integer.parseInt(st.nextToken());
		}
		
		PriorityQueue<A> pq = new PriorityQueue<>();
		pq.add(new A(0,0,1,1));
		
		for (int i=0; i<n; i++) {
			ArrayList<A> removed = new ArrayList<>();
			while (!pq.isEmpty()) {
				A c = pq.poll();
				//c.print();
				if (Math.abs(arr[i][1] - c.x) + Math.abs(arr[i][2] - c.y) + c.time <= arr[i][0]) {
					pq.add(new A(c.val + 1, arr[i][0], arr[i][1], arr[i][2]));
					//pq.peek().print();
					removed.add(c);
					break;
				}
				removed.add(c);
			}
			for (int j=0; j<removed.size(); j++) {
				pq.add(removed.get(j));
			}
		}
		
		int max=pq.peek().val;
		
		System.out.println(max);
		
	}
	
	static class A implements Comparable<A> {
		int val; int time; int x; int y;
		A(int a, int b, int c, int d) {
			val = a; time = b; x = c; y = d;
		}
		public int compareTo(A o) {
			if (val == o.val) return time - o.time;
			return o.val - val;
		}
		void print() {
			System.out.println(val + " " + time + " " + x + " " + y);
		}
	}
}