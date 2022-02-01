
import java.util.*;
import java.io.*;

public class InversionCount {

	// https://www.spoj.com/problems/INVCNT/
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ABKnapsack"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			in.readLine();
			int n = Integer.parseInt(in.readLine());
			A[] arr = new A[n];
			for (int i=0; i<n; i++) {
				arr[i] = new A(Integer.parseInt(in.readLine()), i);
			}
			
			BIT bit = new BIT(n);
			Arrays.parallelSort(arr);
			
			long ans=0;
			for (int i=0; i<n; i++) {
				ans += bit.sum(arr[i].index);
				bit.add(arr[i].index, 1);
			}
			
			System.out.println(ans);	
		}
	}
	
	static class A implements Comparable<A> {
		int val, index;
		A (int a, int b) {
			val = a; index = b;
		}
		public int compareTo(A o) {
			if (val == o.val) return o.index - index;
			return o.val - val;
		}
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

		// sum from l to r
		long sum (int l, int r) {	
			return sum(r) - sum(l-1);
		}
		
		// add value to index i
		void add(int i, long value) {	
			i++;
			while (i<=n) {
				f[i] += value;
				i += i&-i;
			}
		}

		// add value to indices l to r --> arr[i] = sum(i);
		void range_add(int l, int r, long value) {	
			add(l,value); add(r+1,-value);
		}
	}
}