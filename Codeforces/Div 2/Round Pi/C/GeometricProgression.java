
import java.util.*;
import java.io.*;

public class GeometricProgression {

	// https://codeforces.com/contest/567/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("GeometricProgression"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
			// int, indices
		HashMap<Integer, Long> val = new HashMap<>();
		st = new StringTokenizer(in.readLine());
		int[] arr = new int[n];
		long ans=0;
		
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			
			if (arr[i]%k == 0 && i>0) {
				
				int needed = arr[i]/k;
				
				if (map.containsKey(needed)) {					
					if (needed%k == 0 && i>1) {
						if (val.containsKey(needed/k)) ans += val.get(needed/k);
					}
					
					val.put(needed, val.getOrDefault(needed, 0l) + map.get(needed).size());
				}
			}
			
			if (map.containsKey(arr[i])) {
				map.get(arr[i]).add(i);
			}
			else {
				ArrayList<Integer> cur = new ArrayList<>();
				cur.add(i);
				map.put(arr[i], cur);
			}
		}
		
		System.out.println(ans);
		
	}
}