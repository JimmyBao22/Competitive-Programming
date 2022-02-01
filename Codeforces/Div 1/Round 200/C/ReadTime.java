
import java.util.*;
import java.io.*;

public class ReadTime {

	// https://codeforces.com/contest/343/problem/C
	
	static int n,m;
	static long[] h;
	static long[] p;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ReadTime"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		h = new long[n];
		p = new long[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			h[i] = Long.parseLong(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) {
			p[i] = Long.parseLong(st.nextToken());
		}
		
		long min = 0;
		long max = (long)(1e18);
		while (min < max ) {
			long middle = min + (max - min)/2;
			if (check(middle)) {
				max = middle;
			}
			else {
				min = middle + 1;
			}
		}
		
		System.out.println(min);
	}
	
	public static boolean check(long time) {
		int ppoint = 0;
		for (int i=0; i<n; i++) {
			if (ppoint < m && p[ppoint] < h[i] && h[i] - p[ppoint] > time) return false;
			
			if (ppoint < m && p[ppoint] <= h[i]) {
				// go to a p[ppoint] first, find furthest you can go to
				int min=ppoint;
				int max = m-1;
				while (min < max) {
					int middle = (min + max + 1)/2;
					if (h[i] - p[ppoint] + p[middle] - p[ppoint] <= time) {
						min = middle;
					}
					else max = middle-1;
				}
				int left_first = min;
				
				// go to a right point first, then go back to p[ppoint]
				min = ppoint;
				while (min < m && p[min] <= h[i]) min++;
				if (min < m && p[min] - h[i] + p[min] - p[ppoint] <= time) {
					max = m-1;
					while (min < max) {
						int middle = (min + max + 1) / 2;
						if (p[middle] - h[i] + p[middle] - p[ppoint] <= time) {
							min = middle;
						}
						else max = middle-1;
					}
				}
				else min = ppoint;
				
				int right_first = min;
				
				ppoint = Math.max(left_first, right_first) + 1;
				while (ppoint < m && p[ppoint] <= h[i]) ppoint++;
			}
			else {
				while (ppoint < m && p[ppoint] - h[i] <= time) {
					ppoint++;
				}
			}
		}
		return ppoint == m;
	}
}