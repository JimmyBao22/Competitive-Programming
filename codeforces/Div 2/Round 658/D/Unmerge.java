
import java.util.*;
import java.io.*;

public class Unmerge {

	// https://codeforces.com/contest/1382/problem/D
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Unmerge"));

		int t = Integer.parseInt(in.readLine());
		while (t --> 0) {
			int n = Integer.parseInt(in.readLine());
			StringTokenizer st = new StringTokenizer(in.readLine());
			int[] arr = new int[2*n];
			TreeSet<Integer> all = new TreeSet<>();
			for (int i=0; i<2*n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				all.add(i+1);
			}
			ArrayList<Integer> moves = new ArrayList<>();
			int pointer=2*n-1;
			while (all.size()>0) {
				int curmax=all.last();
				for (int i=pointer; i>=0; i--) {
					all.remove(arr[i]);
					if (arr[i] == curmax) {
						moves.add(pointer - i+1);
						pointer=i-1;
						break;
					}
				}
			}
			
			//Collections.sort(moves);
			int[] dp = new int[n+1];
			for (int i=0; i<moves.size(); i++) {
				for (int j= n; j>=moves.get(i); j--) {
					dp[j] += dp[j-moves.get(i)];
				}
				if (moves.get(i)<=n) dp[moves.get(i)]++;
				if (dp[n] > 0) break;
			}
			if (dp[n] > 0) {
				System.out.println("YES");
			}
			else System.out.println("NO");
		}
	}
}
