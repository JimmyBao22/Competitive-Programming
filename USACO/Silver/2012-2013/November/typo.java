
import java.util.*;
import java.io.*;

public class typo {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=188
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("typo.in"));
		PrintWriter out = new PrintWriter(new FileWriter("typo.out"));

		char[] arr = in.readLine().toCharArray();
		int n = arr.length;
		int close=0;
		int[] pref = new int[n];
		boolean found=false;
		for (int i=0; i<n; i++) {
			if (arr[i] == '(') {
				pref[i] += 1;
				if (i!=0) pref[i] += pref[i-1];
			}
			else {
				close++;
				pref[i] -= 1;
				if (i!=0) pref[i] += pref[i-1];
				if (pref[i] < 0) {
					found=true;
					break;
				}
			}
		}
		
		if (found) {
			System.out.println(close);
			out.println(close);
			out.close();
			return;
		}
		
		pref = new int[n];
		int open = 0;
		for (int i=n-1; i>=0; i--) {
			if (arr[i] == '(') {
				pref[i] -= 1;
				open++;
				if (i!=n-1) pref[i] += pref[i+1];
				if (pref[i] < 0) {
					found=true;
					break;
				}
			}
			else {
				pref[i] += 1;
				if (i!=n-1) pref[i] += pref[i+1];
			}
		}
		
		if (found) {
			System.out.println(open);
			out.println(open);
		} else {
			System.out.println(0);
			out.println(0);
		}
		out.close();
	}
}