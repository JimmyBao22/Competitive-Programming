
import java.util.*;
import java.io.*;

public class nocow {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=342
	// http://usaco.org/index.php?page=viewproblem2&cpid=343
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("nocow.in"));
		PrintWriter out = new PrintWriter(new FileWriter("nocow.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		String[][] cows = new String[n][30];
		ArrayList<HashSet<String>> visited = new ArrayList<>();
		ArrayList<ArrayList<String>> adj = new ArrayList<>();
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			for (int j=0; j<4; j++) st.nextElement();
			int pointer=0;
			while (true) {
				String cur = st.nextToken();
				if (cur.equals("cow.")) break;
				if (adj.size()<=pointer) {
					adj.add(new ArrayList<>());
					adj.get(pointer).add(cur);
					visited.add(new HashSet<>());
					visited.get(pointer).add(cur);
				}
				else {
					if (!visited.get(pointer).contains(cur)) {
						adj.get(pointer).add(cur);
						visited.get(pointer).add(cur);
					}
				}
				cows[i][pointer] = cur;
				pointer++;
			}
		}
		
		int m = adj.size();
		for (int i=0; i<m; i++) {
			Collections.sort(adj.get(i));
		}
		
		int[] prod = new int[m];
		prod[m-1] = adj.get(m-1).size();
		for (int i=m-2; i>=0; i--) {
			prod[i] = prod[i+1] * adj.get(i).size();
		}
				
		k--;
		int[] ans = new int[m];
		int pointer=0;
		for (; pointer<m-1; pointer++) {
			if (prod[pointer+1] <= k) break;
		}
		
		for (; pointer<m-1; pointer++) {
			int res = k / prod[pointer+1];
			k -= res*prod[pointer+1];
			ans[pointer] = res;
		}
		
		ans[m-1] = k;
		
		boolean[] already_considered = new boolean[n];
			// skip over cows that john does not have that come before current cow
		while (true) {
			boolean did_something=false;
			for (int i=0; i<n; i++) {
				if (!already_considered[i]) {
					for (int j=0; j<m; j++) {

						int comp = cows[i][j].compareTo(adj.get(j).get(ans[j]));
						// either at this position a cow is less meaning it comes before it,
							// or at the end it is equal (aka all values were equal aka cows
								// are equal)
						if (comp < 0 || j == m-1 && comp == 0) {
							update(m, ans, adj);
							already_considered[i] = true;
							did_something=true;
							break;
						}
						else if (comp > 0) break;
					}
				}
			}
			if (!did_something) break;
		}
				
		StringBuilder s = new StringBuilder();
		for (int i=0; i<m; i++) {
			s.append(adj.get(i).get(ans[i]));
			if (i!=m-1) s.append(" ");
		}
		
		System.out.println(s);
		out.println(s);
		out.close();
	}
	
	public static void update(int m, int[] ans, ArrayList<ArrayList<String>> adj) {
		ans[m-1]++;
		for (int i=m-1; i>=1; i--) {
			if (ans[i] >= adj.get(i).size()) {
				ans[i-1]++;
				ans[i] = 0;
			}
			else break;
		}
	}
}