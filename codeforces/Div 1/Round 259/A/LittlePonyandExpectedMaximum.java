
import java.util.*;
import java.io.*;

public class LittlePonyandExpectedMaximum {

	// https://codeforces.com/contest/453/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("LittlePonyandExpectedMaximum"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());

		double ans=m;
		for (double i=m-1; i>=1; i--) {
			ans -= Math.pow(i/(double)m, n);
		}
		System.out.println(ans);
	}
}


/*

	Take an example. Let's say there are 6 sides, and 6 rolls
	how many ways will 1 be the maximum? 1 way
	
	how many ways will 2 be the max?
		
		- - - - - - 
		there are 6 spots, each spot can be 2 or 1. Though, subtract when all are 1
		2^6 - 1^6
		
	how many will 3 be max? 3^6 - 2^6
		this is because each spot can be 3,2, or 1. Subtract when 2 is the max
		
		
	for x, there will be x^n - (x-1)^n ways where x is the max.
	
	To take expected value given m sides, n dice rolls:
	
	1^n + 2*(2^n - 1^n) + 3*(3^n - 2^n) + ... + m*(m^n - (m-1)^n)
	m^(n+1) - (m-1)^n - ... - 3^n - 2^n - 1^n
	
	These are all possible outcomes and their max, divide by total number of outcomes
	
	(m^(n+1) - (m-1)^n - ... - 3^n - 2^n - 1^n) / m^n
	
	m - ((m-1)/m)^n - ... - (3/m)^n - (2/m)^n - (1/m)^n

*/