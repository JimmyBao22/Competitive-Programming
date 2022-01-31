
import java.util.*;
import java.io.*;

public class crazy {

	// http://www.usaco.org/index.php?page=viewproblem2&cpid=207
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("crazy.in"));
		PrintWriter out = new PrintWriter(new FileWriter("crazy.out"));

		StringTokenizer st = new StringTokenizer(in.readLine());
		int n = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int[][] fences = new int[n][4];
		int[][] cows = new int[c][2];
		
			// coord compresss
		TreeMap<Integer, Integer> xcoords = new TreeMap<>();
		TreeMap<Integer, Integer> ycoords = new TreeMap<>();
		for (int i=0; i<n; i++) {
			st = new StringTokenizer(in.readLine());
			fences[i][0] = Integer.parseInt(st.nextToken());
			fences[i][1] = Integer.parseInt(st.nextToken());
			fences[i][2] = Integer.parseInt(st.nextToken());
			fences[i][3] = Integer.parseInt(st.nextToken());
			xcoords.put(fences[i][0], 0);
			xcoords.put(fences[i][2], 0);
			ycoords.put(fences[i][1], 0);
			ycoords.put(fences[i][3], 0);
		}

		for (int i=0; i<c; i++) {
			st = new StringTokenizer(in.readLine());
			cows[i][0] = Integer.parseInt(st.nextToken());
			cows[i][1] = Integer.parseInt(st.nextToken());
			xcoords.put(cows[i][0], 0);
			ycoords.put(cows[i][1], 0);
		}
		
		// don't start at 0, because there needs to be a border around whole thing
		int p=100;
		for (Integer x : xcoords.keySet()) {
			xcoords.put(x, p);
			p+=2;
		}
		
		p=100;
		for (Integer y : ycoords.keySet()) {
			ycoords.put(y, p);
			p+=2;
		}
				
		int maxn = 100 + Math.max(xcoords.get(xcoords.lastKey()), ycoords.get(ycoords.lastKey()));
		int[][] regions = new int[maxn][maxn];
		
		for (int i=0; i<n; i++) {
			if (fences[i][0] == fences[i][2]) {
				int x = xcoords.get(fences[i][0]);
				
				// fences[i][1] > fences[i][3] OR fences[i][3] > fences[i][1]
				p = ycoords.get(fences[i][1]);
				while (p <= ycoords.get(fences[i][3])) {
					regions[x][p] = -1;
					if (p < ycoords.get(fences[i][3])) regions[x][p+1] = -1;
					p+=2;
				}
				p = ycoords.get(fences[i][3]);
				while (p <= ycoords.get(fences[i][1])) {
					regions[x][p] = -1;
					if (p < ycoords.get(fences[i][1])) regions[x][p+1] = -1;
					p+=2;
				}
			}
			else {
				int y = ycoords.get(fences[i][1]);
				
				// fences[i][0] > fences[i][2] OR fences[i][2] > fences[i][0]
				p = xcoords.get(fences[i][0]);
				while (p <= xcoords.get(fences[i][2])) {
					regions[p][y] = -1;
					if (p < xcoords.get(fences[i][2])) regions[p+1][y] = -1;
					p+=2;
				}
				p = xcoords.get(fences[i][2]);
				while (p <= xcoords.get(fences[i][0])) {
					regions[p][y] = -1;
					if (p < xcoords.get(fences[i][0])) regions[p+1][y] = -1;
					p+=2;
				}
			}
		}
		
		int color=1;
		for (int i=0; i<regions.length; i++) {
			for (int j=0; j<regions[0].length; j++) {
				if (regions[i][j] == 0) {
					bfs(regions, color, i, j);
					color++;
				}
			}
		}
		
		int[] count = new int[color];
		for (int i=0; i<c; i++) {
			int x = xcoords.get(cows[i][0]);
			int y = ycoords.get(cows[i][1]);
			count[regions[x][y]]++;
		}
		
		int max=1;
		for (int i=0; i<color; i++) {
			max = Math.max(max, count[i]);
		}
		
		System.out.println(max);
		out.println(max);
		out.close();

	}
	
	public static void bfs(int[][] regions, int color, int x, int y) {
		ArrayDeque<int[]> d = new ArrayDeque<>();
		d.add(new int[] {x,y});
		while (!d.isEmpty()) {
			int[] cur = d.poll();
			if (cur[0] < 0 || cur[1] < 0 || cur[0] >= regions.length || cur[1] >= regions[0].length) continue;
			if (regions[cur[0]][cur[1]] != 0) continue;
			regions[cur[0]][cur[1]] = color;
			d.add(new int[] {cur[0]+1,cur[1]});
			d.add(new int[] {cur[0]-1,cur[1]});
			d.add(new int[] {cur[0],cur[1]+1});
			d.add(new int[] {cur[0],cur[1]-1});
		}
	}
}

/*

	Coord Compress --- Only keep track of important points
	
	Use p += 2 instead of p++, because notice something like
		A road from 0,0 to 0,2. Then a road from 1,1 to 1,2
		If you do p++, then regions[0][0], regions[0][1], regions[0][2], regions[1][1], regions[1][2]
			are all covered, which actually creates a segment 0,1 to 1,1, which we do not want
			
			Therefore, doing p += 2, this new segment won't be created unintentionally

*/