
import java.util.*;
import java.io.*;

public class msched {

	// http://usaco.org/index.php?page=viewproblem2&cpid=361
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("msched.in"));
		PrintWriter out = new PrintWriter(new FileWriter("msched.out"));

		int n = Integer.parseInt(in.readLine());

		cow[] arr = new cow[n];
		PriorityQueue<Integer> q = new PriorityQueue<>();
		int[] times = new int[10001];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			arr[i] = new cow(a,b);
			times[b]++;
		}
		Arrays.parallelSort(arr);
		
		int cowpointer=0;
		for (int i=1; i<10001; i++) {
			times[i] += times[i-1];
			while (cowpointer < n && arr[cowpointer].time == i) {
				q.add(arr[cowpointer].val);
				cowpointer++;
			}
				// there cannot be more than i cows that are milked with time = i.
					// therefore, remove cows up to this point that have the lowest
					// values, to make it back to i cows
			while (times[i] > i) {
				times[i]--;
				q.poll();
			}
		}
		
		int ans=0;
		while (!q.isEmpty()) {
			ans += q.poll();
		}
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static class cow implements Comparable<cow> {
		int time;
		int val;
		cow (int a, int b) {
			val = a;
			time = b;
		}
			// sort by time. if same time, bigger value first
		public int compareTo(cow o) {
			if (time == o.time) return o.val - val;
			return time - o.time;
		}
	}
}