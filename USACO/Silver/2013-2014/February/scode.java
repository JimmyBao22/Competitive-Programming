
import java.util.*;
import java.io.*;

public class scode {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=396
	
	static HashMap<String, Long> map = new HashMap<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("scode.in"));
		PrintWriter out = new PrintWriter(new FileWriter("scode.out"));

		String s = in.readLine();
		if (s.length()%2==0) {
			out.println(0);
			out.close();
			return;
		}
		
		long ans = dp(s)-1;		// -1 because can't start with s

		System.out.println(ans);
		out.println(ans);
		out.close();
	}
	
	public static long dp(String cur) {
		if (cur.length()%2==0) return 1;
		if (map.containsKey(cur)) return map.get(cur);
		
		long ans=1;
		int n = cur.length();
		int orig = (n + 1)/2;
		
		// cur.substring(0, orig) + cur.substring(orig)
		if (cur.substring(0,orig-1).equals(cur.substring(orig))) {
			ans += dp(cur.substring(0, orig));
		}
		if (cur.substring(1, orig).equals(cur.substring(orig))) {
			ans += dp(cur.substring(0, orig));
		}
		
		// cur.substring(0, orig-1) + cur.substring(orig-1)
		if (cur.substring(0, orig-1).equals(cur.substring(orig))) {
			ans += dp(cur.substring(orig-1));
		}
		if (cur.substring(0, orig-1).equals(cur.substring(orig-1, n-1))) {
			ans += dp(cur.substring(orig-1));
		}
		
		map.put(cur, ans);
		return ans;
	}
}