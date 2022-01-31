
import java.util.*;
import java.io.*;

public class wormhole {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=360

	static int n;
	static Wormhole[] wormholes;
		
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("wormhole.in"));

		n = Integer.parseInt(in.readLine());  
		
		wormholes = new Wormhole[n];
		for (int i = 0; i < n; i++) wormholes[i] = new Wormhole(in);
		in.close();

		for (Wormhole w : wormholes) w.findRight();
		
		int result = tryPairings(0);
			// try pairing of wormholes starting iwth wormhole 0
		
		PrintWriter out = new PrintWriter(new FileWriter("wormhole.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

	// tries pairing up a with all other available WHs
		// and al combos following after that pairing, and
		// returns # of loopy combos that result
	static int tryPairings(int a) {
		// a skip past any WHs already paired
		while (a < n && wormholes[a].paired != -1) a++;
		
		// a is at end of the array
		// all wormholes are paired
		// see if it is loopy or not
		if (a >= n) {
			for (int i = 0; i < n; i++) {
				if (simulate(i)) return 1;
				// it is loopy; count it
			}
			// don't count bc it isnt loopy
			return 0;
		}
		
		int count = 0;
		for (int b = a+1; b < n; b++) {
			if (wormholes[b].paired != -1) continue;
				// skip paired wormholes
			
			wormholes[a].paired = b;
			wormholes[b].paired = a;
			
			
			count += tryPairings(a+1);
			
				// undo pairing to pair with something else later
			wormholes[a].paired = -1;
			wormholes[b].paired = -1;
		}
		return count;
	}
	
	// assume all WHs already paired
	// return whether cow gets stuck in a loop starting from this point
	static boolean simulate(int start) {
		int curr = wormholes[start].paired;
		curr = wormholes[curr].toRight;
		
		
		while (curr != -1) { // -1 is no more wormholes to the right
			if (curr == start) return true;
			// walked back to the start one
			
			curr = wormholes[curr].paired;
			curr = wormholes[curr].toRight;
		}
		
		return false;	// hit -1 so not loopy
		
	}
	
	static class Wormhole {
		int x, y;
		int paired = -1;
			// this is index in 'wormholes' array where paired wormhole is
				// -1 if unpaired
		
		int toRight = -1;
			// index in 'wormholes' array where next right wormhole is
				// same y
				// -1 if no wormhole to the right
				
		Wormhole(BufferedReader in) throws IOException {
			StringTokenizer st = new StringTokenizer(in.readLine());
			x = Integer.parseInt(st.nextToken());  
			y = Integer.parseInt(st.nextToken());  
		}
		
		void findRight() {
			for (int i = 0; i < n; i++) {
				Wormhole w = wormholes[i];
				if (w.y != this.y || w.x <= this.x) continue;
				// skip wormholes that are not directly to the right
				
				// find the best (closest to the right with same y) so far
				// use wormhole i if it is first wormhole to the right or if its distance
					// to the right is closer to this wormhole than the other one
				if (this.toRight == -1 || w.x < wormholes[toRight].x) {
					this.toRight = i;
				}
				// this will find the closest to the right for a wormhole
			}	
		}	
	}
}

/*
	n wormholes
	(n-1)*(n-3)*(n-5)*...1 combinations
	
	generate combination in pattern above
	as long as pairing still needs tobe made
		pick first available wormhole as part one of the next pair
	try all possible other wormholes paired up with that one
		with each pairing made, try linking up remaining wormholes recursively

	if no more wormhole pairs to be made, check combo for loopiness
*/