
import java.util.*;
import java.io.*;

public class Antivirus {

	static long mod = (long)(1e9+7);
	static long p = 97;
	static int maxn = 1001;
	static long[] power;
	static long[][] pref;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("input-antivirus-12ec.txt"));
		PrintWriter out = new PrintWriter(new FileWriter("Antivirus.out"));

		pref = new long[4][maxn];
		power = new long[maxn];
		calc_power();
		
		int t = Integer.parseInt(in.readLine());
		for (int tt = 1; tt < t + 1; tt++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int[] n = new int[4];
			for (int i=0; i<4; i++) {
				n[i] = Integer.parseInt(st.nextToken());
			}
			int m = Integer.parseInt(in.readLine());
			
			String[] all = new String[4];
			all[0] = in.readLine();
			all[1] = in.readLine();
			all[2] = in.readLine();
			all[3] = in.readLine();

			prefHash(all[0], 0);
			prefHash(all[1], 1);
			prefHash(all[2], 2);
			prefHash(all[3], 3);
			
			HashMap<Long, ArrayList<Integer>> count = new HashMap<>();
			for (int i=0; i<4; i++) {
				for (int j=0; j+m-1<n[i]; j++) {
					long cur = SubstringHash(j, j+m-1, i);
					if (!count.containsKey(cur)) {
						count.put(cur, new ArrayList<>());
					}
					if (count.get(cur).size() == i) count.get(cur).add(j);
				}
			}
			
			long special = 0;
			for (Long k : count.keySet()) {
				if (count.get(k).size() == 4) {
					special = k;
					break;
				}
			}
			
			System.out.print("Case #" + tt + ": " );
			out.print("Case #" + tt + ": " );
			for (int j=0; j<4; j++) {
				System.out.print(count.get(special).get(j) + " ");
				out.print(count.get(special).get(j) + " ");
			}
			System.out.println();
			out.println();
		}
		out.close();
	}
	
	public static long SubstringHash(int i, int j, int index) {
	    if (i != 0) {
	        return (((pref[index][j] - (pref[index][i-1] * power[j - i + 1])%mod)%mod+mod)%mod);
	    }
	    else {
	        return pref[index][j];
	    }
	}
	
	public static void prefHash(String s, int index) {
	    if (s.length() > 0) pref[index][0] = s.charAt(0) - 'a';
	    for (int i=1; i<s.length(); i++) {
	        pref[index][i] = ((pref[index][i-1]*p)%mod + (s.charAt(i) - 'a'))%mod;
	    }
	}
	
	public static void calc_power() {
	    if (power.length > 0) power[0] = 1;
	    for (int i=1; i<maxn; i++) {
	        power[i] = power[i-1] * p;
	        power[i] %= mod;
	    }
	}
}