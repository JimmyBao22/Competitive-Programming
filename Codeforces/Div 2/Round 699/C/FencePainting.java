
import java.util.*;
import java.io.*;

public class FencePainting {

	// https://codeforces.com/contest/1481/problem/C
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("FencePainting"));

		int t = Integer.parseInt(in.readLine());
		while (t-->0) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int[] a = new int[n];
			int[] b = new int[n];
			int[] c =  new int[m];
			
			int[] moves = new int[m];
			ArrayList<Integer> extra = new ArrayList<>();
			HashMap<Integer, ArrayList<Integer>> fix = new HashMap<>();
				// val, indices
			
			HashMap<Integer, Integer> good = new HashMap<>();
			
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				a[i] = Integer.parseInt(st.nextToken());
			}
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<n; i++) {
				b[i] = Integer.parseInt(st.nextToken());
				if (a[i] != b[i]) {
					if (!fix.containsKey(b[i])) {
						fix.put(b[i], new ArrayList<>());
					}
					fix.get(b[i]).add(i);
				}
				else {
					good.put(b[i], i);
				}
			}
			st = new StringTokenizer(in.readLine());
			for (int i=0; i<m; i++) {
				c[i] = Integer.parseInt(st.nextToken());
			}
			
			for (int i=0; i<m; i++) {
				if (fix.containsKey(c[i])) {
					
					// set them all to paint this one, so that when c[i] paints it
						// it will paint over
					for (Integer x : extra) {
						moves[x] = fix.get(c[i]).get(fix.get(c[i]).size()-1);
					}
					extra.clear();
					
					moves[i] = fix.get(c[i]).get(fix.get(c[i]).size()-1);
					good.put(c[i], fix.get(c[i]).get(fix.get(c[i]).size()-1));
					fix.get(c[i]).remove(fix.get(c[i]).size()-1);
					if (fix.get(c[i]).size() == 0) {
						fix.remove(c[i]);
					}
				}
				else {
					if (good.containsKey(c[i])) {
						
						for (Integer x : extra) {
							moves[x] = good.get(c[i]);
						}
						extra.clear();
						
						moves[i] = good.get(c[i]);
					}
					else {
						extra.add(i);						
					}
				}
			}
			
			if (extra.size() > 0 || fix.size() > 0) {
				System.out.println("NO");
			}
			else {
				System.out.println("YES");
				StringBuilder s = new StringBuilder();
				for (int i=0; i<m; i++) {
					s.append(moves[i]+1);
					s.append(" ");
				}
				System.out.println(s);
			}
		}	
	}
}