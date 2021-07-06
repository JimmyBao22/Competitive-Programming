
import java.util.*;
import java.io.*;

public class MountainMileage {

	// https://codeforces.com/gym/102802/problem/C
	// https://mbit.mbhs.edu/archive/2019/varsity.pdf
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MountainMileage"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long a = Integer.parseInt(st.nextToken());
		long b = Integer.parseInt(st.nextToken());
		long[] arr = new long[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		Arrays.parallelSort(arr);
		
		long left=0;
		long right=0;
		for (int i=1; i<n; i++) {
			right += (arr[i] - arr[0]) * b;
		}
		
		long min = right;
		
		for (int i=1; i<n; i++) {
			left += (arr[i] - arr[i-1]) * a * (i);
			right -= (arr[i] - arr[i-1]) * b * (n - i);
			min = Math.min(min, left + right);
		}
		
		System.out.println(min);
	}
}