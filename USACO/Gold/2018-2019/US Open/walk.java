
import java.util.*;
import java.io.*;

public class walk {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=946
	
	static int n,k;
	static long mod = 2019201997;
	static long INF = (long)(1e18);
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("walk.in"));
		PrintWriter out = new PrintWriter(new FileWriter("walk.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		long ans = MST2(n-1);

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static long MST2(int start) {
		boolean[] visited = new boolean[n];
		long[] dist = new long[n];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		long val = 0;
		
		int count=0;
		while (count < n-k+2) {
			// find the smallest one
			int smallest=-1;
			long minval=INF;
			for (int i=0; i<n; i++) {
				if (!visited[i] && dist[i]<minval) {
					minval = dist[i];
					smallest = i;
				}
			}
			if (smallest == -1) break;
			val = minval;
			
			for (int i=0; i<n; i++) {
				if (!visited[i] && calcdist(smallest+1, i+1) < dist[i]) {
					dist[i] = calcdist(smallest+1, i+1);
				}
			}
			visited[smallest] = true;
			count++;
		}
		return val;
	}
	
	public static long calcdist(long i, long j) {
		if (i < j) return ((-84*i + -48*j)%mod+mod)%mod;
		else return ((-84*j + -48*i)%mod+mod)%mod;
	}
}

/*

	when you are drawing out the actual graph, you want to keep taking the max
		edge available until you are left with k groups.
		
	Another way to think about it is, we take the opposite graph.
		So, we can take the minimum spanning tree and take n-k edges. This will leave us with
		 k groups left.
		
	note: not rly sure why while loops need to be <n-k+2, but it does for the sample
	
*/