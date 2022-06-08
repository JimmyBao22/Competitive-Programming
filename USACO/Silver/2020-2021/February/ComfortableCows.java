
import java.util.*;
import java.io.*;

public class ComfortableCows {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=1110
	
	static int range = 4002;
	static boolean[][] coordinate = new boolean[range][range];
	static int[][] count = new int[range][range];
	static int[] xdir = {1, -1, 0, 0}, ydir = {0, 0, 1, -1};
	static HashSet<Pair> all = new HashSet<>(), added = new HashSet<>();
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//      BufferedReader in = new BufferedReader(new FileReader("ComfortableCows.in"));
		//		PrintWriter out = new PrintWriter(new FileWriter("ComfortableCows.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			arr[i][0] += 2000;		// so it doesn't go out of range
			arr[i][1] += 2000;
		}
		
		int[] ans = new int[n];
			
		for (int i=0; i<n; i++) {
			// add this cow
			Pair cur = new Pair(arr[i][0], arr[i][1]);
			all.add(cur);
			coordinate[cur.x][cur.y] = true;

			if (added.contains(cur)) {
				added.remove(cur);			// if already added, then already
											// have checked conditions
			}
			else {
				for (int j=0; j<4; j++) {
					int x = cur.x + xdir[j];
					int y = cur.y + ydir[j];
					count[x][y]++;
					if (coordinate[x][y] && count[x][y] == 3) {
						fill(x, y);
					}
				}
			}
			
			if (count[cur.x][cur.y] == 3) {
				fill(cur.x, cur.y);
			}
			
			ans[i] = added.size();
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<n; i++) {
			sb.append(ans[i]);
			sb.append("\n");
		}

		System.out.print(sb);
		//		out.println();
		//		out.close();
	}
	
	public static void fill(int x, int y) {
		if (count[x][y] == 3) {
			for (int k=0; k<4; k++) {
				int xx = x + xdir[k];
				int yy = y + ydir[k];
				if (!coordinate[xx][yy]) {
					added.add(new Pair(xx, yy));
					coordinate[xx][yy] = true;
					
					// continue filling if others around reach 3 as well
					for (int i=0; i<4; i++) {
						int xxx = xx + xdir[i];
						int yyy = yy + ydir[i];
						count[xxx][yyy]++;
						if (count[xxx][yyy] == 3 && coordinate[xxx][yyy]) fill(xxx, yyy);
					}
					
					fill(xx, yy);
				}
			}
		}
	}
	
	static class Pair {	
		int x, y;
			// coordinate, index
		
		Pair (int a, int b) {
			x = a; y = b;
		}
		
		@Override
		 public boolean equals(Object o) {        
		     Pair cur = (Pair) o;   
		     if (x != cur.x || y != cur.y) return false;
		     return true;
		 }    
		 
		 @Override
		 public int hashCode() {        
			 return x * 97 + y * 103 + 31;
		 }
	}
}