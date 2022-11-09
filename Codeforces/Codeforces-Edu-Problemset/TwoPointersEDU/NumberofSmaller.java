
import java.util.*;
import java.io.*;

public class NumberofSmaller {

	// https://codeforces.com/edu/course/2/lesson/9/1/practice/contest/307092/problem/B
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("NumberofSmaller"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] a = new int[n];
		int[] b = new int[m];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) a[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<m; i++) b[i] = Integer.parseInt(st.nextToken());
		
		int j=0;
		for (int i=0; i<m; i++) {
			while (j<n && b[i] > a[j]) j++;
			System.out.print(j + " ");
		}
	}
}
