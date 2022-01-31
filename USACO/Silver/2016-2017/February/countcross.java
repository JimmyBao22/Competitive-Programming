
import java.util.*;
import java.io.*;

public class countcross {
	
	//  http://www.usaco.org/index.php?page=viewproblem2&cpid=716
	
	static int n;
	static int[][] farm;
			
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("countcross.in"));
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());  
		farm = new int[2*n+1][2*n+1];
		
		int k = Integer.parseInt(st.nextToken());  
		int r = Integer.parseInt(st.nextToken());  

		for (int i = 0; i < r; i++) {
			StringTokenizer stt = new StringTokenizer(in.readLine());
			int r1 = Integer.parseInt(stt.nextToken()) * 2 - 1;  
			int c1 = Integer.parseInt(stt.nextToken()) * 2 - 1;  
			int r2 = Integer.parseInt(stt.nextToken()) * 2 - 1;  
			int c2 = Integer.parseInt(stt.nextToken()) * 2 - 1;  
			
			int rRoad = (r1+r2)/2;
			int cRoad = (c1+c2)/2;
			
			farm[rRoad][cRoad] = -1;
				// -1 means this part of farm is blocked
		}
		
		for (int i = 0; i < 2*n+1; i+=2) {
			for (int j = 0; j < 2*n+1; j+=2) {
				// all even numbered should be blocked
				farm[i][j] = -1;
			}
		}
		
		int regionId = 1;
		for (int fr = 1; fr < 2*n; fr += 2) {
			// odd numbers
			for (int fc = 1; fc < 2*n; fc += 2) {
				if (farm[fr][fc] == 0) {
					// not flooded yet
					floodFill(fr, fc, regionId);
					regionId++;
				}
			}
		}
		
		int[] cowRegions = new int[k];
			// index: which cow
			// value: which region is this cow inside
		
		for (int i = 0; i < k; i++) {
			StringTokenizer sttt = new StringTokenizer(in.readLine());
						
			int cowR = Integer.parseInt(sttt.nextToken()) * 2 - 1;  
			int cowC = Integer.parseInt(sttt.nextToken()) * 2 - 1;  
			
			cowRegions[i] = farm[cowR][cowC];
		
		}
		
		in.close();

		int result = 0;
		for (int i = 0; i < k; i++) {
			for (int j=i+1; j < k; j++) {
				if (cowRegions[i] != cowRegions[j]) result++;
			}
		}
		
		PrintWriter out = new PrintWriter(new FileWriter("countcross.out"));
		System.out.println(result);
		out.println(result);
		out.close();

	}

	// fill in unmarked squares in region with given id
	public static void floodFill(int r, int c, int id) {
		if (r <= 0 || r >= 2*n || c <= 0 || c>=2*n) return;
		
		// dont cross road
		if (farm[r][c] == -1) return;
		
		// don't revisit cell
		if (farm[r][c] != 0) return;
		
		// make sure we don't revisit bc its not zero anymore
		// marks the region with the id number
		farm[r][c] = id;
		
		floodFill(r+1, c, id);
		floodFill(r-1, c, id);
		floodFill(r, c+1, id);
		floodFill(r, c-1, id);
	}
}

/*
	
	3 3 3
	2 2 2 3
	3 3 3 2
	3 3 2 3
	3 3
	2 2
	2 3
	
	note: coords go from 1 to n
	
	3x3 field, 3 cows, 3 roads
	1 2 3 4 5
1	F   F   F 
2	
3	F   * | *
4		    -
5	F   F | *
	make bigger grid, twice as big
	array[r*2+1][c*2+1]
		will have walls around the outsides
	
	sol 1 to go around gaps
		we will not land on empty spaces, we will jump over
			every time we get to a field just check if there are
			any roads
	sol 2 to go around gaps
		block extra amount either left and right or up and down (block 3 squares instead of 1)
			ex. F   F
				- - -
				F   F
	sol 3 to go around gaps
		block off ALL 4 way corner spaces, since we will never move through them anyways

	NOte: all fields in odd x odd; all autoblocked in even x even; all potential roads in one even x one odd
	
	
	look at every field cell (odd coord)
		if we have determined its region, skip it
		if we haven't determined region, flood it and assign a new id # to all cells in the region
	
	do a loop through all pairs of cows that are distant; count them
		
*/