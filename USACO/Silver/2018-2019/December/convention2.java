
import java.util.*;
import java.io.*;

public class convention2 {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=859
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("convention2.in"));

		int n = Integer.parseInt(in.readLine());  
		
		Cow[] cows = new Cow[n];
		for (int i = 0; i < n; i++) cows[i] = new Cow(i, in);
		
		in.close();

		Arrays.sort(cows, new Comparator<Cow>() {
				// general ordering of cows is based on arrival time
				public int compare(Cow a, Cow b) {
					return a.a - b.a;
				}
			});
		
		PriorityQueue<Cow> waiting = new PriorityQueue<>( 
			new Comparator<Cow> () {
				public int compare(Cow a, Cow b) {
					return a.s - b.s;
			}
		});
		
		int next = 0; // next cow that has not arrived at the field yet
		int tNext = 0; // time that waiting / arriving cows must wait before one can start eating
		
		int result = 0;
			// worst amnt of waiting time for any cow
		
		while (next < n || waiting.size() > 0) {
			// while there still are cows ...
			
			// if no cows are waiting, and field is available
				// time advances to next cow
			if (waiting.isEmpty() && cows[next].a > tNext) {
				tNext = cows[next].a;
			}
			
			// all cows arriving by next time get in line
			while (next < n && cows[next].a <= tNext) {
				waiting.add(cows[next]);
				next++;
			}
			
			// now guaranteed at least one cow is waiting
			// allow most senior cow to start eating
			// tNext right now is the starting time of the cow.
			Cow senior = waiting.poll();
			int wait = tNext - senior.a;
			result = Math.max(result, wait);
			tNext += senior.t;   // this gives the finished time of senior cow
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("convention2.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

	static class Cow {
		int s, a, t;
		// s --- seniority, lower is better
		// a --- arrival time
		// t --- eating duration
		
		Cow(int s, BufferedReader in) throws IOException {
			this.s = s;
			StringTokenizer st = new StringTokenizer(in.readLine());
			a = Integer.parseInt(st.nextToken());  
			t = Integer.parseInt(st.nextToken());  
		}
	}
	
	
}

/*
	use sorted array to get cows in line as they arrive
	use priority queue to get cows out of line in order they'd start eating
*/