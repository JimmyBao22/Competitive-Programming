
import java.util.*;
import java.io.*;

public class MergingArrays {

	// https://codeforces.com/edu/course/2/lesson/9/1/practice/contest/307092/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MergingArrays"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] a = new int[n];
		int[] b = new int[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) b[i] = Integer.parseInt(st.nextToken());
		
		int[] c = new int[n+m];
		int i=0;
		int j=0;
		while (i < a.length && j < b.length) {
			if (a[i] < b[j]) {
				c[i+j] = a[i];
				i++;
			}
			else {
				c[i+j] = b[j];
				j++;
			}
		}
		while (i<a.length) {
			c[i+j] = a[i];
			i++;
		}
		while (j < b.length) {
			c[i+j] = b[j];
			j++;
		}
		
		for (int k=0; k<n+m; k++) {
			System.out.print(c[k] + " ");
		}
	}
}
