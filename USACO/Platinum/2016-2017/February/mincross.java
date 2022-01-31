
import java.util.*;
import java.io.*;

public class mincross {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=720
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("mincross.in"));
		PrintWriter out = new PrintWriter(new FileWriter("mincross.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr1 = new int[n], arr2 = new int[n], pos1 = new int[n], pos2 = new int[n];
		for (int i=0; i<n; i++) {
			arr1[i] = Integer.parseInt(in.readLine())-1;
			pos1[arr1[i]] = i;
		}
		for (int i=0; i<n; i++) {
			arr2[i] = Integer.parseInt(in.readLine())-1;
			pos2[arr2[i]] = i;
		}
		
		long initial = 0;
		
		BIT bit = new BIT(n);
		// find initial # of intersections
		for (int i=n-1; i>=0; i--) {
			initial += bit.sum(pos2[arr1[i]]);
			bit.add(pos2[arr1[i]], 1);
		}
		
		long ans = initial;
		long cur = initial;
		
			// rotate 1
		for (int i=0; i<n-1; i++) {
			// arr[n-1-i] is at end
			cur = cur - (n - pos2[arr1[n-1-i]] - 1) + pos2[arr1[n-1-i]];
			ans = Math.min(ans, cur);
		}
		
		cur = initial;
			// rotate 2
		for (int i=0; i<n-1; i++) {
			// arr[n-1-i] is at end
			cur = cur - (n - pos1[arr2[n-1-i]] - 1) + pos1[arr2[n-1-i]];
			ans = Math.min(ans, cur);
		}
				
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	static class BIT {
		int n;
		long[] f;	// 1 base indexing
		BIT (int n) {
			this.n = n; f = new long[n+1];
		}
		
		// sum from i to 0
		long sum (int i) {		
			i++;
			long ret=0;
			while (i>0) {
				ret += f[i];
				i -= i&-i;
			}
			return ret;
		}
		
		// add value to index i
		void add(int i, long value) {	
			i++;
			while (i<=n) {
				f[i] += value;
				i += i&-i;
			}
		}
	}
	
	public static int brute(int n, int[] arr1, int[] arr2, int[] pos1, int[] pos2) {
		int ans = (int)(1e9);
		for (int i=0; i<n; i++) {
			int[] temp = new int[n];
			for (int j=0; j<n; j++) {
				temp[(j+1)%n] = arr1[j];
			}
			for (int j=0; j<n; j++) {
				arr1[j] = temp[j];
			}
			
			int count=0;
			for (int j=0; j<n; j++ ) {
				for (int k=j+1; k<n; k++) {
					// arr1[j], arr1[k]
					if (pos2[arr1[j]] > pos2[arr1[k]]) {
						count++;
					}
				}
			}
			ans = Math.min(ans, count);
		}
		
		for (int i=0; i<n; i++) {
			int[] temp = new int[n];
			for (int j=0; j<n; j++) {
				temp[(j+1)%n] = arr2[j];
			}
			for (int j=0; j<n; j++) {
				arr2[j] = temp[j];
			}
			
			int count=0;
			for (int j=0; j<n; j++ ) {
				for (int k=j+1; k<n; k++) {
					if (pos1[arr2[j]] > pos1[arr2[k]]) {
						count++;
					}
				}
			}
			ans = Math.min(ans, count);
		}
		
		return ans;
	}
}