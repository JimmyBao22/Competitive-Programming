
import java.util.*;
import java.io.*;

public class cardgame {

	// http://usaco.org/index.php?page=viewproblem2&cpid=573
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("cardgame.in"));
		PrintWriter out = new PrintWriter(new FileWriter("cardgame.out"));

		int n = Integer.parseInt(in.readLine());
		int[] Elsie = new int[n];
		HashSet<Integer> E_set = new HashSet<>();
		int[] Bessie = new int[n];		
		for (int i=0; i<n; i++)  {
			Elsie[i] = Integer.parseInt(in.readLine());
			E_set.add(Elsie[i]);
		}
		
		int pointer=0;
		for (int i=1; i<=2*n; i++) {
			if (!E_set.contains(i)) {
				Bessie[pointer] = i;
				pointer++;
			}
		}
		
		int[] Elsiefirst = new int[n/2];
		int[] Elsiesec = new int[n/2];
		
		for (int i=0; i<n/2; i++) {
			Elsiefirst[i] = Elsie[i];
		}
		for (int i=n/2; i<n; i++) {
			Elsiesec[i-n/2] = Elsie[i];
		}
		
		Arrays.parallelSort(Elsiefirst);
		Arrays.parallelSort(Elsiesec);

		int count=0;
		pointer = n-1;
		for (int i=Elsiefirst.length-1; i>=0; i--) {
			if (Elsiefirst[i] < Bessie[pointer]) {
				count++;
				pointer--;
			}
		}
		int pointer2=0;
		for (int i=0; i<n/2 && pointer2<=pointer; i++) {
			if (Elsiesec[i] > Bessie[pointer2]) {
				pointer2++;
				count++;
			}
		}
		
		System.out.println(count);
		out.println(count);
		out.close();
	}
}