
import java.util.*;
import java.io.*;

public class haywire {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=281
	
	static int n;
	static int[][] friends;
	static int best;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("haywire.in"));
		PrintWriter out = new PrintWriter(new FileWriter("haywire.out"));

		n = Integer.parseInt(in.readLine());
		friends = new int[n][3];
		best = (int)(1e9);
		for (int i=0; i<n; i++ ) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			friends[i][0] = Integer.parseInt(st.nextToken())-1;
			friends[i][1] = Integer.parseInt(st.nextToken())-1;
			friends[i][2] = Integer.parseInt(st.nextToken())-1;
		}

		int[] arr = new int[n]; int[] pos = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = i;
			pos[i] = -1;
		}
		permutation(arr, 0, pos, 0, 0, 0);
		
		System.out.println(best);
		out.println(best);
		out.close();
	}
	
	public static void permutation(int[] arr, int i, int[] pos, int curval, int pendinglinks, int pendingval) {
		if (i == arr.length) {
			best = Math.min(best, curval);
			return;
		}
		if (curval + pendingval >= best) return;
		for (int j=i; j<arr.length; j++) {
			int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp; 
			pos[arr[i]] = i;
			int add=0;
			int newpendinglinks = pendinglinks + 3;		// 3 new possible links
			for (int k=0; k<3; k++) {
				if (pos[friends[arr[i]][k]] != -1) {
					add += Math.abs(pos[arr[i]] - pos[friends[arr[i]][k]]);
					newpendinglinks -= 2;			// get rid of links for both this and previous
				}
			}
			int newpendingval = pendingval - add;		// subtract those got rid of at this step
			newpendingval += pendinglinks;				// each link left add 1 dist
			permutation(arr, i+1, pos, curval + add, newpendinglinks, newpendingval);
			pos[arr[i]] = -1;
			temp = arr[i]; arr[i] = arr[j]; arr[j] = temp; 
		}
	}
}