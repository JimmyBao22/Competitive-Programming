
import java.util.*;
import java.io.*;

public class cownomics {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=736
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cownomics.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cownomics.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		String[] bad = new String[n];
		String[] good = new String[n];
		for (int i=0; i<n; i++) {
			bad[i] = in.readLine();
		}
		for (int i=0; i<n; i++) {
			good[i] = in.readLine();
		}
		
		int count=0;
		for (int character=0; character < m; character++) {
			HashSet<Character> a = new HashSet<>();
			for (int i=0; i<n; i++) {
				a.add(bad[i].charAt(character));
			}
			boolean works=true;
			for (int i=0; i<n; i++) {
				if (a.contains(good[i].charAt(character))) works=false;
			}
			if (works)count++;
		}
		
		System.out.println(count);
		out.println(count);
		out.close();

	}
}