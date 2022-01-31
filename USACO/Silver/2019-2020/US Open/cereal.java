
import java.util.*;
import java.io.*;

public class cereal {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1039
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cereal.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cereal.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		int[] ans = new int[n];
		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		int[] last = new int[m+1];
		Arrays.fill(last, -1);
		ans[n-1] = 1;
		last[arr[n-1].t1] = n-1;
		for (int i=n-2; i>=0; i--) {
			ans[i] = ans[i+1] + 1;
			if (last[arr[i].t1] == -1) {
				last[arr[i].t1] = i;
			}
			else {
				int prev = i;
				while (prev != -1) {
					int prev2 = -1;
					if (last[arr[prev].t1] == -1 || last[arr[prev].t1] > prev) {
						prev2 = last[arr[prev].t1];
						last[arr[prev].t1] = prev;
					}
					else if (last[arr[prev].t2] == -1 || last[arr[prev].t2] > prev) {
						prev2 = last[arr[prev].t2];
						last[arr[prev].t2] = prev;
					}
					else {
						ans[i]--;
					}
					prev = prev2;
				}
			}
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			s.append(ans[i]);
			s.append("\n");
		}
		
		System.out.print(s);
		out.print(s);
		out.close();
	}
	
	static class A {
		int t1, t2;
		A (int a, int b) {
			t1 = a; t2 = b;
		}
	}
}

// go backwards, keep track of last one that used this color