import java.util.*;
import java.io.*;

public class CowFrisbee {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1183
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("CowFrisbee.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("CowFrisbee.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		int[] positions = new int[n+1];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			positions[arr[i]] = i;
		}
		
		long ans = 0;
		TreeSet<Integer> indices_used = new TreeSet<>();
		for (int val=n; val>=1; val--) {
			int i = positions[val];
			
			// find lower
			Integer prev = indices_used.lower(i);
			if (prev != null) ans += (i - prev + 1);
			
			// find upper
			Integer next = indices_used.higher(i);
			if (next != null) ans += (next - i + 1);
			
			indices_used.add(i);
		}
		
		System.out.println(ans);
		//		out.println();
		//		out.close();
	}
}