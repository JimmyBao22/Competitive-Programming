
import java.util.*;
import java.io.*;

public class Packmen {

	// https://codeforces.com/contest/847/problem/E
	
	static int n;
	static String s;
	static ArrayList<Integer> star, p;
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("Packmen"));

		n = Integer.parseInt(in.readLine());
		s = in.readLine();
		p = new ArrayList<>(); star = new ArrayList<>();
		
		for (int i=0; i<n; i++) {
			if (s.charAt(i) == 'P') p.add(i);
			if (s.charAt(i) == '*') star.add(i);
		}
		
		int min=0;
		int max=5*n;
		while (min<max) {
			int middle = (min+max)/2;
			if (check(middle)) {
				max = middle;
			}
			else min = middle+1;
		}
		System.out.println(min);
	}
	
	public static boolean check(int mid) {
		int starp=0;
		for (int i=0; i<p.size(); i++) {
			if (starp < star.size() && star.get(starp) < p.get(i) && p.get(i) - star.get(starp) > mid) return false;
			
			int min=starp-1;
			int max=star.size()-1;
			
			int forwards = min;

				// go to star.get(starp) then go as far as possible to right
			if (starp < star.size() && star.get(starp) < p.get(i)) {
				while (min<max) {
					int middle = (min+max+1)/2;
					if (star.get(middle) - star.get(starp) + p.get(i) - star.get(starp) <= mid) {
						min = middle;
					}
					else max = middle-1;
				}
				forwards = min;
				min=starp-1;
				max=star.size()-1;
					// go to right then back to starp
				while (min<max) {
					int middle = (min+max+1)/2;
					if (Math.abs(p.get(i) - star.get(middle)) + Math.abs(star.get(middle) - star.get(starp)) <= mid) {
						min = middle;
					}
					else max = middle-1;
				}
			}
			else {
				while (min<max) {
					int middle = (min+max+1)/2;
					if (star.get(middle) - p.get(i) <= mid) {
						min =middle;
					}
					else max = middle-1;
				}
			}
			
			starp = Math.max(forwards, min)+1;
			
		}
		return starp >= star.size();
	}
}