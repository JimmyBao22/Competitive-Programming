
import java.util.*;
import java.io.*;

public class MaximumAverageSegment {

	// https://codeforces.com/edu/course/2/lesson/6/4/practice/contest/285069/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MaximumAverageSegment"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		double[] arr = new double[n];
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		double min=0;
		double max = 100;
		int[] ans=new int[2];
		for (int i=0; i<100; i++) {
			double middle = (min+max)/2.0;
			int[] ret = check(middle,arr,n,d);
			if (ret[0]!=-1) {		// works
				min = middle;
				ans=ret;
			}
			else max = middle;
		}
		System.out.println(ans[0]+1 + " " + (ans[1]+1));
	}
	
	public static int[] check(double mid, double[] arr, int n, int d) {
		double[] p = new double[n+1];
		int[] m = new int[n+1];
		
		for (int i=1; i<=n; i++) {
			p[i] = p[i-1] + (arr[i-1]-mid);
			
			if (i==1)	m[i] = 1;
			else if (p[i] <= p[m[i-1]]) m[i] = i;
			else m[i] = m[i-1];
		}
		
		for (int r=d; r<=n; r++) {
			// sum arr[r-d] to arr[r-1]
			if (p[r] >= 0) return new int[] {0, r-1};
			if (p[r] >= p[m[r-d]]) return new int[] {m[r-d], r-1};
		}
		return new int[] {-1,-1};
	}
}

/*
	Binary Search on maximum
	
	We want to find (sum{i=l to r-1} {ai}) / (r-l) ≥ x
	sum{i=l to r-1} {ai} ≥ x(r-l)
	Subtract x from each term the left once. In total subtracted x(r-l)
	sum{i=l to r-1} {ai-x} ≥ 0
	
	This is simple prefix sums.
	Define p[j] = sum{i=0 to j-1} {ai-x}
	
	Therefore, in order for sum{i=l to r-1} {ai-x} ≥ 0, 
		p[r] - p[l] ≥ 0 --> p[r] ≥ p[l];
		
	Since r-l≥d, l≤r-d
	
	Therefore, keep an array m, which will keep the indices of the minimum.
	m[i] = index(min(p[0], p[1], ..., p[i]))
	
	This way, the minimum on segment (0,r-d) = p[m[r-d]]
	
	Therefore, we can loop over all end points (r).
		If p[r] ≥ 0, this means that sum{i=0 to r-1} {ai-x} ≥ 0
			return segment {0, r-1}
		If p[r] ≥ p[m[r-d]], this means that the sum{i=m[r-d] to r-1} {ai-x} ≥ 0
			return segment {m[r-d], r-1}
 */