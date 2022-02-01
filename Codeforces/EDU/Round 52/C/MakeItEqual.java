
import java.util.*;
import java.io.*;

public class MakeItEqual {

	// https://codeforces.com/contest/1065/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MakeItEqual"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		long k = Long.parseLong(st.nextToken());
		
		int[] towers = new int[n];
		long[] heightcount = new long[(int)2e5+1000];
		int maxheight = 0;
		int minheight = Integer.MAX_VALUE;
		st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			towers[i] = Integer.parseInt(st.nextToken());
			maxheight = Math.max(maxheight, towers[i]);
			minheight = Math.min(minheight, towers[i]);
			heightcount[1]++;
			heightcount[towers[i]+1]--;
		}
		for (int i=2; i<maxheight+2; i++) {
			heightcount[i] += heightcount[i-1];
		}
		
		long curcost = 0;
		int curindex = maxheight;
		int count=0;
		while (curindex > minheight) {
			if (heightcount[curindex] + curcost <= k) {
				curcost += heightcount[curindex];
			}
			else {
				count++;
				curcost = heightcount[curindex];
			}
			curindex--;
		}
		if (curcost != 0) count++;
		
		System.out.println(count);
	}
}
