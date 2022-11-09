
import java.util.*;
import java.io.*;

public class StringGame {

	// https://codeforces.com/edu/course/2/lesson/6/2/practice/contest/283932/problem/F
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("StringGame"));

		char[] p =in.readLine().toCharArray();
		char[] t = in.readLine().toCharArray();
		int[] instructions = new int[p.length];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<p.length; i++) {
			instructions[i] = Integer.parseInt(st.nextToken())-1;
		}
		boolean[] bad = new boolean[p.length];
		int min=0;
		int max = p.length;
		while (min<max) {
			int middle = (min+max+1)/2;
			if (check(instructions, bad, p, t, middle)) {
				min=middle;
			}
			else max = middle-1;
		}
		
		System.out.println(min);
		
	}
	
	public static boolean check(int[] instructions, boolean[] bad, char[] p, char[] t, int mid) {
		Arrays.fill(bad, false);
		for (int i=0; i<mid; i++) {
			bad[instructions[i]] = true;
		}
		int tpoint=0;
		for (int i=0; i<p.length; i++) {
			if (bad[i]) continue;
			if (tpoint<t.length && p[i] == t[tpoint]) tpoint++;
		}
		return tpoint == t.length;
	}	
}