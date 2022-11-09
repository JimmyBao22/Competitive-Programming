
import java.util.*;
import java.io.*;

public class Gettogether {

	// https://codeforces.com/edu/course/2/lesson/6/3/practice/contest/285083/problem/A
	
	static int[][] arr;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Gettogether"));

		int n = Integer.parseInt(in.readLine());
		arr = new int[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			int two = Integer.parseInt(st.nextToken());
			arr[i][0] = one;
			arr[i][1] = two;
		}
		
		double min=0;
		double max = 1e10;
		for (int i=0; i<100; i++) {
			double middle = (min+max)/2;
			if (check(middle)) {
				max=middle;
			}
			else min = middle;
		}
		System.out.println(min);

	}
	
	public static boolean check(double t) {
		double left=-1e18;
		double right=1e18;
		for (int i=0; i<arr.length; i++) {
			double curleft = arr[i][0] - t*arr[i][1];
			double curright = arr[i][0] + t*arr[i][1];
			left = Math.max(left, curleft);
			right = Math.min(right, curright);
		}
		return left<=right;
	}
}

/*
	Binary search on the minimum time. 
	
	For check, you know that each person has to get to some position with time
		≤ current time (t)
	Each person (i) can move from their position xi to [xi-t*vi, xi+t*vi]
	
	For every person, you just need to make sure that all these possible moves
		intersect at some point, or that the left endpoint ≤ right endpoint
		over all people
*/