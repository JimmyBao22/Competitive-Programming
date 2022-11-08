
import java.util.*;
import java.io.*;

public class Stones {

	// https://atcoder.jp/contests/dp/tasks/dp_k
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Stones"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		boolean[] arr = new boolean[k+1];
		int[] values = new int[n];
		st = new StringTokenizer(in.readLine());
		int min = k;
		for (int i=0; i<n; i++) {
			values[i] = Integer.parseInt(st.nextToken());
			arr[values[i]] = true;
			min = Math.min(min, values[i]);
		}
		
		for (int i=min+1; i<=k; i++) {
			for (Integer j : values) {
				if (i - j < 0) continue;
				if (!arr[i-j]) {
					arr[i] = true;
					break;
				}
			}
		}
		
		if (arr[k]) {
			System.out.println("First");
		}
		else {
			System.out.println("Second");
		}		
	}
}

// arr[i] = does first player have a winning strategy
	// since first player in control, if can subtract a number from i to go to cell
	// i-j such as other player does not have a winning strategy (aka they will lose),
	// then this is a winning strategy.