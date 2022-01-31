
import java.util.*;
import java.io.*;

public class bphoto {

	// http://usaco.org/index.php?page=viewproblem2&cpid=693
	
	static int n;
	static A[] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("bphoto.in"));
		PrintWriter out = new PrintWriter(new FileWriter("bphoto.out"));

		int n = Integer.parseInt(in.readLine());
		arr = new A[n];
		for (int i=0; i<n; i++) {
			arr[i] = new A(Integer.parseInt(in.readLine()), i);
		}
		Arrays.parallelSort(arr);
		
		int ans=0;
		BIT bit = new BIT(n);
		for (int i=n-1; i>=0; i--) {
			int curindex = arr[i].index;
			int left = bit.add(0, curindex-1);
			int right = bit.add(curindex+1, n-1);
			if (left*2 < right || right*2 < left) ans++;
			bit.set(curindex, 1);
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	static class BIT {
		int n; 
		int[] f;
		BIT(int n) {
			this.n=n; f=new int[n+1];
		}
		
		int add(int l, int r) {
			return add(r) - add(l-1);
		}
		
		int add(int i) {
			++i;
			int ret=0;
			while (i>0) {
				ret += f[i];
				i -= i&-i;
			}
			return ret;
		}
		
		void set(int i, int val) {
			++i;
			while (i<=n) {
				f[i] += val;
				i += i&-i;
			}
		}
	}
	
	static class A implements Comparable<A> {
		int val; int index;
		A(int a, int b) {
			val=a; index=b;
		}
		public int compareTo(A o) {
			return val-o.val;
		}
	}
}