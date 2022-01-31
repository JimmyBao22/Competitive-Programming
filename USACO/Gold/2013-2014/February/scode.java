
import java.util.*;
import java.io.*;

public class scode {

	// http://usaco.org/index.php?page=viewproblem2&cpid=399
	
	static String x;
	static HashMap<String, Integer> memo;
	static int mod = 2014;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("scode.in"));
		PrintWriter out = new PrintWriter(new FileWriter("scode.out"));

		x = in.readLine();
		memo = new HashMap<>();
		
		int ans = dp(x)%mod;
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static int dp(String cur) {
		if (memo.containsKey(cur)) return memo.get(cur);
		
		int ans=0;
		int n = cur.length();
		for (int i=1; i<=(n-1)/2; i++) {
			String current = cur.substring(0,i);	// current part that has been added
			String rest = cur.substring(i);			// rest of the string
			
			if (current.equals(rest.substring(0, current.length()))) {
				ans += dp(rest);
				ans %= mod;
			}
			if (current.equals(rest.substring(rest.length()-current.length()))) {
				ans += dp(rest);
				ans %= mod;
			}
		}
		for (int i=1; i<=(n-1)/2; i++) {
			String current = cur.substring(cur.length()-i);	// current part that has been added
			String rest = cur.substring(0, cur.length()-i);			// rest of the string
			
			if (current.equals(rest.substring(0, current.length()))) {
				ans += dp(rest);
				ans %= mod;
			}
			if (current.equals(rest.substring(rest.length()-current.length()))) {
				ans += dp(rest);
				ans %= mod;
			}
		}
		
		if (!cur.equals(x)) ans++;		// start with cur
		ans %= mod;
		
		memo.put(cur, ans);
		
		return memo.get(cur);
	}
}

/*

		dp w/ memo
	hash map —> string to integer
	
		top going to bottom. Start with final, then go down.
			Only some transitions work!

			Ex. ABABA
				You can move to a suffix or prefix of the inputted string
					in certain scenarios
				

	solve (String in) {
		if (map.contains(in)) return map.get(in);
		calculate
			store in map and return
}

*/