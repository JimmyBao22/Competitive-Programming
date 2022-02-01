
import java.util.*;
import java.io.*;

public class Searchlights {

	// https://codeforces.com/contest/1408/problem/D
	
	static int n,m;
	static int[][] arr, lights;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Searchlights"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		arr = new int[n][2];
		lights = new int[m][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		for (int i=0; i<m; i++) {
			st = new StringTokenizer(in.readLine());
			lights[i][0] = Integer.parseInt(st.nextToken());
			lights[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.parallelSort(lights, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if (a[0] == b[0]) {
					return a[1] - b[1];
				}
				return a[0] - b[0];
			}
		});
		
		int sum = (int)(1e7);
		int[] maxlight = new int[sum];
		int pointer=m-1;
		maxlight[sum-1] = -1;
		for (int i=sum-2; i>=0; i--) {
			maxlight[i] = maxlight[i+1];
			while (pointer-1>=0 && lights[pointer-1][0]==i) pointer--;
			if (pointer>=0 && lights[pointer][0] == i) {
				maxlight[i] = Math.max(maxlight[i], lights[pointer][1]);
				pointer--;
			}
		}
		
		for (int right=0; right<sum; right++) {
			int rightside = arr[0][0] + right;
			int max = Math.max(maxlight[rightside] - arr[0][1] + 1,0);
			for (int i=1; i<n; i++) {
				rightside = arr[i][0] + right;
				max = Math.max(max, maxlight[rightside]-arr[i][1]+1);
			}
			sum = Math.min(sum, max+right);
		}
		
		System.out.println(sum);
//		int brute = brute();
//		System.out.println(brute);
//		if (brute != sum ) {
//			System.out.println("NO");
//		}
	}
	
	public static int brute() {
		int sum = (int)(1e6+5);
		o: for (int right=0; right<sum; right++) {
			int max=0;
			for (int i=0; i<n; i++) {
				for (int j=0; j<m; j++) {
					if (lights[j][0] >= arr[i][0]+right &&
							lights[j][1] >= arr[i][1]) {
						max = Math.max(max, lights[j][1] - arr[i][1]+1);
						if (max+right >= sum) {
							continue o;
						}
					}
				}
			}
			sum = Math.min(sum, max+right);
		}
		
		return sum;
	}
}