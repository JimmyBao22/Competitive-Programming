
import java.util.*;
import java.io.*;

public class PackingRectangles {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PackingRectangles"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		long w = Integer.parseInt(st.nextToken());
		long h = Integer.parseInt(st.nextToken());
		long n = Integer.parseInt(st.nextToken());

		long min = 0;
		long max = 1;
		while (!check(max, w, h, n)) {
			min=max;
			max *= 2;
		}
		while (min < max) {
			long middle = min + (max - min)/2;
			if (check(middle, w, h, n)) {
				max = middle;
			}
			else min = middle+1;
		}
		System.out.println(min);
	}
	
	public static int log(long top, int base) {
		return (int)(Math.log(top)/Math.log(base));
	}
	
	public static boolean check(long x, long w, long h, long n) {
		return (x/w) * (x/h) >= n;
	}
}