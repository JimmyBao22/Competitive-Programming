
import java.util.*;
import java.io.*;

public class censor {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=526
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=529
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("censor.in"));
		PrintWriter out = new PrintWriter(new FileWriter("censor.out"));

		char[] s = in.readLine().toCharArray();
		char[] t = in.readLine().toCharArray();
		long mod = (long)(1e9+7);
		long bad = 0;
		long cur = 0;
		
		for (int i=0; i<t.length; i++ ) {
			bad = 29*bad + (t[i]-'a'+1);
			bad %= mod;
		}
		
		long pow = pow(29, t.length-1, mod);
		boolean[] used = new boolean[s.length];
		ArrayList<Integer> all = new ArrayList<>();
			// indices
		for (int i=0; i<s.length; i++) {
			if (used[i]) continue;
			if (all.size()==0 || all.get(all.size()-1) < i) all.add(i);
			if (all.size() <= t.length) {
				// continue increasing cur
				cur = cur*29 + (s[i]-'a'+1);
				cur %= mod;
			}
			else {
				// subtract out previous, add in new
				cur -= pow*(s[all.get(all.size()-t.length-1)]-'a'+1);
				cur *= 29;
				cur += (s[i] - 'a' + 1);
				cur %= mod;
				if (cur<0) cur += mod;
			}
			if (all.size()>=t.length && cur == bad) {		// found same
				for (int j=0; j<t.length; j++) {		// remove
					used[all.get(all.size()-1)] = true;
					all.remove(all.size()-1);
				}
				cur=0;
				if (all.size()==0) {

				}
				else {
					i = all.get(Math.max(all.size()-t.length,0));	// backtrack
					int count=0;
					for (; count<t.length && i<=all.get(all.size()-1); i++) {
						if (used[i]) continue;
						cur = 29*cur + (s[i]-'a'+1);
						cur%=mod;
						count++;
					}
					i--;
				}
			}
		}	
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<all.size(); i++) {
			sb.append(s[all.get(i)]);
		}
		System.out.println(sb);
		out.println(sb);
		out.close();

	}

	static long pow(long a, long b, long m) {
        // a^b mod m
    	long ans=1;
    	while (b >0) {
    		if (b%2 == 1) {
    			ans *= a%m;
    			ans %= m;
    		}
    		a *= a %m;
    		a%=m;
    		b >>=1;
    	}
    	return ans;
    }	
}