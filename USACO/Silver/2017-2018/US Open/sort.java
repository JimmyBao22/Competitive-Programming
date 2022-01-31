
import java.util.*;
import java.io.*;

public class sort {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=834
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("sort.in"));
		PrintWriter out = new PrintWriter(new FileWriter("sort.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		A[] arr2 = new A[n];
		for (int i=0; i<n; i++ ) {
			arr[i] = Integer.parseInt(in.readLine());
			arr2[i] = new A(i, arr[i]);
		}
		
		Arrays.parallelSort(arr2);
		int count=0;
		for (int i=0; i<n; i++) {
			count = Math.max(count, arr2[i].oldind - i);
		}

		System.out.println(count+1);
		out.println(count+1);
		out.close();

	}
	
	static class A implements Comparable<A> {
		int oldind; int val; 
		A (int a, int b) {
			oldind = a; val = b;
		}
		public int compareTo (A o) {
			if (val == o.val) return oldind - o.oldind;
			return val - o.val;
		}
	}
	
	public static int brute(int[] arr) {
		int n = arr.length;
		int count=0;
		boolean sorted=false;
		while (!sorted) {
			sorted=true;
			count++;
			for (int i=0; i<n-1; i++) {
				if (arr[i+1] < arr[i]) {
					int temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					sorted=false;
				}
			}
		}
		return count;
	}
}