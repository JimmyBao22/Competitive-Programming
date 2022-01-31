
import java.util.*;
import java.io.*;

public class bteams {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=378
	
	static int best;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("bteams.in"));
		PrintWriter out = new PrintWriter(new FileWriter("bteams.out"));

		int[] arr = new int[12];
		for (int i=0; i<12; i++) arr[i] = Integer.parseInt(in.readLine());
		best = Integer.MAX_VALUE;
		
		ArrayList<Integer>[] include = new ArrayList[4];
		for (int i=0; i<4; i++) include[i] = new ArrayList<>();
		boolean[] used = new boolean[12];
		
		// brute force all combos (12C3 * 9C3 * 6C3)
		combination_size(arr, include, used, 0, 0, 3);

		System.out.println(best);
		out.println(best);
		out.close();

	}

	public static void combination_size(int[] arr, ArrayList<Integer>[] include, boolean[] used, int i, int j, int length_needed) {
		if (include[j].size() == length_needed) {
			if (j == 3) {
				int[] sum = new int[4];
				for (int k=0; k<4; k++) {
					for (int g=0; g<3; g++) {
						sum[k] += include[k].get(g);
					}
				}
				Arrays.parallelSort(sum);
				best = Math.min(best, sum[3] - sum[0]);
			}
			else {
				combination_size(arr, include, used, 0, j+1, length_needed);
			}
			return;
		}
		if (i >= arr.length || arr.length - i + include[j].size() < length_needed) return;
		
		combination_size(arr, include, used, i+1, j, length_needed);		// don't include
		
		if (!used[i]) {
			used[i] = true;
			include[j].add(arr[i]); 	// include
			combination_size(arr, include, used, i+1, j, length_needed);
			include[j].remove(include[j].size()-1);		// undo
			used[i] = false;
		}
	}
}