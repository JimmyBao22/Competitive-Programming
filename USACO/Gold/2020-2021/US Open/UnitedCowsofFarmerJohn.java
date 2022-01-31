
import java.util.*;
import java.io.*;

public class UnitedCowsofFarmerJohn {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1137
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("UnitedCowsofFarmerJohn"));

		int n = Integer.parseInt(in.readLine());
		int[] arr=new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		TreeSet<Integer>[] map = new TreeSet[n];
		for (int i=0; i<n; i++) {
			map[i]=new TreeSet<>();
		}
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken())-1;
			map[arr[i]].add(i);
		}
		
		BIT bit = new BIT(n);
		long ans=0;
		for (int i=0; i<n; i++) {
			Integer lower = map[arr[i]].floor(i-1);
			if (lower == null) lower = -1;
			ans += bit.sum(lower+1);
			bit.range_add(0, i, 1);
			if (lower != -1) bit.range_add(0, lower, -1);
		}
		
		System.out.println(ans);
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

		// add value to indices l to r --> arr[i] = sum(i);
		void range_add(int l, int r, long value) {	
			add(l,value); add(r+1,-value);
		}
	}
}