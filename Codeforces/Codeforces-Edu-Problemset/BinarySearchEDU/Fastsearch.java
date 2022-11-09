
import java.util.*;
import java.io.*;

public class Fastsearch {

	// https://codeforces.com/edu/course/2/lesson/6/1/practice/contest/283911/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Fastsearch"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(arr);
		
		int k=Integer.parseInt(in.readLine());
		while (k-->0) {
			st = new StringTokenizer(in.readLine());
			int l =Integer.parseInt(st.nextToken());
			int r = Integer.parseInt(st.nextToken());
			if (n == 1) {
				if (l<= arr[0] && r>= arr[0]) {
					System.out.print("1 ");
				}
				else System.out.print("0 ");
				continue;
			}
			if (l>arr[n-1] || r<arr[0]) {
				System.out.print("0 ");
				continue;
			}
			int min=0;
			int max=n-1;
			while (min < max) {
				int middle=(min + max)/2;
				if (arr[middle]<l) {
					min = middle+1;
					
				}
				else max = middle;
			}
			int left = min;
			min=0;
			max=n-1;
			while (min < max) {
				int middle = (min + max+1)/2;
				if (arr[middle] > r) {
					max = middle-1;
				}
				else min = middle;
			}
			int right=min;
			if (left>right) {
				System.out.print("0 ");
				continue;
			}
			System.out.print(right-left+1 + " ");
					
		}

	}

}
