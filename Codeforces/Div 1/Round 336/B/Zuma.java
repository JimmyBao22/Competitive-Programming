
import java.util.*;
import java.io.*;

public class Zuma {

	// https://codeforces.com/problemset/problem/607/B
	
	static int n, INF, arr[], memo[][][];
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Zuma"));

		int t = 1;
		while (t-- > 0) {
			n = Integer.parseInt(in.readLine());
			arr =new int[n];
			StringTokenizer st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				arr[i] = Integer.parseInt(st.nextToken());
			}
			INF = (int)(1e9);
			
			memo = new int[n][n][2];
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					Arrays.fill(memo[i][j], -1);
				}
				memo[i][i][0] = 0;
				memo[i][i][1] = 1;
			}
			
			int ans = dp(0, n-1, 1);
			
			System.out.println(ans);
		}		
	}
	
	public static int dp(int left, int right, int type) {
		if (left > right) return 0;
		if (memo[left][right][type] != -1) return memo[left][right][type];
		
		int ans = dp(left+1, right, 1);
		if (type == 1) ans++;
		
		for (int k=left+1; k<=right; ++k) {
			if (arr[left] == arr[k]) {
				if (type == 0) {
					ans = Math.min(ans, dp(left+1, k-1, 0) + dp(k+1, right, 1));
				}
				else {
					ans = Math.min(ans, 1 + dp(left+1, k-1, 0) + dp(k+1, right, 1));
				}
			}
		}
		
		return memo[left][right][type] = ans;
	}
}