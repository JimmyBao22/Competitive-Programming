
import java.util.*;
import java.io.*;

public class highcard {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=571
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("highcard.in"));

		int n = Integer.parseInt(in.readLine());
		int[] Elsiecards = new int[n];
		HashSet<Integer> Elsiecardsset = new HashSet<>();
		int[] Bessiecards = new int[n];
		
		for (int i=0; i<n; i++) {
			Elsiecards[i] = Integer.parseInt(in.readLine());
			Elsiecardsset.add(Elsiecards[i]);
		}

		int bessieindex=0;
		for (int i=1; i<=2*n; ++i) {
			if (!Elsiecardsset.contains(i)) {
				Bessiecards[bessieindex] = i;
				bessieindex++;
			}
		}
		
		Arrays.parallelSort(Bessiecards);
		Arrays.parallelSort(Elsiecards);

		int bessiepointer=0;
		int count=0;
		for (int elsiepointer=0; elsiepointer<n && bessiepointer<n; elsiepointer++) {
			while (bessiepointer < n && Bessiecards[bessiepointer] < Elsiecards[elsiepointer]) {
				bessiepointer++;
			}
			if (bessiepointer >= n) break;
			
			count++;
			bessiepointer++;
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("highcard.out"));
		System.out.println(count);
		out.println(count);
		out.close();

	}
}