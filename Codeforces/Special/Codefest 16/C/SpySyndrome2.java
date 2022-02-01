
import java.util.*;
import java.io.*;

public class SpySyndrome2 {

	// https://codeforces.com/contest/633/problem/C
	
	static int n, m;
	static A[] given;
	static HashSet<String> visited = new HashSet<>();
	static boolean works;
	static ArrayList<Integer> ans = new ArrayList<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("SpySyndrome2"));

		n = Integer.parseInt(in.readLine());
		String orig = in.readLine();
		m = Integer.parseInt(in.readLine());
		given = new A[m];
		for (int i=0; i<m; ++i) {
			given[i] = new A(in.readLine());
		}
		works = false;
		
		dp(orig);
		
		StringBuilder s = new StringBuilder();
		for (int i=ans.size()-1; i>=0; i--) {
			s.append(given[ans.get(i)].str);
			s.append(" ");
		}
		System.out.println(s);
	}
	
	public static void dp(String cur) {
		if (visited.contains(cur)) return;
		if (cur.equals("")) {
			works=true; 
			return;
		}
		for (int i=0; i<m; i++) {
			if (cur.length() >= given[i].l && given[i].modified.equals(cur.substring(0, given[i].l))) {
				dp(cur.substring(given[i].l));
				if (works) {
					ans.add(i);
					return;
				}
			}
		}
	}
	
	static class A {
		String str;
		String modified;
		int l;
		A (String a) {
			str = a;
			char[] c = str.toCharArray();
			l = c.length;
			for (int i=0; i<(l+1)/2; i++) {
				char temp = c[i];
				c[i] = c[l - i - 1];
				c[l - i - 1] = temp;
			}
			modified = new String(c);
			modified = modified.toLowerCase();
		}
	}
}