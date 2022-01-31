
import java.util.*;
import java.io.*;

public class lifeguards {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=786
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lifeguards.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lifeguards.out"));

		int n = Integer.parseInt(in.readLine());

		int[][] bounds = new int[n][2];
		int max=0;
		int min=(int)1e9;
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken())-1;
			bounds[i][0] = a;
			bounds[i][1] = b;
			min = Math.min(min, a);
			max = Math.max(max, b);
		}
		
		for (int i=0; i<n; i++) {
			bounds[i][0] -= min;
			bounds[i][1] -= min;
		}
		max -= min;
		
		TreeMap<Integer, Integer> all = new TreeMap<>();
		for (int i=0; i<n; i++) {
			all.put(bounds[i][0], all.getOrDefault(bounds[i][0], 0)+1);
			all.put(bounds[i][1]+1, all.getOrDefault(bounds[i][1]+1, 0)-1);
		}
		
		TreeMap<Integer, Integer> prefones = new TreeMap<>();
		// number of ones. this means if you remove something
						// on this interval, that point would get removed
				
		int total=0;
		int all_last = all.firstKey();
		for (Integer a : all.keySet()) {
			if (all.get(all_last) > 0) {
				total += (a - all_last);
			}
			if (a == all_last) continue;
			all.put(a, all.get(a) + all.get(all_last));
			all_last = a;
		}
		
		all_last = all.firstKey();
		int last_pref = -1;
		boolean last = false;
		if (all.get(all.firstKey()) == 1) {
			prefones.put(all_last, 1);
			last_pref = all_last;
			last = true;
		}
		
		for (Integer a : all.keySet()) {
			if (a == all_last) continue;
			if (last) {
						// put as 0 as a stopping point
				prefones.put(a, 0);
				prefones.put(a-1, prefones.get(last_pref) + (a - 1 - (last_pref+1) + 1));
				last_pref = a-1;
				last = false;
			}
			if (all.get(a) == 1) {
				last = true;
				prefones.put(a, prefones.get(last_pref)+1);
				last_pref = a;
			}
			all_last = a;
		}
		
		int result = 0;
		
		for (int i=0; i<n; i++) {
			int a = bounds[i][0];
			int b = bounds[i][1];
			int curdelete=0;
			
			Integer preva = prefones.floorKey(a-1);
			Integer prevb = prefones.floorKey(b);
			
			if (preva == null && prevb == null) {
				
			}
			else if (preva == null) {
				if (prefones.get(prevb) == 0) curdelete = prefones.get(prevb-1);
				else {
					// prevb+1 to b
					curdelete = prefones.get(prevb) + (b - (prevb + 1) + 1);
				}
			}
			else {
				if (prefones.get(prevb) == 0) {
					if (prefones.get(preva) == 0) {
						curdelete = prefones.get(prevb-1) - prefones.get(preva-1);
					}
					else {
							// preva + 1 to a - 1
						curdelete = prefones.get(prevb-1) - (prefones.get(preva) + (a - 1 - (preva + 1) + 1));
					}
				}
				else {
					if (prefones.get(preva) == 0) {
						curdelete = prefones.get(prevb) + (b - (prevb + 1) + 1) - prefones.get(preva-1);
					}
					else {
						curdelete = prefones.get(prevb) + (b - (prevb + 1) + 1) - (prefones.get(preva) + (a - 1 - (preva + 1) + 1));
					}
				}
			}
			
			result = Math.max(result, total - curdelete);
		}

		System.out.println(result);
		out.println(result);
		out.close();
	}
}