
import java.util.*;
import java.io.*;

public class slowdown {

	// http://usaco.org/index.php?page=viewproblem2&cpid=379
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("slowdown.in"));
		PrintWriter out = new PrintWriter(new FileWriter("slowdown.out"));

		int n = Integer.parseInt(in.readLine());

		PriorityQueue<Integer> time = new PriorityQueue<>();
		PriorityQueue<Integer> dist = new PriorityQueue<>();

		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			char d = st.nextToken().charAt(0);
			int t = Integer.parseInt(st.nextToken());
			if (d == 'T') time.add(t);
			else dist.add(t);
		}
		
		double curspeed = 1;
		double totaltime = 0;
		double curpos=0;
		
		while (!time.isEmpty() || !dist.isEmpty()) {
			if (time.isEmpty()) {
				int curdist = dist.poll();
				double rem = curdist - curpos;
				totaltime += rem * curspeed;
				curspeed++;
				curpos = curdist;
			}
			else if (dist.isEmpty()) {
				int curtime = time.poll();
				double rem = curtime - totaltime;
				curpos += rem * (1.0/curspeed);
				totaltime = curtime;
				curspeed++;
			}
			else {
				int curtime = time.peek();
				double rem1 = curtime - totaltime;
				double curpos1 = rem1 * (1.0/curspeed) + curpos;
				int curdist = dist.peek();
				
				if (curpos1 > curdist) {
					curdist = dist.poll();
					double rem = curdist - curpos;
					totaltime += rem * curspeed;
					curpos = curdist;
				}
				else {
					curtime = time.poll();
					double rem = curtime - totaltime;
					curpos += rem * (1.0/curspeed);
					totaltime = curtime;
				}
				curspeed++;
			}
		}
		
		double rem = 1000 - curpos;
		totaltime += rem * curspeed;
		long f = Math.round(totaltime);
		System.out.println(f);
		out.println(f);
		out.close();
	}
}