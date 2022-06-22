
import java.util.*;
import java.io.*;

public class ConvolutedIntervals {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1160
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("a.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("a.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		long[] number_line = new long[2*m+1];
		long[] start_count = new long[2*m+1];
		long[] end_count = new long[2*m+2];
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			start_count[arr[i][0]]++;
			end_count[arr[i][1]]++;
		}
				
		for (int i=0; i<number_line.length; i++) {
			// check how many start at this point
			for (int j=0; j<=i; j++) {
				number_line[i] += start_count[j] * start_count[i-j];
			}
			
			// check how many end at this point
			for (int j=1; j<=i; j++) {
				number_line[i] -= end_count[j-1] * end_count[i-j];
			}
		}
		
		StringBuilder s = new StringBuilder();
		s.append(number_line[0]);
		s.append("\n");
		for (int i=1; i<number_line.length; i++) {
			number_line[i] += number_line[i-1];
			s.append(number_line[i]);
			s.append("\n");
		}
		
		System.out.print(s);
		//		out.println();
		//		out.close();
	}
}