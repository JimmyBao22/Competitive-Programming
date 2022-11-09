import java.util.*;
import java.io.*;

public class CountingSubstrings {

	// https://codeforces.com/edu/course/2/lesson/2/3/practice/contest/269118/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CountingSubstrings"));

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
			
			for (int i=0; i<n; i++) {		//shift all positions 2^k to the left
				p[i] = (p[i] - (1<<k) + n)%n;
			}
			
			CountSort(n,p,c);
			
			int[] c_new = new int[n];
			c_new[p[0]] = 0;
			
			for (int i=1; i<n; i++) {
				B prevB = new B(c[p[i-1]], c[(p[i-1] + (1<<k))%n]);	
				B curB = new B(c[p[i]], c[(p[i] + (1<<k))%n]);	
				if (curB.first == prevB.first && curB.second == prevB.second) {
					c_new[p[i]] = c_new[p[i-1]];
				}
				else {
					c_new[p[i]] = c_new[p[i-1]] + 1;
				}
			}
			c = c_new;
			k++;
		}
		
		int t = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder();
		while (t-->0) {
			String needed = in.readLine();
			int m = needed.length();
			int min=0;
			int max=n-1;
			// minimum
			while (min<max) {
				int middle = (min+max)/2;
				int compare = needed.compareTo(s.substring(p[middle], Math.min(n, p[middle]+m)));
				if (compare == 0) {
					max = middle;
				}
				else if (compare < 0) {
					// needed < s substring
					max = middle-1;
				}
				else {
					min = middle+1;
				}
			}
			int minimum = min;
			
			min = 0;
			max = n-1;
			// maximum
			while (min<max) {
				int middle = (min+max+1)/2;
				int compare = needed.compareTo(s.substring(p[middle], Math.min(n, p[middle]+m)));
				if (compare == 0) {
					min = middle;
				}
				else if (compare < 0) {
					// needed < s substring
					max = middle-1;
				}
				else {
					min = middle+1;
				}
			}
			int maximum = min;
			
			if (needed.compareTo(s.substring(p[min], Math.min(n, p[min]+m))) != 0) {
				sb.append("0\n");
			}
			else sb.append(maximum - minimum + 1 +"\n");
		}
		System.out.println(sb);
	}
	
	public static void CountSort(int n, int[] p, int[] c) {	
			// already sorted by second element, just sort by first
		
		int[] count = new int[n];		// count how many time an element appears	
		for (int i=0; i<n; i++) {
			count[c[i]]++;
		}
		
		int[] p_new = new int[n];
		int[] pos = new int[n];		// pos[i] = first empty position in bucket i 
		pos[0] = 0;
		for (int i=1; i<n; i++ ) {
			pos[i] = pos[i-1] + count[i-1];
		}
		
		for (int i=0; i<n; i++) {
			int val = c[p[i]];
			p_new[pos[val]] = p[i];
			pos[val]++;
		}
		for (int i=0; i<n; i++) p[i] = p_new[i];
	}
	
	static class B implements Comparable<B> {
		int first; int second;
		B (int a, int b) {
			first = a; second = b;
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