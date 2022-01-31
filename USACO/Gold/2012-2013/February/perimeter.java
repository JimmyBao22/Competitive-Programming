
import java.util.*;
import java.io.*;

public class perimeter {

	// http://usaco.org/index.php?page=viewproblem2&cpid=244
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader("perimeter.in"));
		PrintWriter out = new PrintWriter(new FileWriter("perimeter.out"));

		int n = Integer.parseInt(in.readLine());
		int[][] arr = new int[n][2];
		HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
			// x = y's
		
		int curx = 1000000, cury = 1000000;
		for (int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken());
			arr[i][1] = Integer.parseInt(st.nextToken());
			if (!map.containsKey(arr[i][0])) map.put(arr[i][0], new HashSet<>());
			map.get(arr[i][0]).add(arr[i][1]);
			if (arr[i][0] < curx) {
				curx = arr[i][0];
				cury = arr[i][1];
			}
		}
		
		int count=0;
		int startx = curx, starty = cury, startdir = 0;
		int direction = 0;
			// 0 = up, 1 = right, 2 = down, 3 = left
		while (true) {
			
			count++;
			
			if (direction == 0) {
				// turn left. At 1 go to 3
				// 32
				//  1
				if (map.containsKey(curx-1) && map.get(curx-1).contains(cury+1)) {
					curx--; cury++;
					direction = 3;
				}
				// go straight. At 1 go to 2
				// 2
				// 1
				else if (map.containsKey(curx) && map.get(curx).contains(cury+1)) {
					cury++;
				}
				// turn right. At 2 go to top of 2
				// 23
				// 1
				else {
					direction = 1;
				}
			}
			else if (direction == 1) {
				// turn left. At 1 go to 3
				//  3
				// 12 
				if (map.containsKey(curx+1) && map.get(curx+1).contains(cury+1)) {
					curx++; cury++;
					direction = 0;
				}
				// go straight. At 1 go to 2
				// 
				// 12
				else if (map.containsKey(curx+1) && map.get(curx+1).contains(cury)) {
					curx++;
				}
				// turn right. At 2 go to right of 2
				// 12
				//  3
				else {
					direction = 2;
				}
			}
			else if (direction == 2) {
				// turn left. At 1 go to 3
				// 1
				// 23
				if (map.containsKey(curx+1) && map.get(curx+1).contains(cury-1)) {
					curx++; cury--;
					direction = 1;
				}
				// go straight. At 1 go to 2
				// 1
				// 2
				else if (map.containsKey(curx) && map.get(curx).contains(cury-1)) {
					cury--;
				}
				// turn right. At 2 go to bottom of 2
				//  1
				// 32
				else {
					direction = 3;
				}
			}
			else {
				// turn left. At 1 go to 3
				// 21
				// 3
				if (map.containsKey(curx-1) && map.get(curx-1).contains(cury-1)) {
					curx--; cury--;
					direction = 2;
				}
				// go straight. At 1 go to 2
				// 21
				// 
				else if (map.containsKey(curx-1) && map.get(curx-1).contains(cury)) {
					curx--;
				}
				// turn right. At 2 go to left side of 2
				// 3
				// 21
				else {
					direction = 0;
				}
			}

			if (curx == startx && cury == starty && startdir == direction) {
				break;
			}
		}
				
		System.out.println(count);
		out.println(count);
		out.close();
	}
}

/*
	all cells on leftmost, upmost, rightmost, downmost  are  always on perimeter
		find one cell you know is on perimeter and start moving
			start leftmost, then face up. 
	
	always keep a block not the right of you. This will allow you to go through the whole thing
		(like a maze)
	
	First prioritize turning left. Then going straight. then turning right
*/