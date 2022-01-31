
import java.util.*;
import java.io.*;

public class proximity {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=260
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("proximity.in"));
		PrintWriter out = new PrintWriter(new FileWriter("proximity.out"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
		}
		
		HashMap<Integer, Integer> lastpos = new HashMap<>();
		
		int max=-1;
		for (int i=0; i<n; i++) {
			if (lastpos.containsKey(arr[i])) {
				int last = lastpos.get(arr[i]);
				if (i - last <= k) {
					max = Math.max(max, arr[i]);
				}
				lastpos.put(arr[i], i);
			}
			else {
				lastpos.put(arr[i], i);
			}
		}
		
		System.out.println(max);
		out.println(max);
		out.close();
	}
}