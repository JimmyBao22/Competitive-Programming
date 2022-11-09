
import java.util.*;
import java.io.*;

public class VeryEasyTask {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/C
	
	static long n, x, y;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("VeryEasyTask"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		if (x>y) {
			long temp = x;
			x=y;
			y=temp;
		}
		
		long min = x;
		long max = (long)(1e9)+100;
		while (min < max) {
			long middle = (min+max)/2;
			if (check(middle)) {
				max = middle;
			}
			else min = middle+1;
		}
		
		System.out.println(min);
		
	}
	
	public static boolean check(long t) 
	{
		long num=1;
		t -= x;
		num += (t/x);
		num += (t/y);
		return num >= n;
	}
}