
import java.util.*;
import java.io.*;

public class ModernArt3 {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1114
	
	static int n, INF = (int)(1e9);
	static int[] arr;
	static int[][][] memo;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("ModernArt3"));

		n = Integer.parseInt(in.readLine());
		arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		memo = new int[n][n][2];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				Arrays.fill(memo[i][j], -1);
			}
		}
		
		int ans = dp(0, n-1, 1);
		
		System.out.println(ans);
	}
	
	public static int dp(int left, int right, int type) {
		if (left > right) return 0;
		if (memo[left][right][type] != -1) return memo[left][right][type];
		int ans=INF;
		
		if (type == 0) {
			for (int j=left; j<=right; j++) {
				if (arr[j] == arr[left]) {
						// draw line from arr[left] to arr[j]
					ans = Math.min(ans, dp(left, j-1, 0) + dp(j+1, right, 1));
				}
			}
		}
		else {
			for (int j=left; j<=right; j++) {
				if (arr[j] == arr[left]) {
					// draw line from arr[left] to arr[j]
					ans = Math.min(ans, 1 + dp(left, j-1, 0) + dp(j+1, right, 1));
				}
			}
		}
		
		return memo[left][right][type] = ans;
	}
}

/*

	previously was trying to only do memo[left][right]
		the problem with this is, if you have something like 1...1..1, and you paint the left to right
			then the middle 1 next time could be ignored (aka painted with cost 0).

	 memo[left][right][type]
		
		means we are currently looking at the subset from left...right, where if type=0, that means that
			we just painted color arr[left] from left...right, and therefore anything in the middle 
				with color arr[left] we can take for free
				
			If type = 1, that means we have not painted anything yet, so anything we paint will cost something.
			
	two cases:
		if type = 0, then loop over j from left...right. 
			if (arr[j] == arr[left]), then you can take arr[j] for free
				ans = min(ans, dp(left, j-1, 0) + dp(j+1, right, 1))
				
					dp(left, j-1, 0) means that from the range left to j-1, you can still take anything
						with type arr[left] for free
						
					dp(j+1, right, 1) means that from j+1 to right, you are going to start a new section
		
		if type == 1, then loop over j from left...right.
			if (arr[j] == arr[left]), then you can paint color arr[left] from left...j
				ans = min(ans, 1 + dp(left, j-1, 0) + dp(j+1, right, 1));
				
					1 + dp(left, j-1, 0) means that you're painting arr[left] from left...j, so that costs 1,
						and now anything inside from left to j-1 with type arr[left] can be free
					
					dp(j+1, right, 1) means that from j+1 to right, you are going to start a new section

*/