
import java.util.*;
import java.io.*;

public class CrazyTown {

	// https://codeforces.com/contest/498/problem/A
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new FileReader("CrazyTown"));

		long[][] yourcoords = new long[2][2];
		StringTokenizer st = new StringTokenizer(in.readLine());
		yourcoords[0][0] = Integer.parseInt(st.nextToken());
		yourcoords[0][1] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(in.readLine());
		yourcoords[1][0] = Integer.parseInt(st.nextToken());
		yourcoords[1][1] = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(in.readLine());
		long[][] roads = new long[n][3];
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			roads[i][0] = Integer.parseInt(st.nextToken());
			roads[i][1] = Integer.parseInt(st.nextToken());
			roads[i][2] = Integer.parseInt(st.nextToken());
		}

		int count=0;
		for (int i=0; i<n; i++ ) {
			
			// if on opposite sides of a line, must cross it
			
			long first = yourcoords[0][0] * roads[i][0] + yourcoords[0][1] * roads[i][1] + roads[i][2];
			long sec = yourcoords[1][0] * roads[i][0] + yourcoords[1][1] * roads[i][1] + roads[i][2];

			if ((first>0) == (sec >0)) {
				
			}
			else count++;
			
		}
		
		System.out.println(count);
	}
}
