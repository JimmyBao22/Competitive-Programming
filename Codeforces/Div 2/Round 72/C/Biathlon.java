
import java.util.*;
import java.io.*;

public class Biathlon {

	// https://codeforces.com/contest/84/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Biathlon"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];
			// center, radius
		HashMap<Integer, Integer> x = new HashMap<>();
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			x.put(arr[i][0], i);
		}
		
		// sort based on x
		Arrays.sort(arr, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		
		int m = Integer.parseInt(in.readLine());
		int[][] shots = new int[m][2];
		for (int i=0; i<m; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			shots[i][0] = Integer.parseInt(st.nextToken());
			shots[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int[] ans = new int[n];
		Arrays.fill(ans, -1);
		
		for (int i=0; i<m; i++) {
			int low = 0;
			int high = n-1;
			// search for the right target
			while (low < high) {
				int middle = low + (high - low)/2;
				
				int mag = (arr[middle][0] - shots[i][0])*(arr[middle][0] - shots[i][0]) + (shots[i][1])*(shots[i][1]);
				if (mag <= arr[middle][1]*arr[middle][1]) {
					// x.get(arr[middle][0])
					if (ans[x.get(arr[middle][0])] == -1) {
						ans[x.get(arr[middle][0])]=i+1;
					}
					if (shots[i][1] == 0) {
						// it is on the x axis, meaning it can have multiple targets
						
						if (middle != 0) {
							middle--;
							mag = (arr[middle][0] - shots[i][0])*(arr[middle][0] - shots[i][0]) + (shots[i][1])*(shots[i][1]);
							if (mag <= arr[middle][1]*arr[middle][1]) {
								if (ans[x.get(arr[middle][0])] == -1) {
									ans[x.get(arr[middle][0])]=i+1;
								}
							}
							middle++;
						}
						if (middle != n-1) {
							middle++;
							mag = (arr[middle][0] - shots[i][0])*(arr[middle][0] - shots[i][0]) + (shots[i][1])*(shots[i][1]);
							if (mag <= arr[middle][1]*arr[middle][1]) {
								if (ans[x.get(arr[middle][0])] == -1) {
									ans[x.get(arr[middle][0])]=i+1;
								}
							}
							middle--;
						}
					}
					break;
				}
				else if (arr[middle][0] - shots[i][0] > 0){
					// shots is to the left
					high = middle-1;
				}
				else low = middle+1;
			}
			if (low == high && ans[x.get(arr[low][0])] == -1) {
				int middle = low;
				int mag = (arr[middle][0] - shots[i][0])*(arr[middle][0] - shots[i][0]) + (shots[i][1])*(shots[i][1]);
				if (mag <= arr[middle][1]*arr[middle][1]) {
					if (ans[x.get(arr[middle][0])] == -1) {
						ans[x.get(arr[middle][0])]=i+1;
					}
				}
			}
		}
		int count=0;
		for (int i=0; i<n; i++) {
			if (ans[i] != -1) count++;
			
		}
		System.out.println(count);
		for (int i=0; i<n; i++) System.out.print(ans[i] + " ");
	}
}
