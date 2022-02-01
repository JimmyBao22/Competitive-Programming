
import java.util.*;
import java.io.*;

public class StaticRMQ3 {

	// https://judge.yosupo.jp/problem/staticrmq
	
	static int n;
	static long[] arr;
	
	// O(Nsqrt(N)) --> 839 ms
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
		
		SqrtDecomp sd = new SqrtDecomp();

		StringBuilder s = new StringBuilder();
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			s.append(sd.query(a, b-1));
			s.append("\n");
		}
		System.out.print(s);
	}
	
	static class SqrtDecomp {
		
		int len;				// size of blocks and # of blocks
		long[] b;
		long INF = (long)(1e18);
		
		public SqrtDecomp() {
			len = (int) (Math.sqrt(n) + 1);
			b = new long[len];
			Arrays.fill(b, INF);
			for (int i=0; i<n; i++) b[i/len] = Math.min(b[i/len], arr[i]);
		}
		
		// query l to r
		public long query(int l, int r) {
			long min=INF;
			int left = l/len; int right = r/len;
			if (left == right) {
				for (int i=l; i<=r; i++) min = Math.min(min, arr[i]);
			}
			else {
				for (int i=l; i<=(left + 1)*len - 1; i++) min = Math.min(min, arr[i]);
				for (int i=left+1; i<=right-1; i++) min = Math.min(min, b[i]);
				for (int i=right*len; i<=r; i++) min = Math.min(min, arr[i]);
			}
			return min;
		}
	}
}