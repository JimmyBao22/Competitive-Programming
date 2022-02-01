
import java.util.*;
import java.io.*;

public class MarvoloGauntRing {
	
	// https://codeforces.com/problemset/problem/855/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MarvoloGauntRing.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		long p = Long.parseLong(st.nextToken());  
		long q = Long.parseLong(st.nextToken());  
		long r = Long.parseLong(st.nextToken());  
		
		long[] a = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i = 0; i < n ; i++) {
			a[i] = Integer.parseInt(st.nextToken()); 
		}
		in.close();
		
		long[] d0 = new long[n];
		d0[0] = a[0] * p;
		for (int i = 1; i < n; i++) {
			d0[i] = Math.max(d0[i-1], a[i]*p);
		}
		
		long[] d1 = new long[n];
		d1[0] = d0[0] + a[0] * q;
		for (int i = 1; i < n; i++) {
			d1[i] = Math.max(d1[i-1], d0[i] + a[i] * q);
		}
		
		long[] d2 = new long[n];
		d2[0] = d1[0] + a[0] * r;
		for (int i = 1; i < n; i++) {
			d2[i] = Math.max(d2[i-1], d1[i] + a[i] * r);
		}
		
		long result = d2[n-1];

		System.out.println(result);
	}
}

/*
 	
 	Let int[] d0 be an array where d0[x] be the max value of a[i] * P given i ≤ x.
 	
 	Then d0[x] is the max of either a[i] * P given i ≤ x-1, and a[i]*P when i = x.
 	d0[x] = Math.max(d0[x-1], a[x]*p);
 
 
 	Let int[] d1 be an array where d1[x] be the max value of a[i] * P + a[j] * Q given i ≤ j ≤ x.
 	
 	Then d1[x] is the max of either a[i] * P + a[j] * Q given i ≤ j ≤ x-1, and 
 	a[i] * P + a[j] * Q given i ≤ j = x.
 	
 	d1[x] = Math.max(d1[x-1], a[x]*p + a[x] * Q i ≤ j = x);
 	d1[x] = Math.max(d1[x-1], d0[x] + a[x] * Q);
 	
 	Same concept for d2[x] except now d2[x] is the max of a[i] * P + a[j] * Q + a[k] * R given i ≤ j ≤ k ≤ x.
 	 
 	d2[x] = Math.max(d2[x-1], d1[x] + a[x] * R);
 	
 	https://youtu.be/3SvG1q7IFCQ
*/
