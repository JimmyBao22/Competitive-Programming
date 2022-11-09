
import java.util.*;
import java.io.*;

public class BinarySearch {

	// https://codeforces.com/edu/course/2/lesson/6/1/practice/contest/283911/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("BinarySearch"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		o: for (int i=0; i<k; i++) {
			int min=0;
			int max = n-1;
			int needed = Integer.parseInt(st.nextToken());
			while (min < max) {
				int middle = (min + max)/2;
				if (arr[middle] == needed) {
					System.out.println("YES");
					continue o;
				}
				else if (arr[middle] < needed) {
					min = middle+1;
				}
				else max = middle-1;
			}
			if (arr[min] == needed) {
				System.out.println("YES");
				continue;
			}
			System.out.println("NO");
		}
	}

}
