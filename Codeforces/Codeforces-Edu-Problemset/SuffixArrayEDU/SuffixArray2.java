
import java.util.*;
import java.io.*;

public class SuffixArray2 {

	// https://codeforces.com/edu/course/2/lesson/2/2/practice/contest/269103/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SuffixArray2"));

		String s = in.readLine() + "$";
		int n = s.length();
		int[] p = new int[n];	// ordering of the strings
		int[] c = new int[n];	// equivalence classes
		
		// k = 0
		A[] cur = new A[n];
			// character, index
		for (int i=0; i<n; i++) {
			cur[i] = new A(s.charAt(i), i);
		}
		Arrays.sort(cur);
		
		for (int i=0; i<n; i++) {
			p[i] = cur[i].index;
		}
		
		c[p[0]] = 0;
		for (int i=1; i<n; i++) {
			if (cur[i-1].c == cur[i].c) {
				c[p[i]] = c[p[i-1]];
			}
			else {
				c[p[i]] = c[p[i-1]] + 1;
			}
		}
		
		
		// transitions
		int k = 0;
		while ((1<<k) < n) {
			// k --> k+1
			// create array of pairs and sort
			B[] arr = new B[n];
			for (int i=0; i<n; i++) {
				arr[i] = new B(c[i], c[(i+(1<<k))%n], i);
					// first half, second half, index
			}
			RadixSort(n, arr);
			
			for (int i=0; i<n; i++) {
				p[i] = arr[i].index;
			}
			
			c[p[0]] = 0;
			for (int i=1; i<n; i++) {
				if (arr[i-1].first == arr[i].first && arr[i-1].second == arr[i].second) {
					c[p[i]] = c[p[i-1]];
				}
				else {
					c[p[i]] = c[p[i-1]] + 1;
				}
			}
			
			k++;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			sb.append(p[i] + " ");
		}
		System.out.println(sb);
	}
	
	public static void RadixSort(int n, B[] arr) {			
			// first sort by second element
		
		int[] count = new int[n];		// count how many time an element appears	
		for (int i=0; i<n; i++) {
			count[arr[i].second]++;
		}
		
		B[] arr_new = new B[n];
		int[] pos = new int[n];		// pos[i] = first empty position in bucket i 
		pos[0] = 0;
		for (int i=1; i<n; i++ ) {
			pos[i] = pos[i-1] + count[i-1];
		}
		
		for (int i=0; i<n; i++) {
			int val = arr[i].second;
			arr_new[pos[val]] = arr[i];
			pos[val]++;
		}
		
			// now sort by first element instead of second
		count = new int[n];
		for (int i=0; i<n; i++) {
			count[arr_new[i].first]++;
		}
		
		pos = new int[n];		// pos[i] = first empty position in bucket i 
		pos[0] = 0;
		for (int i=1; i<n; i++ ) {
			pos[i] = pos[i-1] + count[i-1];
		}
		
		for (int i=0; i<n; i++) {
			int val = arr_new[i].first;
			arr[pos[val]] = arr_new[i];
			pos[val]++;
		}
	}
	
	static class B implements Comparable<B> {
		int first; int second; int index;
		B (int a, int b, int c) {
			first = a; second = b; index = c;
		}
		public int compareTo(B o) {
			if (first == o.first) {
				return second - o.second;
			}
			return first - o.first;
		}
	}
	
	static class A implements Comparable<A> {
		char c;
		int index;
		A (char a, int b) {
			c = a; index = b;
		}
		public int compareTo(A o) {
			return Character.compare(c, o.c);
		}
	}
}

/*

	Basically the radix sort just uses O(n) instead of O(nlogn) to sort.
		It basically creates buckets which hold elements with the same value.
		First it sorts by the second value. Then by the first.
		
	https://codeforces.com/edu/course/2/lesson/2/2

*/