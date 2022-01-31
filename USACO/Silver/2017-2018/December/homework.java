
import java.util.*;
import java.io.*;

public class homework {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=762

	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("homework.in"));
		
		int n = Integer.parseInt(in.readLine());  
		int[] scores = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());

		for (int i = 0; i < n; i++)	scores[i] = Integer.parseInt(st.nextToken());  
		in.close();

		// note: bessie eats 1 to n-2 questions. will always eat at least 1, and it 
			// will never leave just the last one
		// note: start at end bc last grade will always be included
		int postMin = scores[n-1]; // lowest score at or after n-1 
		int postSum = scores[n-1];	// sum of scores at or after ...
		int postCount = 1; // # scores at or after ...
		
		Fraction grade[] = new Fraction[n-1];
		// index = first score included in grade == # scores skipped (0 unused)
		// value = grade (avg with lowest excluded)
		
		Fraction best = new Fraction(scores[n-1], 1);
		
		// accumulate grade, right to left
		for (int i = n-2; i > 0; i--) {
			postMin = Math.min(postMin,  scores[i]);
			postSum += scores[i];
			postCount++;
			grade[i] = new Fraction(postSum - postMin, postCount-1);
			
			if (grade[i].compareTo(best) > 0) best = grade[i];
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("homework.out"));
		
		for (int i = 1; i < n-1; i++) {
			System.out.println(grade[i]);
			if (grade[i].compareTo(best) == 0) {
				System.out.println(i);
				out.println(i);
			}
		}
		out.close();
	}

	// precise fraction storage with comparison by cross-multiplication
	static class Fraction implements Comparable<Fraction> {
		int n, d;
		
		Fraction(int n, int d) {
			this.n = n;
			this.d = d;
		}
		
		public int compareTo(Fraction other) {
			return Long.signum((long) this.n * other.d - (long) other.n * this.d);
			// returns 1 if pos, 0 if 0, -1 if neg
			// this is just cross multiplication
				// n1/d1 ? n2/d2 ==> n1*d2 ? n2*d1
		}
		
		public String toString() {
			return n + "/" + d;
		}
	}
}