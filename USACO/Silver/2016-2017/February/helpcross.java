
import java.util.*;
import java.io.*;

public class helpcross {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=714
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("helpcross.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int c = Integer.parseInt(st.nextToken());  
		int n = Integer.parseInt(st.nextToken());  

		Event[] events = new Event[c+n];
		for (int j = 0; j < c; j++) {
			// chickens one time
			events[j] = new Event(Integer.parseInt(in.readLine()));
			
		}
		for (int j = c; j < n+c; j++) {
			StringTokenizer stt = new StringTokenizer(in.readLine());
			events[j] = new Event(Integer.parseInt(stt.nextToken()), Integer.parseInt(stt.nextToken()));
		}
		in.close();

		Arrays.sort(events);

		int result = 0;
		
		PriorityQueue<Integer> giveUpTimes = new PriorityQueue<>();
		// this is what time the cow is going to give up.
		
		for (Event e : events) {
			if (e.type == CHICKEN) {
				// get next 'cow' (the give up time) who is looking for a chicken
				Integer nextTime = giveUpTimes.poll();
				while (nextTime != null && nextTime < e.time1) {
					nextTime = giveUpTimes.poll();
				}
				if (nextTime != null) {
					result++; 	// one cow crosses
				}
				// else no cow crosses with this chicken
			}
			if (e.type == COW_WAIT) {
				// cow starts waiting, until time to give up
				giveUpTimes.add(e.time2);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("helpcross.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

	static final int COW_WAIT = 1;
	static final int CHICKEN = 2;
	
	static class Event implements Comparable<Event> {
		int type, time1, time2;
		
		Event(int t) {
			type = CHICKEN;
			time1 = t;
		}
		
		Event(int t1, int t2) {
			type = COW_WAIT;
			time1 = t1;
			time2 = t2;
		}
		
		public int compareTo(Event other) {
			int timeDiff = this.time1 - other.time1;
			
			if (timeDiff != 0) return timeDiff;
			
			// chickens sorted later if the same;
			return this.type - other.type;
		}
	}
}