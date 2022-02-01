
import java.util.*;
import java.io.*;

public class ToyTrain {

	// https://codeforces.com/contest/1129/problem/A1
	// https://codeforces.com/contest/1129/problem/A2
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ToyTrain"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[][] arr = new int[n][2];
			// number of stations, furthest one
		for (int i=0; i<arr.length; i++) arr[i][1] = -1;
		
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken())-1;
			int two = Integer.parseInt(st.nextToken())-1;
			arr[one][0]++;
			
				// find closest one
			if (arr[one][1] == -1) {
				arr[one][1] = two;
			}
			else if (arr[one][1] > one) {
				if (two < arr[one][1] && two >= one) {
					arr[one][1] = two;
				}
			}
			else {
				if (two >= one || two < arr[one][1]) {
					arr[one][1] = two;
				}
			}
		}
		
		int[] time = new int[n];
		for (int j=0; j<n; j++) {
			if (arr[j][0] != 0) {
				time[j] = (arr[j][0]-1) * n;
				
				if (arr[j][1] >= j) {
					time[j] += arr[j][1] - j;
				}
				else {
					time[j] += n - j + arr[j][1];
				}
				
			}
		}
		
		int[] ans = new int[n];
		for (int i=0; i<n; i++) {
			int time_to_get_here=0;
			int max=0;
			for (int j=i; j<n; j++) {
				if (arr[j][0] != 0) max = Math.max(max, time_to_get_here + time[j]);
				time_to_get_here++;
			}
			for (int j=0; j<i; j++) {
				if (arr[j][0] != 0) max = Math.max(max, time_to_get_here + time[j]);
				time_to_get_here++;
			}
			ans[i] = max;
		}
		
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			s.append(ans[i] + " ");
		}
		System.out.print(s);
				
	}
}
