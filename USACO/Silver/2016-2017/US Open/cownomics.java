
import java.util.*;
import java.io.*;

public class cownomics {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=739
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cownomics.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cownomics.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		char[][] spotty = new char[n][];
		char[][] plain = new char[n][];
		for (int i=0; i<n; i++) {
			spotty[i] = in.readLine().toCharArray();
		}
		for (int i=0; i<n; i++) {
			plain[i] = in.readLine().toCharArray();
		}
		
		HashMap<Character, Integer> map = new HashMap<>();
		map.put('A', 1);
		map.put('C', 2);
		map.put('G', 3);
		map.put('T', 4);
		int count=0;
		for (int a=0; a<m; a++) {
			for (int b=a+1; b<m; b++) {
				for (int c=b+1; c<m; c++) {
					HashSet<Integer> set = new HashSet<>();
					for (int i=0; i<n; i++) {
						set.add(map.get(spotty[i][a]) * 100 + map.get(spotty[i][b]) * 10 + map.get(spotty[i][c]));
					}
					
					boolean works=true;
					for (int i=0; i<n; i++) {
						int cur = map.get(plain[i][a]) * 100 + map.get(plain[i][b]) * 10 + map.get(plain[i][c]);
						if (set.contains(cur)) {
							works=false;
							break;
						}
					}
					
					if (works) count++;
				}
			}
		}
		
		System.out.println(count);
		out.println(count);
		out.close();

	}

}

/*
 		// full brute force n^2 -- tle
			boolean works=true;
			o: for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					if (spotty[i][a] == plain[j][a] && spotty[i][b] == plain[j][b] && spotty[i][c] == plain[j][c]) {
						works=false;
						break o;
					}
				}
			}
			if (works) count++;
 */


/*

		3 8
		AATCCCAT
		GATTGCAA
		GGTCGCAA
		ACTCCCAG
		ACTCGCAT
		ACTTCCAT
		01234567
		
		
	Every group of three letters from the top, cannot be seen in the bottom
	
	For example, choosing 0,1,2 works because u have
		top:
		AAT
		GAT
		GGT
		bottom:
		ACT
		ACT
		ACT
			and none of these appear in the bottom (pos 0,1,2) 
			
	Though, for positions 0,2,3
		top:
		ATC
		GTT
		GTC
		bottom:
		ATC
		ATC
		ATT
			The bottom contains ATC, so this does not work		
		
*/