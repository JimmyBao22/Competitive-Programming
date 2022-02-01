
import java.util.*;
import java.io.*;

public class BFSDFS {

	// https://csacademy.com/contest/archive/task/bfs-dfs
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ABKnapsack"));

		int n = Integer.parseInt(in.readLine());
		int[] bfsin = new int[n];
		int[] dfsin = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		int anscount=0;
		StringBuilder s = new StringBuilder();
		for (int i=0; i<n; i++) {
			bfsin[i] = Integer.parseInt(st.nextToken())-1;
		}
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			dfsin[i] = Integer.parseInt(st.nextToken())-1;
			if (i != 0) {
				s.append(dfsin[i-1]+1 + " " + (dfsin[i]+1) + "\n");
				anscount++;
			}
		}
		
		for (int i=1; i<n; i++) {
			if (bfsin[i] == dfsin[1]) continue;
			s.append(bfsin[0]+1 + " " + (bfsin[i] + 1) + "\n");
			anscount++;
		}
		
		if (bfsin[0] != dfsin[0] || (n > 1 && bfsin[1] != dfsin[1])) {
			System.out.println(-1);
			return;
		}

		System.out.println(anscount);
		System.out.print(s);
	}
}