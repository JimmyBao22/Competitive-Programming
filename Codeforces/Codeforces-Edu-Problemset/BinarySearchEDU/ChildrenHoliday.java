
import java.util.*;
import java.io.*;

public class ChildrenHoliday {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ChildrenHoliday"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		A[] arr = new A[n];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			arr[i] = new A(a,b,c);
		}
		
		long min = 0;
		long max = 1;
		while (!check(max, arr, m)) {
			min=max;
			max *= 2;
		}
		while (min < max) {
			long middle = min + (max-min)/2;
			if (check(middle, arr, m)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		System.out.println(min);
		for (A a : arr) {
			int num=0;
			long group = a.t*a.z+a.y;
			num += (min/group) * a.z;
			long left = min%group;
			num += Math.min(left/a.t, a.z);
			num = Math.min(num, m);
			m -= num;
			System.out.print(num+" ");
		}
		
	}
	
	public static boolean check(long mid, A[] arr, int m) {
		int num=0;
		for (A a : arr) {
			long group = a.t*a.z+a.y;
			num += (mid/group) * a.z;
			long left = mid%group;
			num += Math.min(left/a.t, a.z);
		}
		return num>=m;
	}
	
	static class A {
		int t;
		int z;
		int y;
		A (int a, int b, int c) {
			t=a; z=b; y=c;
		}
	}

}
