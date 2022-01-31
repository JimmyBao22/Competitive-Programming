
import java.util.*;
import java.io.*;

public class dishes {

	// http://usaco.org/index.php?page=viewproblem2&cpid=922
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("dishes.in"));
		PrintWriter out = new PrintWriter(new FileWriter("dishes.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(in.readLine());
		
		ArrayList<ArrayList<Integer>> stacks = new ArrayList<>();
		int elsielast = 0;		// last number that elsie took
		for (int i=0; i<n; i++) {
			if (arr[i] < elsielast) {
				System.out.println(i);
				out.println(i);
				out.close();
				return;
			}
			
			int min=0;
			int max=stacks.size();
			while (min < max) {
				int middle = (min + max)/2;
				if (stacks.get(middle).get(0) < arr[i]) {
					min = middle+1;
				}
				else max = middle;
			}
			
			if (min == stacks.size()) {
				ArrayList<Integer> c = new ArrayList<>();
				c.add(arr[i]);
				stacks.add(c);
			}
			else {
				// repeatedly take stuff off the stack until you can put arr[i] on it
				while (stacks.get(min).get(stacks.get(min).size()-1) < arr[i]) {
					elsielast = stacks.get(min).get(stacks.get(min).size()-1);
					stacks.get(min).remove(stacks.get(min).size()-1);
				}
				stacks.get(min).add(arr[i]);
			}
		}
		
		System.out.println(n);
		out.println(n);
		out.close();
	}
}