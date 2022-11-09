
import java.util.*;
import java.io.*;

public class NumberofEqual {

	// https://codeforces.com/edu/course/2/lesson/9/1/practice/contest/307092/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("NumberofEqual"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] a = new int[n];
		int[] b = new int[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) b[i] = Integer.parseInt(st.nextToken());
		
		long ans=0;
		int j=0;
		for (int i=0; i<n; i++) {
			int val = a[i];
			while (j < m && val > b[j]) j++;
			long c1=0;
			for (; i<n; i++) {
				if (a[i] != val) {
					i--;
					break;
				}
				c1++;
			}
			long c2=0;
			for (; j<m; j++) {
				if (b[j] != val) {
					break;
				}
				c2++;
			}
			ans += c1*c2;
		}
		
		System.out.println(ans);
	}
}
