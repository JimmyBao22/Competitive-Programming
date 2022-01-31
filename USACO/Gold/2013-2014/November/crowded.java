
import java.util.*;
import java.io.*;

public class crowded {

	// http://usaco.org/index.php?page=viewproblem2&cpid=344
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("crowded.in"));
		PrintWriter out = new PrintWriter(new FileWriter("crowded.out"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		Arrays.parallelSort(arr, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0]-b[0];
			}
		});
		
		if (n==1 || n==2) {
			System.out.println(0);
			out.println(0);
			out.close();
			return;
		}
		
		int lfirst=0;
		int rsec=2;
		TreeMap<Integer, Integer> left = new TreeMap<>();
		TreeMap<Integer, Integer> right = new TreeMap<>();
		right.put(arr[1][1], 1);
		int ans=0;
		for (int i=1; i<n-1; i++) {
			left.put(arr[i-1][1], left.getOrDefault(arr[i-1][1], 0)+1);
			while (lfirst<i && Math.abs(arr[lfirst][0]-arr[i][0])>d) {
				left.put(arr[lfirst][1], left.get(arr[lfirst][1])-1);
				if (left.get(arr[lfirst][1]) == 0) left.remove(arr[lfirst][1]);
				lfirst++;
			}
			
			right.put(arr[i][1], right.get(arr[i][1])-1);
			if (right.get(arr[i][1]) == 0) right.remove(arr[i][1]);
			while (rsec<n && Math.abs(arr[rsec][0]-arr[i][0])<=d) {
				right.put(arr[rsec][1], right.getOrDefault(arr[rsec][1], 0)+1);
				rsec++;
			}
			
			if (left.lastKey() >= 2*arr[i][1] && right.lastKey()>=2*arr[i][1]) {
				ans++;
			}	
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}