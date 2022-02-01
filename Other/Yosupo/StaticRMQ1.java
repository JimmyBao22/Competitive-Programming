
import java.util.*;
import java.io.*;

public class StaticRMQ1 {

	// https://judge.yosupo.jp/problem/staticrmq
	
	static int n;
	static long[] arr;
	
	// O(NlogN) --> 752 ms
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("StaticRMQ"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		arr = new long[n];
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		SparsetableRMQ rmq = new SparsetableRMQ();

		StringBuilder s = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			s.append(rmq.query(a, b-1));
			s.append("\n");
		}
		System.out.print(s);
	}
	
	static class SparsetableRMQ {
		
		int len;
		long[][] st;
		
		public SparsetableRMQ() {
			len = 32;
			st = new long[len][n];
			
			for (int i=0; i<n; i++) {
				st[0][i] = arr[i];
			}
			
			for (int log=1; (1 << log)<=n; log++) {
				for (int i=0; i + (1 << log) < n+1; i++) {
					st[log][i] = Math.min(st[log-1][i], st[log-1][i + (1 << (log-1))]);
				}
			}
		}
		
		// query l to r
		public long query(int l, int r) {
			int j = log2(r - l + 1);
			return Math.min(st[j][l], st[j][r - (1 << j) + 1]);
		}
		
		public int log2(int n) {
			return 31 - Integer.numberOfLeadingZeros(n);
		}
	}
}