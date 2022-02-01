
import java.util.*;
import java.io.*;

public class FindMaximum {

	// https://codeforces.com/contest/353/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("FindMaximum"));

		int n = Integer.parseInt(in.readLine());  
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		char[] ones = in.readLine().toCharArray();
		int index=0;
		for (int i=n-1; i>=0; i--) {
			if (ones[i] == '1') {
				// find the first one!
				index=i;
				break;
			}
		}

			// ignore the stuff that you can never reach
		int[] betarr = new int[index+1];
		for (int i=0; i<betarr.length; i++) {
			betarr[i] = arr[i];
		}
		
		int sum =0;
		int sumzeroessofar =0;
		for (int i=0; i<betarr.length; i++) {
			if (ones[i] == '1') {
					// do we switch the '1' by turning this one into a '0'
					// and all previous '0's into '1's
				if (betarr[i] < sumzeroessofar) {
					sum += sumzeroessofar;
					sumzeroessofar = betarr[i];
				}
				else {
					sum += betarr[i];
				}
			}
			else {
				sumzeroessofar += betarr[i];
			}
		}
		
		System.out.println(sum);
	}
}
