
import java.util.*;
import java.io.*;

public class cowrun {

	// http://usaco.org/index.php?page=viewproblem2&cpid=264
	
	static ArrayList<Long> pos;
	static ArrayList<Long> neg;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cowrun.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cowrun.out"));

		int n = Integer.parseInt(in.readLine());
		pos = new ArrayList<>();
		neg = new ArrayList<>();
		for (int i=0; i<n; i++) {
			long c = Integer.parseInt(in.readLine());
			if (c>=0) pos.add(c);
			else neg.add(-1*c);
		}
				
		int p = pos.size(); 
		int m = neg.size();
			
		if (p == 0) {
			long curpos=0;
			long ans=0;
			for (int i=0; i<m; i++) {
				ans += (m-i)*(Math.abs(neg.get(i) - curpos));
				curpos = neg.get(i);
			}
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
		if (m == 0) {
			long curpos=0;
			long ans=0;
			for (int i=0; i<p; i++) {
				ans += (p-i)*(Math.abs(pos.get(i) - curpos));
				curpos = pos.get(i);
			}
			System.out.println(ans);
			out.println(ans);
			out.close();
			return;
		}
				
		Collections.sort(pos);
		Collections.sort(neg);
		for (int i=0; i<m; i++) neg.set(i, neg.get(i)*-1);
		
		long[][][] memo1 = new long[p][m][2];
			// pos, neg, last move
				// 0 = pos; 1 = neg;
		
		for (int i=0; i<p; i++) {
			for (int j=0; j<m; j++) {
				Arrays.fill(memo1[i][j],-1);
			}
		}
		
		long ans = dp(memo1, 0, 0, 0);
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
	
	public static long dp(long[][][] memo, int p, int m, int lastpos) {
		if (p >= pos.size()) {
			long res=0;
			long curpos = pos.get(pos.size()-1);
			for (int i=m; i<neg.size(); i++) {
				res += (neg.size()-i) * (Math.abs(neg.get(i) - curpos));
				curpos = neg.get(i);
			}
			return res;
		}
		if (m>= neg.size()) {
			long res=0;
			long curpos = neg.get(neg.size()-1);
			for (int i=p; i<pos.size(); i++) {
				res += (pos.size()-i) * (Math.abs(pos.get(i) - curpos));
				curpos = pos.get(i);
			}
			return res;
		}
		if (memo[p][m][lastpos] != -1) return memo[p][m][lastpos];
		
		long res=0;
		if (lastpos == 0) {
				// go to pos
			if (p-1>=0) res = dp(memo, p+1, m, 0) + Math.abs((pos.get(p) - pos.get(p-1)) * (pos.size()-p + neg.size()-m));
			else res = dp(memo, p+1, m, 0) + Math.abs(pos.get(p) * (pos.size()-p + neg.size()-m));
			
				// go to neg
			if (p-1>=0) res = Math.min(res, dp(memo, p, m+1, 1) + Math.abs((neg.get(m) - pos.get(p-1)) * (pos.size()-p + neg.size()-m)));
			else res = Math.min(res, dp(memo, p, m+1, 1) + Math.abs(neg.get(m) * (pos.size()-p + neg.size()-m)));
		}
		else {
				// go to neg
			if (m-1>=0) res = dp(memo, p, m+1, 1) + Math.abs((neg.get(m) - neg.get(m-1)) * (pos.size()-p + neg.size()-m));
			else res = dp(memo, p, m+1, 1) + Math.abs(neg.get(m) * (pos.size()-p + neg.size()-m));
			
				// go to pos
			if (m-1>=0) res = Math.min(res, dp(memo, p+1, m, 0) + Math.abs((pos.get(p) - neg.get(m-1)) * (pos.size()-p + neg.size()-m)));
			else res = Math.min(res, dp(memo, p+1, m, 0) + Math.abs(pos.get(p) * (pos.size()-p + neg.size()-m)));
		}
		return memo[p][m][lastpos] = res;
	}
}