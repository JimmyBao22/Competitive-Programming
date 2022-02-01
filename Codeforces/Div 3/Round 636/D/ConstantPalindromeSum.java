
import java.util.*;
import java.io.*;

public class ConstantPalindromeSum {
	
	// https://codeforces.com/contest/1343/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new FileReader("ConstantPalindromeSum"));

		int t = Integer.parseInt(in.readLine());
		for (int j = 0;j < t; j++ ) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());  
			int k = Integer.parseInt(st.nextToken());  
			int[] a  =new int[n];
			st = new StringTokenizer(in.readLine());
			for (int i = 0; i < n; i++ ) {
				a[i] = Integer.parseInt(st.nextToken());  
			}
			
			int[] pref = new int[2*k+2];
			HashMap<Integer, Integer> zeroes = new HashMap<>();		
				// {a,b} = b pairs have sum a already
			
			for (int i = 0; i < n/2; i++) {
				int min = Math.min(a[i], a[n-1-i]);
				int max = Math.max(a[i], a[n-1-i]);
				
					// number of #'s where you only need to change 1 of the 2 elements
				pref[min+1]++;
				pref[max+k+1]--;
				//pref[a[i] + a[n-1-i]]--;
				zeroes.put(a[i] + a[n-1-i], zeroes.getOrDefault(a[i] + a[n-1-i], 0)+1);
			}
			int ans = Integer.MAX_VALUE;
			for (int i = 2; i < pref.length; i++) {
				pref[i] += pref[i-1];
				int change1 = pref[i];
				int change0 = zeroes.getOrDefault(i, 0);
				change1 -= change0;
				int change2 = n/2 - change1 - change0;
				int curr = change1 + change2*2;
				ans = Math.min(ans, curr);
			}
			System.out.println(ans);
		}
	}
}
