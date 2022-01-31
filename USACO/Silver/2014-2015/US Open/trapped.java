
import java.util.*;
import java.io.*;

public class trapped {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=547
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("trapped.in"));
		PrintWriter out = new PrintWriter(new FileWriter("trapped.out"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i] = new A(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		Arrays.parallelSort(arr);
		
		int ans=0;
		for (int k=0; k<n-1; k++) {
			// between regions k and k+1
			int l=k;
			int r=k+1;
			while (l >= 0 && r < n) {
				int dist = arr[r].pos - arr[l].pos;
				if (arr[r].size < dist) r++;
				else if (arr[l].size < dist) l--;
				else break;
			}
			
			if (l >= 0 && r < n) ans += arr[k+1].pos - arr[k].pos;	
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	static class A implements Comparable<A> {
		int size; int pos;
		A (int a, int b) {
			size = a; pos = b;
		}
		public int compareTo (A o) {
			return pos - o.pos;
		}
	}
}