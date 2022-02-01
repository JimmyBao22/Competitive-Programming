
import java.util.*;
import java.io.*;

public class IntersectingIntervals {

	// https://www.hackerrank.com/contests/potw-s3/challenges/intersecting-intervals
	// Problem Statement: https://pastebin.com/F1S9PuRP
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("IntersectingIntervals"));

		int n = Integer.parseInt(in.readLine());
		int m = Integer.parseInt(in.readLine());
		int[][] a = new int[n][2];
		int[][] b = new int[m][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			a[i][0] = Integer.parseInt(st.nextToken());
			a[i][1] = Integer.parseInt(st.nextToken());
		}
		for (int i=0; i<m; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			b[i][0] = Integer.parseInt(st.nextToken());
			b[i][1] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(a, new Comparator<int[]> () {
			public int compare(int[] a, int[] b) {
				return a[0]-b[0];
			}
		});
		Arrays.sort(b, new Comparator<int[]> () {
			public int compare(int[] a, int[] b) {
				return a[0]-b[0];
			}
		});
		int apoint=0;
		int bpoint=0;
		ArrayList<int[]> intersections = new ArrayList<>();
		while (apoint<n && bpoint<m) {
			int[] cura = a[apoint];
			int[] curb = b[bpoint];
			if (cura[1]<curb[0]) {
				apoint++;
				continue;
			}
			if (cura[0] > curb[1]) {
				bpoint++;
				continue;
			}
			if (cura[0] <= curb[0] && curb[0] <= cura[1]) {
				if (curb[1] > cura[1]) {
					intersections.add(new int[] {curb[0], cura[1]});
					apoint++;
					continue;
				}
				else {
					intersections.add(curb);
					bpoint++;
					continue;
				}
			}
			if (curb[0] <= cura[0] && cura[0] <= curb[1]) {
				if (cura[1] > curb[1]) {
					intersections.add(new int[] {cura[0], curb[1]});
					bpoint++;
					continue;
				}
				else {
					intersections.add(cura);
					apoint++;
					continue;
				}
			}
		}
		
		for (int i=0; i<intersections.size(); i++) {
			System.out.println(intersections.get(i)[0] + " " + intersections.get(i)[1]);
		}
		
	}
}