
import java.util.*;
import java.io.*;

public class paint {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=224
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("paint.in"));
		int n = Integer.parseInt(in.readLine());  
		
		// use a tree map!
		// this way it will be sorted
		
		TreeMap<Integer, Integer> relCoats = new TreeMap<>();
		// key: location on the fence
		// values: relative change in coats of paint
		
		int x = 0;
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int steps = Integer.parseInt(st.nextToken());  
			String direction = st.nextToken();
			if (direction.charAt(0) == 'L') steps *= -1;
		
			// moving from x, moving to x+steps
			int a = Math.min(x, x+steps);
			int b = Math.max(x, x+steps);
			x = x+steps;
			// movement covers from a to b or b to a
			
			// a should get +1 to its # of coats, b gets -1
			
			// see if it is already stored; if already stored, you have to modify it
				// this is why you have to get() something from the treemap
			Integer aVal = relCoats.get(a);
			if (aVal == null) aVal = 0;
			relCoats.put(a, aVal+1);
			
			Integer bVal = relCoats.get(b);
			if (bVal == null) bVal = 0;
			relCoats.put(b, bVal-1);
		
		}
		
		in.close();

		int result = 0;
		int coats = 0;
		x = -1000000000; // furthest to the left Bessie goes
		// x will be tracking 'where we are coming from'
		
		for (int newX : relCoats.keySet()) {
			// gets keys in the map (here it is locations on the fence)
			// keys r sorted cuz its a treemap
			
			int dist = newX - x;
			
			if (coats >= 2) result += dist;
			
			x = newX;
			coats += relCoats.get(x);
		}

		PrintWriter out = new PrintWriter(new FileWriter("paint.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}
}

/*
	ANALYSIS
	
	if we store endpts of intervals, we can subtract upper end from lower end ot find how long the interval is
	instead of committing to an absolute, final # of coats for each interval,
	store a relative change in the # of coats at each endpt
	
	only store info that is dependent on movement into/away from a point, not other info
		O(n) storage

*/

/*

	ANALYSIS on bottom part
	
	example - 
	
		2 R
		6 L
		1 R
		8 L
		1 R
		2 R
		
	relCoats = {-11=2, -10=0, -8=-1, -4=2, -3=-2, 0=1, 2=-2};
	
		
	for (int newX : relCoats.keySet()) {
			// gets keys in the map (here it is locations on the fence)
			// keys r sorted cuz its a treemap
			
			int dist = newX - x;
			
			if (coats >= 2) result += dist;
			
			x = newX;
			coats += relCoats.get(x);
		}
		
	coats = 0; x = -11; coats += 2;
	coats = 2, so result += 1 (dist from -11 to -10); x = -10; coats += 0;
	coats = 2, so result += 2; x = -8; coats += -1;
	coats = 1; x = -4; coats += 2;
	coats = 3, so result += 1; x = -3; coats += -2;
	coats = 1; x = 0; coats += 1;
	coats = 2, so result += 2; x = 2; coats += -2;
	coats = 0 for rest
	
	Start = -11 has 2 coats
		then go to next checkpt, which is -10
		from -10 to -8, there is a difference of 0 coats from the two
			therefore -10=0
	then from -8 to -4, there is a change of -1 (only 1 coat each here)
		therefore -8=-1
	then from -4 to -3, there is a change of 2 (3 coats each here)
		therefore -4=2
	...	

*/