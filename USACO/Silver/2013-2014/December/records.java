
import java.util.*;
import java.io.*;

public class records {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=358
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("records.in"));
		PrintWriter out = new PrintWriter(new FileWriter("records.out"));

		int n = Integer.parseInt(in.readLine());
		HashMap<String[], Integer> map = new HashMap<>();
		for (int i=0; i<n; i++ ) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			String[] cur =new String[3];
			cur[0] = st.nextToken();
			cur[1] = st.nextToken();
			cur[2] = st.nextToken();
			Arrays.parallelSort(cur);
			
			boolean found=false;
			for (String[] alr : map.keySet()) {
				if (alr[0].equals(cur[0]) && alr[1].equals(cur[1]) && alr[2].equals(cur[2])) {
					map.put(alr, map.get(alr)+1);
					found=true;
					break;
				}
			}
			
			if (!found) {
				map.put(cur, 1);
			}
		}
		
		int max=1;
		for (String[] alr : map.keySet()) {
			max = Math.max(max, map.get(alr));
		}

		System.out.println(max);
		out.println(max);
		out.close();
	}
}