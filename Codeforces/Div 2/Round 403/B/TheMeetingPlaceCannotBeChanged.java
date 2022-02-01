
import java.util.*;
import java.io.*;

public class TheMeetingPlaceCannotBeChanged {

	// https://codeforces.com/contest/782/problem/B
	
	static int n;
	static double[] pos;
	static double[] speed;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("TheMeetingPlaceCannotBeChanged"));

		n = Integer.parseInt(in.readLine());
		pos = new double[n];
		speed =new double[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			pos[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			speed[i] = Integer.parseInt(st.nextToken());
		}
		
		double min=0;
		double max=(double)(1e9);
		for (int i=0; i<100; i++) {
			double middle = (min+max)/2;
			if (check(middle)) {
				max=middle;
			}
			else min = middle;
		}
		System.out.println(min);
		
	}
	
	public static boolean check(double mid) {
		double left=1;
		double right = (double)(1e9);
		for (int i=0; i<n; i++) {
			double l = pos[i] - speed[i]*mid;
			double r = pos[i] + speed[i]*mid;
			left = Math.max(left, l);
			right = Math.min(right, r);
		}
		return left <= right;
	}

	public static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	public static void shuffle(int[] a) {
		Random get = new Random();
		for (int i = 0; i < a.length; i++) {
			int r = get.nextInt(a.length);
			int temp = a[i];
			a[i] = a[r];
			a[r] = temp;
		}
	}

}