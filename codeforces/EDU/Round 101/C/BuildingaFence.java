
import java.util.*;
import java.io.*;

public class BuildingaFence {

	// https://codeforces.com/contest/1469/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("three"));

		int t = Integer.parseInt(in.readLine());
		o: while (t-->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			long k = Integer.parseInt(st.nextToken());
			long[] h = new long[n];
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; ++i) {
				h[i] = Integer.parseInt(st.nextToken());
			}
			
			long[][] rangebottom = new long[n][2];
			long[][] rangetop = new long[n][2];
			rangebottom[0][0] = h[0];
			rangebottom[0][1] = h[0];
			rangetop[0][0] = rangebottom[0][0]+k;
			rangetop[0][1] = rangebottom[0][1]+k;
			
			for (int i=1; i<n; i++) {
				rangebottom[i][0] = Math.max(h[i], rangebottom[i-1][0]+1-k);
				rangebottom[i][1] = Math.min(h[i]+k-1, rangetop[i-1][1]-1);
				rangetop[i][0] = rangebottom[i][0] + k;
				rangetop[i][1] = rangebottom[i][1] + k;
			}
			
			for (int i=1; i<n; i++) {
				if (rangetop[i][1] <= rangebottom[i-1][0]) {
					System.out.println("NO");
					continue o;
				}
				if (rangebottom[i][0] >= rangetop[i-1][1]) {
					System.out.println("NO");
					continue o;
				}
				if (rangebottom[i][0] > rangebottom[i][1]) {
					System.out.println("NO");
					continue o;
				}
			}
			
			if (rangebottom[n-1][0] != h[n-1]) {
				System.out.println("NO");
				continue o;
			}
			
			System.out.println("YES");

		}

	}
}