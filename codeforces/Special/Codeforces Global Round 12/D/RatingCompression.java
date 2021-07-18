
import java.util.*;
import java.io.*;

public class RatingCompression {

	static int n;
	static int[] arr;
	
	// https://codeforces.com/contest/1450/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("four"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			n = Integer.parseInt(in.readLine());
			arr = new int[n];
			StringTokenizer st = new StringTokenizer(in.readLine());
			ArrayList<Integer>[] m = new ArrayList[n+1];
			
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				m[i+1] = new ArrayList<>();
			}
			for (int i=0; i<n; i++) {
				m[arr[i]].add(i);
			}
			
			SparsetableRMQ rmq = new SparsetableRMQ();
			
			int[] maxmoves = new int[n+1];
			
			int upto=-1;
			for (int i=1; i<=n; i++) {
				if (upto == -1 && m[i].size() != 1) {
					upto=i;
				}
				for (Integer j : m[i]) {
					int min=0;
					int max=j;
					while (min < max) {
						int middle = (min + max + 1)/2;
						if (rmq.query(j - middle, j) == i) {
							min = middle;
						}
						else max = middle-1;
					}
					int left = min;
					min = 0;
					max = n - j - 1;
					while (min < max) {
						int middle = (min + max+1)/2;
						if (rmq.query(j, j+middle) == i) {
							min = middle;
						}
						else max = middle-1;
					}
					int right = min;
					
					maxmoves[i] = Math.max(maxmoves[i], j+right - (j - left) + 1);
				}
			}
			if (upto == -1) upto = n;
			
			int[] ans = new int[n+1];
			
			int minsofar=maxmoves[1];
			for (int i=1; i<=upto; i++) {
				minsofar = Math.min(minsofar, maxmoves[i]);
				if (ans[i-1] == 1 && maxmoves[i] >= n - i + 1) {
					ans[i] = 1;
				}
				else if (ans[i-1] == 0 && maxmoves[i] >= n - i + 1) {
					if (minsofar >= n - i + 1) {
						ans[i] = 1;
					}
				}
			}
			
			StringBuilder s = new StringBuilder();
			for (int i=n; i>=1; i--) {
				s.append(ans[i]);
			}
			System.out.println(s);
			
		}

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