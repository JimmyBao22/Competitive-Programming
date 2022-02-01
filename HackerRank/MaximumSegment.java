
import java.util.*;
import java.io.*;

public class MaximumSegment {

	// https://www.hackerrank.com/contests/ich-christmas-2020/challenges/c-maximum-segment
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MaximumSegment"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		int max=arr[0];
		int cur=arr[0];
		if (cur < 0) cur=0;
		for (int i=1; i<n; i++) {
			cur += arr[i];
			max = Math.max(max,cur);
			if (cur < 0) cur = 0;
		}
		
		int[] pref = new int[n];
		int[] suff=  new int[n];
		for (int i=0; i<n; i++) {
			if (i == 0) pref[0] = arr[0];
			else pref[i] = pref[i-1] + arr[i];
		}
		
		for (int i=n-1; i>=0; i--) {
			if (i == n-1) suff[i] = arr[i];
			else suff[i] = suff[i+1] + arr[i];
		}
		
		for (int i=1; i<n; i++) {
			pref[i] = Math.max(pref[i], pref[i-1]);
		}
		
		for (int i=n-2; i>=0; i--) {
			suff[i] = Math.max(suff[i], suff[i+1]);
		}
		
		int maxhere=0;
		for (int i=1; i<n; i++) {
			maxhere = Math.max(maxhere, pref[i-1] + suff[i]);
		}
		
		System.out.println(Math.max(max, maxhere));
	}
}