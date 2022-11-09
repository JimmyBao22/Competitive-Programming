
import java.util.*;
import java.io.*;

public class PairSelection {

	// https://codeforces.com/edu/course/2/lesson/6/4/practice/contest/285069/problem/C
	
	static int n,k;
	static double[][] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PairSelection"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		arr = new double[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		double min=0;
		double max = (long)(1e11);
		for (int i=0; i<75; i++) {
			double middle = (min + max)/2;
			if (check(middle)) {
				min = middle;
			}
			else max = middle;
		}
		
		System.out.println(min);
	}
	
	public static boolean check(double mid) {
		double[] sum = new double[n];
		for (int i=0; i<n; i++) {
			sum[i] = arr[i][0] - mid * arr[i][1];
		}
		Arrays.parallelSort(sum);
		double s = 0;
		for (int i=n-1; i>=n-k; i--) {
			s += sum[i];
		}
		return s >= 0;
	}
}