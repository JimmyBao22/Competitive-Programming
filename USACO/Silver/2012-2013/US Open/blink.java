
import java.util.*;
import java.io.*;

public class blink {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=279
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("blink.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		long b = Long.parseLong(st.nextToken());
		
		int[] bulbs = new int[n];
		for (int i = 0; i < n; i++) {
			bulbs[i] = Integer.parseInt(in.readLine()); 
		}

		in.close();

		HashMap<String, Long> firstTimePerState = new HashMap<>();
		// key: String representation of the light bulbs
		// value: the first time this key was seen
		
		for (long i = 0; i < b; i++) {
			change(bulbs);
			String state = Arrays.toString(bulbs);
			if (firstTimePerState.containsKey(state)) {
				// we have a cycle
				long begin = firstTimePerState.get(state);
					// getting the first time this state happened
				
				// want to know length of cycle
				long cycleLen = i-begin;
				long remaining = b-i;
				
				long cyclesToSkip = remaining / cycleLen;
				i += cyclesToSkip*cycleLen;
				
				//firstTimePerState.clear(); // empties map 
				
			}
			else {
				firstTimePerState.put(state, i);
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("blink.out"));
		for (int i = 0; i < bulbs.length; i++) {
			System.out.println(bulbs[i]);
			out.println(bulbs[i]);
		}

		out.close();

	}

	static void change(int[] bulbs) {
		int oldRightbulb = bulbs[bulbs.length-1];
		for (int i = bulbs.length-1; i>0 ; i--) {
			if (bulbs[i-1] == 1) {
				bulbs[i] = (bulbs[i]+1) % 2;
			}
		}
		// change leftmost only based on value of rightmost
		if (oldRightbulb == 1) {
			bulbs[0] = (bulbs[0]+1) % 2;
		}
	}
}