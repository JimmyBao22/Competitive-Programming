
import java.util.*;
import java.io.*;

public class split {

	// http://usaco.org/index.php?page=viewproblem2&cpid=645
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("split.in"));
		PrintWriter out = new PrintWriter(new FileWriter("split.out"));
		
		int n = Integer.parseInt(in.readLine());
		long[][] arrx = new long[n][2];
		long[][] arry = new long[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arry[i][0] = arrx[i][0] = Integer.parseInt(st.nextToken());
			arry[i][1] = arrx[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.parallelSort(arrx, new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				return Long.compare(a[0], b[0]);
			}
		});
		Arrays.parallelSort(arry, new Comparator<long[]>() {
			public int compare(long[] a, long[] b) {
				return Long.compare(a[1],b[1]);
			}
		});
		
		long anshere = Math.min(find(arrx), find(arry));
		long total = Math.abs(arrx[n-1][0] - arrx[0][0]) * Math.abs(arry[n-1][1] - arry[0][1]);
		
		System.out.println(total-anshere);
		out.println(total-anshere);
		out.close();

	}
	
	public static long find(long[][] arr) {
		TreeMap<Long, Integer> firstxmap = new TreeMap<>();
		TreeMap<Long, Integer> firstymap = new TreeMap<>();
		TreeMap<Long, Integer> secondxmap = new TreeMap<>();
		TreeMap<Long, Integer> secondymap = new TreeMap<>();
		firstxmap.put(arr[0][0], 1);
		firstymap.put(arr[0][1], 1);
		for (int i=1; i<arr.length; i++) {
			secondxmap.put(arr[i][0], secondxmap.getOrDefault(arr[i][0], 0)+1);
			secondymap.put(arr[i][1], secondymap.getOrDefault(arr[i][1], 0)+1);
		}
		
		long min=Math.abs(secondxmap.lastKey() - secondxmap.firstKey()) * Math.abs(secondymap.lastKey() - secondymap.firstKey());
		for (int i=1; i<arr.length-1; i++) {
			firstxmap.put(arr[i][0], firstxmap.getOrDefault(arr[i][0], 0)+1);
			firstymap.put(arr[i][1], firstymap.getOrDefault(arr[i][1], 0)+1);
			secondxmap.put(arr[i][0], secondxmap.get(arr[i][0])-1);
			if (secondxmap.get(arr[i][0]) == 0) secondxmap.remove(arr[i][0]);
			secondymap.put(arr[i][1], secondymap.get(arr[i][1])-1);
			if (secondymap.get(arr[i][1]) == 0) secondymap.remove(arr[i][1]);
			
			long cur = Math.abs(firstxmap.lastKey() - firstxmap.firstKey()) * Math.abs(firstymap.lastKey() - firstymap.firstKey())
					+ Math.abs(secondxmap.lastKey() - secondxmap.firstKey()) * Math.abs(secondymap.lastKey() - secondymap.firstKey());
			min = Math.min(min, cur);
		}
		return min;
	}
}