
import java.util.*;
import java.io.*;

public class maxcross {
	
	// http://www.usaco.org/index.php?page=viewproblem2&cpid=715
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("maxcross.in"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());  
		int k = Integer.parseInt(st.nextToken());  
		int b = Integer.parseInt(st.nextToken());  

		Set<Integer> broken = new TreeSet<Integer>();
		for (int i = 0; i < b; i++) {
			broken.add(Integer.parseInt(in.readLine()));
		}
		in.close();

		int numbroken = 0;
		//the first one
		for (int i = 1; i <= k; i++) {
			// 1 to k
			if (broken.contains(i)) {
				numbroken++;
			}
		}
		
		int minbroken = numbroken;
		for (int i = 2; i <= n+1-k; i++) {
			if (broken.contains(i-1)) {
				// we left this one
				numbroken--;
			}
			if (broken.contains(i+k-1)) {
				numbroken++;
			}
			
			minbroken = Math.min(numbroken, minbroken);
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("maxcross.out"));
		System.out.println(minbroken);
		out.println(minbroken);
		out.close();
	}
}