
import java.util.*;
import java.io.*;

public class UdderedbutnotHerd {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1089
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("UdderedbutnotHerd"));

		String s = in.readLine();
		int n = s.length();
		
		int[][] precomp = new int[26][26];
		int[] count = new int[26];
		ArrayList<Integer> allchars = new ArrayList<>();
		for (int i=1; i<n; i++) {
			precomp[s.charAt(i) - 'a'][s.charAt(i-1) - 'a']++;
				// i comes before i-1
			
			count[s.charAt(i) - 'a']++;
		}
		for (int i=0; i<26; i++) {
			if (count[i] > 0) allchars.add(i);
		}
		
		int m = allchars.size();
		long[] dp = new long[(1 << m)];
		Arrays.fill(dp, (long)(1e9));
		dp[0] = 0;
		for (int i=1; i<(1 << m); i++) {
			ArrayList<Integer> indices = new ArrayList<>();
			for (int j=0; j<m; j++) {
				if (((i >> j)&1) == 1) {
					indices.add(j);
				}
			}
			for (int j=0; j<indices.size(); j++) {
				// put character j after character k
				int prec = 0;
				for (int k=0; k<indices.size(); k++) {
						
						// check where character k is after character j. If k is after j, but here k
							// comes before j in the alphabet, you have to add because you have to start 
							// new alphabet
					prec += precomp[allchars.get(indices.get(k))][allchars.get(indices.get(j))];
				}
				dp[i] = Math.min(dp[i], dp[i - (1 << indices.get(j))] + prec);
			}
		}
		
		System.out.println(dp[(1 << m)-1] + 1);
	}
}