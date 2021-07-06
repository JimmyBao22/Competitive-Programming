
import java.util.*;
import java.io.*;

public class MrKitayutatheTreasureHunter {

	// https://codeforces.com/contest/505/problem/C
	
	static int n = 30001, nn, d, m = 300;
	static int[][] memo;
	static HashMap<Integer, Integer> pos = new HashMap<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("MrKitayutatheTreasureHunter"));

		int t = 1;
		while (t-- > 0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			nn = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			
			for (int i=0; i<nn; i++) {
				int a = Integer.parseInt(in.readLine());
				pos.put(a, pos.getOrDefault(a, 0)+1);
			}
			
			memo = new int[n][2*m+1];
			for (int i=0; i<n; i++) {
				Arrays.fill(memo[i], -1);
			}
			
			int ans = pos.getOrDefault(d, 0) + dp(d, d);
			System.out.println(ans);
		}
	}
	
	public static int dp(int index, int step) {
		if (index >= n) return 0;
		int sstep = step - d + m;
		if (memo[index][sstep] != -1) return memo[index][sstep];
		int ans=0;
		
		if (step == 1) {
			ans = Math.max(pos.getOrDefault(index+step, 0) + dp(index + step, step), 
					pos.getOrDefault(index+step+1, 0) + dp(index + step + 1, step + 1));
		}
		else {
			ans = Math.max(pos.getOrDefault(index+step, 0) + dp(index + step, step), 
					pos.getOrDefault(index+step+1, 0) + dp(index + step + 1, step + 1));
			ans = Math.max(ans, pos.getOrDefault(index+step-1, 0) + dp(index + step - 1, step - 1));
		}
		
		return memo[index][sstep] = ans;
	}
}