
import java.util.*;
import java.io.*;

public class NoTimetoPaint {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1087
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("NoTimetoPaint.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("NoTimetoPaint.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		char[] arr = in.readLine().toCharArray();
		int[][] ranges = new int[q][2];
		for (int i=0; i<q; i++) {
			st = new StringTokenizer(in.readLine());
			ranges[i][0] = Integer.parseInt(st.nextToken())-1;
			ranges[i][1] = Integer.parseInt(st.nextToken())-1;
		}
		
		TreeSet<Integer> allletters = new TreeSet<>();
		for (int i=0; i<n; i++) {
			allletters.add(arr[i] - 'A');
		}
		
		int[] lefttoright = new int[n];
		int[] righttoleft = new int[n];
		
		for (Integer a : allletters) {
			int start = -1;
			// i = 0
			if (arr[0] - 'A' == a) {
				start = 0;
			}
			for (int i=1; i<n; i++) {
				if (arr[i] - 'A' == a) {
					if (start == -1) start = i;
				}
				else if (arr[i] - 'A' > a) {
					// continue whatever you were doing before
				}
				else {
					if (start != -1) {
						lefttoright[start]++;
					}
					start = -1;
				}
			}
			if (start != -1) {
				lefttoright[start]++;
			} 
		}
		
		for (Integer a : allletters) {
			int start = -1;
			// i = n-1
			if (arr[n-1] - 'A' == a) {
				start = n-1;
			}
			for (int i=n-2; i>=0; i--) {
				if (arr[i] - 'A' == a) {
					if (start == -1) start = i;
				}
				else if (arr[i] - 'A' > a) {
					// continue whatever you were doing before
				}
				else {
					if (start != -1) {
						righttoleft[start]++;
					}
					start = -1;
				}
			}
			if (start != -1) {
				righttoleft[start]++;
			} 
		}
		
		for (int i=1; i<n; i++) lefttoright[i] += lefttoright[i-1];
		for (int i=n-2; i>=0; i--) righttoleft[i] += righttoleft[i+1];
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<q; i++ ) {
			int ans = 0;
			if (ranges[i][0] != 0) ans += lefttoright[ranges[i][0]-1];
			if (ranges[i][1] != n-1) ans += righttoleft[ranges[i][1]+1];
			sb.append(ans);
			if (i != q-1) sb.append("\n");
		}
		
		System.out.println(sb);
		//		out.println();
		//		out.close();
	}

}
