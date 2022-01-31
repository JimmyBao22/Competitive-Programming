
import java.util.*;
import java.io.*;

public class div7 {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=595
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("div7.in"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		int[] psums = new int[n+1];
		HashMap<Integer, Integer> map = new HashMap<>();
		int max=0;
		for (int i=0; i<n; i++) {
			int cur = Integer.parseInt(in.readLine())%7;
			arr[i] = cur;
			psums[i+1] = (psums[i] + arr[i])%7;
			
			if (psums[i+1] == 0) {
				max = Math.max(max, i+1);
			}
			else {
				if (map.containsKey(psums[i+1])) {
					max = Math.max(max, (i) - map.get(psums[i+1]));
				}
				else {
					map.put(psums[i+1], i);
				}
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("div7.out"));
		System.out.println(max);
		out.println(max);
		out.close();
	}
}