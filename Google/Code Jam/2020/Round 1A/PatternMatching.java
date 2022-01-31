
import java.util.*;
import java.io.*;

public class PatternMatching {

	// https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd74/00000000002b3034
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("PatternMatching"));

		int t = Integer.parseInt(in.readLine());
		o: for (int tt = 1; tt < t + 1; tt++) {
			int n =  Integer.parseInt(in.readLine());
			char[][] arr = new char[n][];
			int[] fp = new int[n];
			int[] sp = new int[n];
			for (int i=0; i<n; i++) {
				arr[i] = in.readLine().toCharArray();
				sp[i] = arr[i].length-1;
			}
			
			ArrayList<Character> begin = new ArrayList<>();
			ArrayList<Character> end = new ArrayList<>();
			
			for (int i=0; i<n; i++) {
				ArrayList<Character> cur = new ArrayList<>();
				while (fp[i] < arr[i].length && arr[i][fp[i]] != '*') {
					cur.add(arr[i][fp[i]]);
					fp[i]++;
				}
				for (int j=0; j<cur.size(); j++) {
					if (j == begin.size()) {
						begin.add(cur.get(j));
					}
					else {
						if (cur.get(j) != begin.get(j)) {
							System.out.println("Case #" + tt + ": *");
							continue o;
						}
					}
				}
			}
			
			for (int i=0; i<n; i++) {
				ArrayList<Character> cur = new ArrayList<>();
				while (sp[i] >= fp[i] && arr[i][sp[i]] != '*') {
					cur.add(arr[i][sp[i]]);
					sp[i]--;
				}
				for (int j=0; j<cur.size(); j++) {
					if (j == end.size()) {
						end.add(cur.get(j));
					}
					else {
						if (cur.get(j) != end.get(j)) {
							System.out.println("Case #" + tt + ": *");
							continue o;
						}
					}
				}
			}
			
			StringBuilder s = new StringBuilder();
			for (int i=0; i<begin.size(); i++) {
				s.append(begin.get(i));
			}
			
			for (int i=0; i<n; i++) {
				for (int j=fp[i]; j<=sp[i]; j++) {
					if (arr[i][j] != '*') s.append(arr[i][j]);
				}
			}
			
			for (int i=end.size()-1; i>=0; i--) { 
				s.append(end.get(i));
			}

			System.out.println("Case #" + tt + ": " + s);
		}
	}
}