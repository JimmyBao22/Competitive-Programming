
import java.util.*;
import java.io.*;

public class lineup {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=229
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lineup.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lineup.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		
		TreeMap<Integer, Long> vals = new TreeMap<>();
		HashMap<Integer, Integer> map = new HashMap<>();
		int rightp = -1;
		int ans = 0;
		for (int i=0; i<n; i++) {
			if (map.containsKey(arr[i])) {
				vals.put(map.get(arr[i]), vals.get(map.get(arr[i]))-1);
				if (vals.get(map.get(arr[i])) == 0) vals.remove(map.get(arr[i]));
				
				map.put(arr[i], map.get(arr[i])+1);
				vals.put(map.get(arr[i]), vals.getOrDefault(map.get(arr[i]), 0l)+1);
			}
			else {
				if (map.size()==k+1) {
					rightp=i;
					break;
				}
				else {
					vals.put(1, vals.getOrDefault(1, 0l)+1);
					map.put(arr[i], 1);
				}
			}
			ans = Math.max(ans, vals.lastKey());
		}
		
		if (rightp == -1) {
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
		
		for (int i=0; i<n; i++) {
			while (rightp < n && map.size()<=k+1) {
				if (map.containsKey(arr[rightp])) {
					vals.put(map.get(arr[rightp]), vals.get(map.get(arr[rightp]))-1);
					if (vals.get(map.get(arr[rightp])) == 0) vals.remove(map.get(arr[rightp]));
					
					map.put(arr[rightp], map.get(arr[rightp])+1);
					vals.put(map.get(arr[rightp]), vals.getOrDefault(map.get(arr[rightp]), 0l)+1);
				}
				else {
					if (map.size()==k+1) {
						break;
					}
					else {
						vals.put(1, vals.getOrDefault(1, 0l)+1);
						map.put(arr[rightp], 1);
					}
				}
				rightp++;
				ans = Math.max(ans, vals.lastKey());
			}
			// move i
			vals.put(map.get(arr[i]), vals.get(map.get(arr[i]))-1);
			if (vals.get(map.get(arr[i])) == 0) vals.remove(map.get(arr[i]));
			
			map.put(arr[i], map.get(arr[i])-1);
			
			if (map.get(arr[i])==0) map.remove(arr[i]);
			else vals.put(map.get(arr[i]), vals.getOrDefault(map.get(arr[i]), 0l)+1);
			
			if (vals.size()>0) ans = Math.max(ans, vals.lastKey());
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}