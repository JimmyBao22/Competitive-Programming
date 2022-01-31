
import java.util.*;
import java.io.*;

public class reststops {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=810
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("reststops.in"));
		PrintWriter out = new PrintWriter(new FileWriter("reststops.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int l = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		long rf = Integer.parseInt(st.nextToken());
		long rb = Integer.parseInt(st.nextToken());

		PriorityQueue<Grass> p = new PriorityQueue<>();
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			long a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());
			Grass cur = new Grass(a,b);
			p.add(cur);
		}
		
		long bespos=0;
		long lastrest=0;
		long ans=0;
		
		while (bespos<l && !p.isEmpty()) {
			Grass cur = p.poll();
			if (cur.pos < bespos) {
				continue;
			}
			long distance = (cur.pos - lastrest);
			ans += (rf*distance - rb*distance) * cur.taste;
			bespos = cur.pos;
			lastrest = cur.pos;
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}

	static class Grass implements Comparable<Grass> {
		long pos;
		long taste;
		Grass (long a, long b) {
			pos = a;
			taste = b;
		}
		
		public int compareTo (Grass other) {
			return Long.compare(other.taste, taste);
		}			// sort decreasing order
	}
}