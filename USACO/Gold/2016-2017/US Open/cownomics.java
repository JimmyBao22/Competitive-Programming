
import java.util.*;
import java.io.*;

public class cownomics {

	// http://usaco.org/index.php?page=viewproblem2&cpid=741
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cownomics.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cownomics.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		String[] good = new String[n];
		String[] bad = new String[n];
		for (int i=0; i<n; i++) {
			good[i] = in.readLine();
		}
		for (int i=0; i<n; i++) {
			bad[i] = in.readLine();
		}
		
		int low = 1;
		int high = m;
		while (low < high) {
			int middle = (low + high)/2;
			if (check2(middle, good, bad)) {
				high = middle;
			}
			else low = middle+1;
		}
		
		// both check and check2 work

		System.out.println(low);
		out.println(low);
		out.close();
	}
	
	public static boolean check(int num , char[][] good, char[][] bad) {
		HashMap<Character, Long> map = new HashMap<>();
		map.put('A', 1l);
		map.put('C', 2l);
		map.put('G', 3l);
		map.put('T', 4l);
		
		int n = good.length;
		int m = good[0].length;
		
		long[][] goodnum_5 = new long[n][m-num+1];
		long[][] badnum_5 = new long[n][m-num+1];
		long[][] goodnum_7 = new long[n][m-num+1];
		long[][] badnum_7 = new long[n][m-num+1];
		
		long mod = (long)(1e9+7);
		
		long[] pow7 = new long[num+1];
		long[] pow5 = new long[num+1];
		pow7[0]=1;
		pow5[0] = 1;
		for (int i=1; i<= num; i++) {
			pow7[i] = pow7[i-1] * 7 %mod;
			pow5[i] = pow5[i-1] * 5 %mod;
		}
		
		
		// sliding window + hashing
		for (int i=0; i<n; i++) {
			for (int j=0; j<num; j++) {
				goodnum_5[i][0] += (long) (map.get(good[i][j])* pow5[num-j-1]);
				goodnum_5[i][0]%=mod;
				goodnum_7[i][0] += (long) (map.get(good[i][j])* pow7[num-j-1]);
				goodnum_7[i][0]%=mod;
				badnum_5[i][0] += (long) (map.get(bad[i][j])* pow5[num-j-1]);
				badnum_5[i][0]%=mod;
				badnum_7[i][0] += (long) (map.get(bad[i][j])* pow7[num-j-1]);
				badnum_7[i][0]%=mod;
			}
		}
		
		for (int j=1; j<=m-num; j++) {
			for (int i=0; i<n; i++) {
				goodnum_5[i][j] = goodnum_5[i][j-1] - map.get(good[i][j-1])*pow5[num-1];
				goodnum_5[i][j] *= 5;
				goodnum_5[i][j] = (goodnum_5[i][j]%mod + mod)%mod;
				goodnum_5[i][j] += map.get(good[i][j+num-1]);
				goodnum_7[i][j] = goodnum_7[i][j-1] - map.get(good[i][j-1])*pow7[num-1];
				goodnum_7[i][j] *= 7;
				goodnum_7[i][j] = (goodnum_7[i][j]%mod + mod)%mod;
				goodnum_7[i][j] += map.get(good[i][j+num-1]);
				
				badnum_5[i][j] = badnum_5[i][j-1] - map.get(bad[i][j-1])*pow5[num-1];
				badnum_5[i][j] *= 5;
				badnum_5[i][j] = (badnum_5[i][j]%mod + mod)%mod;
				badnum_5[i][j] += map.get(bad[i][j+num-1]);
				badnum_7[i][j] = badnum_7[i][j-1] - map.get(bad[i][j-1])*pow7[num-1];
				badnum_7[i][j] *= 7;
				badnum_7[i][j] = (badnum_7[i][j]%mod + mod)%mod;
				badnum_7[i][j] += map.get(bad[i][j+num-1]);
				
			}
		}
		
		boolean allwork=false;
		for (int i=0; i<goodnum_5[0].length; i++) {
			boolean work = true;
			out: for (int j=0; j<n; j++) {
				for (int k=0; k<n; ++k) {
					if (goodnum_5[j][i] == badnum_5[k][i] && goodnum_7[j][i] == badnum_7[k][i]) {
						work = false;
						break out;
					}
				}
			}
			if (work) {
				allwork=true;
				break;
			}
		}
		return allwork;
	}
	
	public static boolean check2(int num , String[] good, String[] bad) {
		int n = good.length;
		int m = good[0].length();
		boolean allwork=false;
		for (int start=0; start<=m-num; start++) {
			boolean work=true;
			HashSet<String> goodset = new HashSet<>();
			for (int i=0; i<n; i++) {
				String cur = good[i].substring(start, start+num);
				goodset.add(cur);
			}
			for (int i=0; i<n; i++) {
				String cur = bad[i].substring(start, start+num);
				if (goodset.contains(cur)) {
					work=false;
					break;
				}
			}
			if (work) {
				allwork=true;
				break;
			}
		}
		return allwork;
	}
}