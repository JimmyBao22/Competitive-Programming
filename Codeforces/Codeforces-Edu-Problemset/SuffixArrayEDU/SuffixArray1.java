
import java.util.*;
import java.io.*;

public class SuffixArray1 {

	// https://codeforces.com/edu/course/2/lesson/2/1/practice/contest/269100/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SuffixArray1"));

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
			Arrays.sort(arr);
			
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