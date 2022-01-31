
import java.util.*;
import java.io.*;

public class lemonade {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=835%27
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("lemonade.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lemonade.out"));

		int n = Integer.parseInt(in.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(in.readLine());
		for (int i=0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());

		Arrays.parallelSort(arr);
		int count=0;
		for (int i=n-1; i>=0; --i) {
			if (arr[i] >= count) {
				count++;
			}
			else break;
		}
		
		System.out.println(count);
		out.println(count);
		out.close();
	}
}