
import java.util.*;
import java.io.*;

public class art2 {

	// http://usaco.org/index.php?page=viewproblem2&cpid=743
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("art2.in"));
		PrintWriter out = new PrintWriter(new FileWriter("art2.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr =new int[n];
		ArrayList<Integer> nums = new ArrayList<>();
		HashMap<Integer, Integer> start = new HashMap<>();
		HashMap<Integer, Integer> end = new HashMap<>();
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(in.readLine());
			if (arr[i] == 0) continue;
			if (start.containsKey(arr[i])) {
				end.put(arr[i], i);
			}
			else {
				start.put(arr[i], i);
				nums.add(arr[i]);
			}
		}

		int[] line = new int[n+1];
		for (Integer a : start.keySet()) {
			line[start.get(a)]++;
			if (end.containsKey(a)) {
				line[end.get(a)+1]--;
			}
			else {
				line[start.get(a)+1]--;
			}
		}
		
		ArrayDeque<Integer> s = new ArrayDeque<>();
		int ans=line[0];
		if (line[0] == 1) s.add(arr[0]);
		for (int i=1; i<=n; i++) {
			if (i != n) {
				if (arr[i] != 0) {
					if (end.containsKey(arr[i]) && end.get(arr[i]) == i) {
						if (s.pollLast() != arr[i]) {
							System.out.println(-1);
							out.println(-1);
							out.close();
							return;
						}
					}
				}
				if (arr[i] != 0) {
					if (start.get(arr[i]) == i) {
						s.add(arr[i]);
						if (!end.containsKey(arr[i])) s.removeLast();
					}
				}
			}
			line[i] += line[i-1];
			ans = Math.max(ans, line[i]);
		}

		System.out.println(ans);
		out.println(ans);
		out.close();

	}
}