
import java.util.*;
import java.io.*;

public class geteven {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=546
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("geteven.in"));
		PrintWriter out = new PrintWriter(new FileWriter("geteven.out"));

		int n = Integer.parseInt(in.readLine());
		HashMap<Character, HashMap<Integer, Long>> map = new HashMap<>();
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			Character one = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken()) % 2;
			if (a<0) a+=2;
			if (map.containsKey(one)) {
				map.get(one).put(a, map.get(one).getOrDefault(a, 0l)+1);
			}
			else {
				HashMap<Integer, Long> c = new HashMap<Integer, Long>();
				c.put(a,1l);
				map.put(one, c);
			}
		}

		long ans=0;
		for (Integer b : map.get('B').keySet()) {
			for (Integer e : map.get('E').keySet()) {
				for (Integer s : map.get('S').keySet()) {
					for (Integer i : map.get('I').keySet()) {
						for (Integer g : map.get('G').keySet()) {
							for (Integer o : map.get('O').keySet()) {
								for (Integer m : map.get('M').keySet()) {
									long bval = map.get('B').get(b);
									long eval = map.get('E').get(e);
									long sval = map.get('S').get(s);
									long ival = map.get('I').get(i);
									long gval = map.get('G').get(g);
									long oval = map.get('O').get(o);
									long mval = map.get('M').get(m);
									long bessie = b+e*2+s*2+i;
									bessie %= 2;
									long goes = g+o+e+s;
									goes %= 2;
									long moo = m+o*2;
									moo%=2;
									long all = bessie*goes*moo;
									if (all%2 == 0) {
										ans += bval*eval*sval*ival*gval*oval*mval;
									}
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println(ans);
		out.println(ans);
		out.close();
	}
}