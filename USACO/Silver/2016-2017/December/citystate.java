
import java.util.*;
import java.io.*;

public class citystate {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=667
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("citystate.in"));
		PrintWriter out = new PrintWriter(new FileWriter("citystate.out"));

		int n = Integer.parseInt(in.readLine());
		String[] states = new String[n];
		String[] cities = new String[n];
		HashMap<String, HashMap<String, Long>> citiestostates = new HashMap<>();
		
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			cities[i] = st.nextToken();
			states[i] = st.nextToken();
			String curcity = cities[i].substring(0, 2);
			
			if (citiestostates.containsKey(curcity)) {
				citiestostates.get(curcity).put(states[i], citiestostates.get(curcity).getOrDefault(states[i],0l)+1);
			}
			else {
				HashMap<String, Long> cur = new HashMap<>();
				cur.put(states[i], 1l);
				citiestostates.put(curcity, cur);
			}
		}
		
		long ans=0;
		HashMap<String, HashSet<String>> used = new HashMap<>();
		for (String city : citiestostates.keySet()) {
			for (String state : citiestostates.get(city).keySet()) {
				if (citiestostates.containsKey(state)) {
					if (citiestostates.get(state).containsKey(city)) {
						if ((used.containsKey(city) && used.get(city).contains(state)) || (used.containsKey(state) && used.get(state).contains(city))) {
							continue;
						}
						
						if (used.containsKey(state)) {
							used.get(state).add(city);
						}
						else {
							HashSet<String> cur = new HashSet<>();
							cur.add(city);
							used.put(state, cur);
						}
												
						if (state.equals(city)) {
							continue;
						}
						else {
							ans += citiestostates.get(state).get(city) * citiestostates.get(city).get(state);
						}
						
					}
				}
			}
		}

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}