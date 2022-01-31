
import java.util.*;
import java.io.*;

public class sleepy {

	// http://usaco.org/index.php?page=viewproblem2&cpid=898
	
	static int n;
	static int[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("sleepy.in"));
		PrintWriter out = new PrintWriter(new FileWriter("sleepy.out"));

		n = Integer.parseInt(in.readLine());
		arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int index=n-2;
		int last = arr[n-1];
		for (; index>=0; index--) {
			if (arr[index] > last) {
				break;
			}
			last = arr[index];
		}
		// decreasing sequence in the end, do not need to move those cows
			// can just insert cows at the beginning into them optimally
		
		if (index<0) {
			System.out.println(0);
			out.println(0);
			out.close();
			return;
		}
		
			// number of moves
		System.out.println(index+1);
		out.println(index+1);
		
		BIT bit = new BIT(n);
		for (int i=n-1; i>index; i--) {			// cows who are not being moved
			bit.set(arr[i],1);	
		}
		ArrayList<Long> ans = new ArrayList<>();
		for (int i=0; i<=index; i++) {
			long k=bit.sum(arr[i]);
			ans.add(k+index-i);
			bit.set(arr[i], 1);			// set to 1 because this cow is no longer going to move
		}
		
		for (int i=0; i<=index; i++) {
			System.out.print(ans.get(i) + " ");
			if (i!=index) out.print(ans.get(i) + " ");
			else out.print(ans.get(i));
		}
		out.close();
	}
	
	static class BIT {
		int n;
		long[] f;	// 1 base indexing
		BIT (int n) {
			this.n = n; f = new long[n+1];
		}
		
		long sum (int i) {		// sum from 0 to i
			long ret=0;
			while (i>0) {
				ret += f[i];
				i -= i&-i;
			}
			return ret;
		}
		
		long sum (int l, int r) {	// sum from l to r
			return sum(r) - sum(l-1);
		}
		
		void set(int i, long value) {	// add value to index i
			while (i<=n) {
				f[i] += value;
				i += i&-i;
			}
		}
	}
	
	public static boolean check(ArrayList<Long> a) {
		int[] cur=  new int[n];
		for (int i=0; i<n; i++) cur[i] = arr[i];
		for (int i=0; i<a.size(); i++) {
			for (int j=1; j<=a.get(i); j++) {
				int temp=cur[j];
				cur[j] = cur[j-1];
				cur[j-1] = temp;
			}
		}
		for (int i=1; i<n; i++) {
			if (cur[i]!=cur[i-1]+1) return false;
		}
		return true;
	}
}