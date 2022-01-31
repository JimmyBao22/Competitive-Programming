
import java.util.*;
import java.io.*;

public class squares {

	// http://usaco.org/index.php?page=viewproblem2&cpid=227
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("squares.in"));
		PrintWriter out = new PrintWriter(new FileWriter("squares.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken())/2;
		
		int[][] arr = new int[n][2];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.parallelSort(arr, new Comparator<int[]> () {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		
		TreeMap<Integer, HashSet<Integer>> map = new TreeMap<>();
			// y, x
		
		long ans=0;
		int p=0;
		boolean found=false;
		o: for (int i=0; i<n; i++) {
			for (; p<n; p++) {
				if (arr[p][0] - arr[i][0] >= 2*k) break;
				if (map.containsKey(arr[p][1])) {
					map.get(arr[p][1]).add(arr[p][0]);
				}
				else {
					HashSet<Integer> c = new HashSet<Integer>();
					c.add(arr[p][0]);
					map.put(arr[p][1], c);
				}
			}
			map.get(arr[i][1]).remove(arr[i][0]);
			if (map.get(arr[i][1]).size()==0) map.remove(arr[i][1]);
			
			int val = arr[i][1];
			while (true) {
				Integer key = map.ceilingKey(val);
				if (key == null) break;
				if (Math.abs(key - arr[i][1]) < 2*k) {
					if (found || map.get(key).size()>1) {
								// if you already found one, or there are multiple possibilities
						ans = -1;
						break o;
					}
					for (Integer a : map.get(key)) {
						found=true;
						ans = Area(k, (long)a, (long)key, (long)arr[i][0], (long)arr[i][1]);						
					}
				}
				else break;
				val = key+1;
			}
			val = arr[i][1];
			while (true) {
				Integer key = map.floorKey(val);
				if (key == null) break;
				if (Math.abs(key - arr[i][1]) < 2*k) {
					if (found || map.get(key).size()>1) {
						ans = -1;
						break o;
					}
					for (Integer a : map.get(key)) {
						found=true;
						ans = Area(k, (long)a, (long)key, (long)arr[i][0], (long)arr[i][1]);						
					}
				}
				else break;
				val = key-1;
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long Area(long k, long a, long b, long c, long d) {
		// (a, b)
			// (a-k, b-k) to (a+k, b+k)
		// (c, d)
			// (c-k, d-k) to (c+k, d+k)
		return (Math.min(a+k, c+k) - Math.max(a-k, c-k)) * (Math.min(b+k, d+k) - Math.max(b-k, d-k));
	}
}