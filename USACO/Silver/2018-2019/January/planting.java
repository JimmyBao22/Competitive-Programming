
import java.util.*;
import java.io.*;

public class planting {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=894
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("planting.in"));
		PrintWriter out = new PrintWriter(new FileWriter("planting.out"));

		int n = Integer.parseInt(in.readLine());
		ArrayList<ArrayList<Integer>> g = new ArrayList<>();
		for (int i=0; i<n; i++) {
			g.add(new ArrayList<>());
		}
		
		for (int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			g.get(a).add(b);
			g.get(b).add(a);
		}
		
		int max=1;
		for (int i=0; i<n; i++) {
			max = Math.max(max, g.get(i).size()+1);
		}

		System.out.println(max);
		out.println(max);
		out.close();

	}
}