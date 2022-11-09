
import java.util.*;
import java.io.*;

public class ClosesttotheRight {

	// https://codeforces.com/edu/course/2/lesson/6/1/practice/contest/283911/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ClosesttotheRight"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());

		for (int i=0; i<k; i++) {
			int needed = Integer.parseInt(st.nextToken());
			if (arr[n-1] < needed) {
				System.out.println(n+1);
				continue;
			}
			int min =0;
			int max = n-1;
			while (min < max) {
				int middle = (min + max)/2;
				if (arr[middle] < needed) min = middle+1;
				else max = middle;
			}
			System.out.println(min+1);
		}

	}

}
