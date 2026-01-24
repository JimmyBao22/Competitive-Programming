
import java.util.*;
import java.io.*;

public class Cannonball {
	
	// https://usaco.org/index.php?page=viewproblem2&cpid=1372

	public static void main(String[] args) throws IOException, FileNotFoundException {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int s = in.nextInt() - 1;
		int power = 1;
		int dir = 1;
		
		int[][] locations = new int[n][2];
		for (int i = 0 ; i < n; i++) {
			locations[i][0] = in.nextInt();
			locations[i][1] = in.nextInt();
		}
		
		// simulate
		// check for infinite cycles (occurs when jump pad v = 0)
		Set<String> visited = new HashSet<>();
		
		boolean[] broken = new boolean[n];
		int targetsBroken = 0;
		while (s >= 0 && s < n) {
			String state = s + " " + power + " " + dir;
			if (visited.contains(state)) break;
			visited.add(state);
					
			if (locations[s][0] == 0) {
				// jump pad
				power += locations[s][1];
				dir *= -1;
			} else {
				// target
				if (!broken[s] && power >= locations[s][1]) {
					// break this target
					targetsBroken++;
					broken[s] = true;
				}
			}
			
			// keep moving forward
			s += power * dir;
		}

		System.out.println(targetsBroken);
	}
}