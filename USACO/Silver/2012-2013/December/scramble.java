
import java.util.*;
import java.io.*;

public class scramble {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=206
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("scramble.in"));
		PrintWriter out = new PrintWriter(new FileWriter("scramble.out"));

		int n = Integer.parseInt(in.readLine());
		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			arr[i] = new A(in.readLine(), i);			
		}
		Arrays.sort(arr, new Comparator<A>() {
			public int compare(A a, A b) {
				return a.backwards.compareTo(b.backwards);
			}
		});
		
		// minimum place it can be
		for (int i=0; i<n; i++) {
			int min=0;
			int max=n-1;
			while (min<max) {
				int middle = (min + max)/2;
				int comp = arr[i].forwards.compareTo(arr[middle].backwards);
				if (comp <= 0) {
					max = middle;
				}
				else min = middle+1;
			}
			arr[i].firstplace = min;
		}
		
		Arrays.sort(arr, new Comparator<A>() {
			public int compare(A a, A b) {
				return a.forwards.compareTo(b.forwards);
			}
		});
		
		// maximum place it can be
		for (int i=0; i<n; i++) {
			int min=0;
			int max=n-1;
			while (min<max) {
				int middle = (min + max + 1)/2;
				int comp = arr[i].backwards.compareTo(arr[middle].forwards);
				if (comp < 0) {
					max = middle - 1;
				}
				else min = middle;
			}
			arr[i].lastplace = min;
		}
		
		StringBuilder s = new StringBuilder();

		int[][] ans = new int[n][2];
		for (int i=0; i<n; i++) {
			ans[arr[i].index][0] = arr[i].firstplace + 1;
			ans[arr[i].index][1] = arr[i].lastplace + 1;
		}
		
		for (int i=0; i<n; i++) {
			s.append(ans[i][0] + " " + ans[i][1]);
			s.append("\n");
		}
		
		System.out.print(s);
		out.print(s);
		out.close();

	}
	
	static class A {
		String s;
		int n;
		char[] arr;
		String forwards;
		String backwards;
		int firstplace;
		int lastplace;
		int index;
		A (String a, int j) {
			s = a;
			arr = s.toCharArray();
			n = arr.length;
			Arrays.sort(arr);
			forwards = new String(arr);
			backwards = "";
			for (int i=n-1; i>=0; i-- ) {
				backwards += arr[i];
			}
			index = j;
		}
		void print() {
			System.out.println(forwards + " " + backwards);
		}
	}
}