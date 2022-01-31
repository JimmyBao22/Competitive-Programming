
import java.util.*;
import java.io.*;

public class paint {

	// http://usaco.org/index.php?page=viewproblem2&cpid=226
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("paint.in"));
		PrintWriter out = new PrintWriter(new FileWriter("paint.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		TreeMap<Integer, Integer> map = new TreeMap<>();
		int curpos=0;
		for (int i=0; i<n; i++ ) {
			st = new StringTokenizer(in.readLine());
			int one = Integer.parseInt(st.nextToken());
			char c = st.nextToken().charAt(0);
			if (c == 'R') {
				map.put(curpos, map.getOrDefault(curpos, 0)+1);
				map.put(curpos+one, map.getOrDefault(curpos+one, 0)-1);
				curpos += one;
			}
			else {
				map.put(curpos-one, map.getOrDefault(curpos-one, 0)+1);
				map.put(curpos, map.getOrDefault(curpos, 0)-1);
				curpos -= one;
			}
		}
		
		int ans=0;
		int pos = map.firstKey();
		int curval = 0;
		for (Integer a : map.keySet()) {
			if (curval >= k) {
				ans += (a - pos);
			}
			curval += map.get(a);
			pos = a;
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();

	}
}