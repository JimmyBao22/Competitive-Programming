
import java.util.*;
import java.io.*;

public class CliqueProblem {

	// https://codeforces.com/contest/527/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CliqueProblem"));

		int n = Integer.parseInt(in.readLine());
		obj[] arr = new obj[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			long a = Integer.parseInt(st.nextToken());
			long b = Integer.parseInt(st.nextToken());
			arr[i] = new obj(a,b);
		}
		Arrays.parallelSort(arr);
		
		int ans = 1;
		int last = 0;
		for (int i=1; i<n; i++) {
			if (arr[i].x - arr[i].w >= arr[last].x + arr[last].w) {
				last = i;
				ans++;
			}
		}
		System.out.println(ans);
		
	}

	static class obj implements Comparable<obj> { 
		long x;
		long w;
		obj (long a, long b) {
			x = a; w = b;
		}
		public int compareTo(obj other) {
			return Long.compare(x+w, other.x + other.w);
		}
	}
}
